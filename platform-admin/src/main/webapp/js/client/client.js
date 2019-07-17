$(function () {
    $("#jqGrid").Grid({
        url: '../client/list',
        colModel: [
            {label: '客户ID', name: 'id', index: "id", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            {label: '手机号', name: 'clientTel', width: 75},
            {label: '客户类型', name: 'clientType', width: 75, formatter: function (value) {
                switch (value){
                    case '1':
                        value = "A类";break;
                    case '2':
                        value = "B类";break;
                    case '3':
                        value = "C类";break;
                    case '4':
                        value = "D类";break;
                    case '5':
                        value = "E类";break;
                    default:value = "无";
                }
                return value;
            }},
            {label: '客户经理姓名', name: 'clientManagerName', width: 75},
            {label: '未跟单天数', name: 'noTrackOrder', width: 75},
            {label: '是否上门', name: 'isVisit', width: 75, formatter: function (value) {
                return value == 0 ? "未上门":"已上门";
            }},
            {label: '是否记入成本', name: 'isRecordCost', width: 75, formatter: function (value) {
                return value == 0 ? "不计入":"记入";
            }},
            {label: '成本金额', name: 'cost', width: 75},
            {
                label: '创建时间', name: 'createTime', index: "createTime", width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            { label: '更新时间', name: 'actionTime', index: "actionTime", width: 80, formatter: function (value) {
                return transDate(value);
            }}]
    });
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            clientTel: null
        },
        showList: true,
        title: null,
        roleList: {},
        client: {
            status: 1,
            deptName: '',
            roleIdList: []
        },
        ruleValidate: {
            clientName: [
                {required: true, message: '姓名不能为空', trigger: 'blur'}
            ],
            clientTel: [
                {required: true, message: '手机号不能为空', trigger: 'blur'}
            ]
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增客户";
            vm.client = {status: 1, roleIdList: [], deptId: '', deptName: ''};

        },
        update: function () {
            var clientId = getSelectedRow("#jqGrid");
            if (clientId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            Ajax.request({
                url: "../client/info/" + clientId,
                async: true,
                successCallback: function (r) {
                    vm.client = r.client;
                }
            });

        },
        del: function () {
            var clientIds = getSelectedRows("#jqGrid");
            if (clientIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../client/delete",
                    params: JSON.stringify(clientIds),
                    contentType: "application/json",
                    type: 'POST',
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.client.id == null ? "../client/save" : "../client/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.client),
                contentType: "application/json",
                type: 'POST',
                successCallback: function () {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        followRecord: function () {
            var id = getSelectedRow("#jqGrid");
            var clientName = $("#jqGrid").getRowData(id).clientName;
            if (id == null) {
                return;
            }
            openWindow({
                title: '跟单记录',
                type: 2,
                content: encodeURI('../client/clientFollowRecord.html?clientId=' + id + '&clientName='+clientName)
            })
        },
        loanRecord: function () {
            var id = getSelectedRow("#jqGrid");
            var clientName = $("#jqGrid").getRowData(id).clientName;
            if (id == null) {
                return;
            }
            openWindow({
                title: '贷款记录',
                type: 2,
                content: encodeURI('../client/clientLoanRecord.html?clientId=' + id + '&clientName='+clientName)
            })
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'clientTel': vm.q.clientTel},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        }
    }
});