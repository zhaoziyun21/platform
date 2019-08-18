$(function () {
    $("#jqGrid").Grid({
        url: '../client/list',
        colModel: [
            {label: '客户ID', name: 'id', index: "id", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            {label: '手机号', name: 'clientTel', width: 75},
            {label: '客户经理', name: 'clientManagerName', width: 75},
            {label: '状态', name: 'status', width: 75, formatter: function (value) {
                switch (value){
                    case '0':
                        value = "未分配";break;
                    case '1':
                        value = "已分配";break;
                    case '2':
                        value = "公海";break;
                }
                return value;
            }}
            ]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            status:null,
            clientTel:""
        },
        showList: true,
        showDivide: false,
        users:{},
        title: null,
        clientTelRecord: {},
        clientIds: 0,
        divideUserId:0,
        divideUserName:"",
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
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'status': vm.q.status,
                    'clientTel':vm.q.clientTel
                },
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        setDivideUserId: function (id) {
            vm.divideUserId = id;
        },
        telImportRecord: function () {
            openWindow({
                title: '手机号导入',
                type: 2,
                content: encodeURI('../client/telImportRecord.html')
            })
        },
        excelImportRecord: function () {
            //待定
        },
        divideRecord: function () {
            var id = getSelectedRows("#jqGrid");
            if (id == null) {
                return;
            }
            var selectedIDs = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
            var idList = '';
            selectedIDs.forEach(function(value,i){
                var row = $("#jqGrid").jqGrid('getRowData', value)
                idList += row.id +',';
            })
            vm.clientIds = id;
            vm.showDivide = true;
            Ajax.request({
                url: "../sys/user/queryAll",
                async: true,
                successCallback: function (r) {
                    vm.users = r.list;
                }
            });
            openWindow({
                title: "选择员工",
                area: ['300px', '450px'],
                content: jQuery("#divideDiv"),
                btn: ['分配', '取消'],
                btn1: function (index) {
                    Ajax.request({
                        url: "../client/divide",
                        type:"POST",
                        async: false,
                        contentType: "application/json",
                        params: JSON.stringify({
                            idList: vm.clientIds,
                            clientManagerId: vm.divideUserId,
                            clientManagerName: vm.divideUserName
                        }),
                        successCallback: function (r) {
                            vm.reload();
                        }
                    });

                    layer.close(index);
                }
            });
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        choice: function (obj) {
            vm.divideUserId = obj.value;
            vm.divideUserName = obj.label;
        }
    }
});