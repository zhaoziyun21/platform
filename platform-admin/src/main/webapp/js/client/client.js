$(function () {
    vm.getCurUser();
    vm.getManagerList();
    var page = getQueryStringNew("page") == null ? 1:getQueryStringNew("page");
    $("#jqGrid").Grid({
        url: '../client/list',
        page:page,
        rowNum: 15,//默认分页大小
        rowList: [15,20,30],//分页大小的下拉
        postData: {
            status: "1"
        },
        multiselect: false,//复选框
        colModel: [
            {label: '客户ID', name: 'id', index: "id", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', index: "clientNameHide", hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75, formatter: function (value, col, row) {
                          value == null ? '暂无' : value;
                    if(row.clientType == 1){
                        return "<font color='red'>"+row.clientName+"</font> "
                    }else{
                        return "<font>"+row.clientName+"</font> "
                    }
                return value ;
            }},
            {label: '客户类型', name: 'clientStar', width: 75, formatter: function (value) {
                switch (value){
                    case '1':
                        value = "1星";break;
                    case '2':
                        value = "2星";break;
                    case '3':
                        value = "3星";break;
                    case '4':
                        value = "4星";break;
                    case '5':
                        value = "5星";break;
                    default:value = "暂无";
                }
                return value;
            }},
            {label: '客户经理姓名', name: 'clientManagerName', width: 75},
            {label: '申请金额', name: 'applyAmount', width: 75,formatter: function (value) {
                    if(value  > 0){
                        var str = value+'万';
                        return str;
                    }else{
                        return 0;
                    }

                }},
            {label: '跟进状态', name: 'followRecordList', width: 75,formatter: function (value) {
                    if(value != undefined && value.length > 0){
                        var str = '';
                        value.forEach(function(value,index){
                            str += (index+1)+'、'+value.followRemark + ' \n';
                        });
                        return str;
                    }else{
                        return "";
                    }

                }},
            {
                label: '创建时间', name: 'createTime', index: "createTime", width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            { label: '更新时间', name: 'actionTime', index: "actionTime", width: 80, formatter: function (value) {
                return transDate(value);
            }},
            { label: '操作',  width: 160, formatter: function (value, col, row) {
                // return '<button class="btn btn-outline btn-info" onclick="vm.update(' + JSON.stringify(row) + ')"><i class="fa fa-info-circle"></i>&nbsp;修改</button>' ;
                //     var str = "<a  onclick='vm.getClientInfo(" + JSON.stringify(row) + ")'>查看</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    var str = "";
                    if(vm.user.userId == 1 || vm.user.userId == row.clientManagerId){
                        str += "<a  onclick='vm.updateNew(" + JSON.stringify(row) + ")'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
                            "<a  onclick='vm.giveUpClient(" + JSON.stringify(row) + ")'>放弃客户</a>&nbsp;&nbsp;&nbsp;&nbsp;" ;
                        if(row.clientType == 0){
                            str +=  "<a  onclick='vm.majorClient(" + JSON.stringify(row) + ")'>重点客户</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                        }else if(row.clientType == 1){
                            str +=  "<a  onclick='vm.cancelMajorClient(" + JSON.stringify(row) + ")'>取消重点客户</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                        }
                    }
                    if(vm.user.userId == 1){
                        str +=  "<a  onclick='vm.del(" + JSON.stringify(row) + ")'>删除</a>";
                    }
                    return str;
            }}
            ]
    });
    $("#jqGrid").jqGrid("setGridHeight", "auto");
});


var vm = new Vue({
    el: '#client',
    data: {
        q: {
            clientType: null,
            clientTel: null,
            clientName: null,
            clientFrom: null,
            clientManagerId: null
        },
        managerList:{},
        showClientList: true,
        title: null,
        roleList: {},
        client: {
            status: 1,
            deptName: '',
            roleIdList: []
        },
        user: {

        },
        ruleValidate: {
            clientName: [
                {required: true, message: '姓名不能为空', trigger: 'blur'}
            ],
            clientTel: [
                {required: true, message: '手机号不能为空', trigger: 'blur'}
            ],
            clientType: [
                {required: true, message: '客户类型不能为空', trigger: 'blur'}
            ],
            applyAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '申请金额只能是整数或者小数', trigger: 'change'}
            ],
            salaryMoney: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '工资金额只能是整数或者小数', trigger: 'change'}
            ],
            socialSecurityPay: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '社保个缴金额只能是整数或者小数', trigger: 'change'}
            ],
            socialSecurityYears: [
                { pattern: /^[0-9]+$/,message: '已交社保年限只能是整数', trigger: 'change'}
            ],
            gjjPay: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '公积金个缴金额只能是整数或者小数', trigger: 'change'}
            ],
            particleLoanAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '微粒贷金额只能是整数或者小数', trigger: 'change'}
            ],
            houseMonthPay: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '房子月供金额只能是整数或者小数', trigger: 'change'}
            ],
            carAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '车的价值金额只能是整数或者小数', trigger: 'change'}
            ],
            insureBillYearPay: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '保单年缴只能是整数或者小数', trigger: 'change'}
            ],
            insureBillMonthPay: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '保单月缴只能是整数或者小数', trigger: 'change'}
            ],
            insureBillYearCount: [
                { pattern: /^[0-9]+$/,message: '保单年缴次数只能是整数', trigger: 'change'}
            ],
            insureBillMonthCount: [
                { pattern: /^[0-9]+$/,message: '保单月缴次数只能是整数', trigger: 'change'}
            ],
            creditCardAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '信用卡额度只能是整数或者小数', trigger: 'change'}
            ],
            // gjjYears: [
            //     { pattern: /^[0-9]+$/,message: '公积金已交年限只能是整数', trigger: 'change'}
            // ],
            // salaryYears: [
            //     { pattern: /^[0-9]+$/,message: '工资联缴年限只能是整数', trigger: 'change'}
            // ],
            cost: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '成本金额只能是整数或者小数', trigger: 'change'}
            ]




        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showClientList = false;
            vm.title = "新增客户";
            vm.client = {status: 1, roleIdList: [], deptId: '', deptName: ''};

        },getCurUser: function () {
            Ajax.request({
                url: "../client/getCurUser/",
                async: false,
                successCallback: function (r) {
                    vm.user = r.user;
                }
            });

        },getManagerList: function () {
            Ajax.request({
                url: "../sys/user/managerList/",
                async: false,
                successCallback: function (r) {
                    vm.managerList = r.page.list;
                }
            });

        },

        update: function (row) {
            vm.showClientList = false;
            vm.title = "修改";
            var clientId =row.id;
            var clientName =row.realName;
            Ajax.request({
                url: "../client/info/" + clientId+"?clientId="+clientId+"&clientName="+clientName,
                async: false,
                successCallback: function (r) {
                    vm.client = r.client;
                    vm.user = r.user;
                }
            });

        },
        updateNew: function (row) {
            var page = $("#jqGrid").getGridParam().page;
            var clientId =row.id;
            var clientName =row.clientName;
            var url = '../client/clientInfo.html?clientId=' + clientId + '&clientName='+clientName+'&page='+page;
            location.href = url;

        },
        del: function (row) {
            // var clientIds = getSelectedRows("#jqGrid");
            // if (clientIds == null) {
            //     return;
            // }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../client/del",
                    params: JSON.stringify(row.id),
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
        saveOrUpdate: function () {
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
        giveUpClient: function (row) {
            var url = "../client/giveUpClient";
            var clientId =row.id;
            confirm('确定要放弃选中的客户？', function () {
                Ajax.request({
                    url: url+"?clientId="+clientId,
                    params: JSON.stringify(vm.client),
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
        majorClient: function (row) {
            var url = "../client/majorClient";
            var clientId =row.id;
            confirm('确定将选中的客户标记为重点客户？', function () {
                Ajax.request({
                    url: url+"?clientId="+clientId+"&clientType=1",
                    params: JSON.stringify(vm.client),
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
        cancelMajorClient: function (row) {
            var url = "../client/majorClient";
            var clientId =row.id;
            confirm('确定将选中的客户取消重点客户？', function () {
                Ajax.request({
                    url: url+"?clientId="+clientId+"&clientType=0",
                    params: JSON.stringify(vm.client),
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
        },signRecord: function () {
            var id = getSelectedRow("#jqGrid");
            var clientName = $("#jqGrid").getRowData(id).clientName;
            if (id == null) {
                return;
            }
            openWindow({
                title: '签单记录',
                type: 2,
                content: encodeURI('../client/clientSignRecord.html?clientId=' + id + '&clientName='+clientName)
            })
        },propertyRecord: function () {
            var id = getSelectedRow("#jqGrid");
            var clientName = $("#jqGrid").getRowData(id).clientName;
            if (id == null) {
                return;
            }
            openWindow({
                title: '资产记录',
                type: 2,
                content: encodeURI('../client/clientPropertyRecord.html?clientId=' + id + '&clientName='+clientName)
            })
        },
        reload: function (event) {
            vm.showClientList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            if(vm.q.clientType == '' || vm.q.clientType == undefined){
                delete $("#jqGrid").getGridParam().postData.clientType
            }
            if(vm.q.clientTel == '' || vm.q.clientTel == undefined){
                delete $("#jqGrid").getGridParam().postData.clientTel
            }
            if(vm.q.clientName == '' || vm.q.clientName == undefined){
                delete $("#jqGrid").getGridParam().postData.clientName
            }
            if(vm.q.clientFrom == '' || vm.q.clientFrom == undefined){
                delete $("#jqGrid").getGridParam().postData.clientFrom
            }
            if(vm.q.clientManagerId == '' || vm.q.clientManagerId == undefined){
                delete $("#jqGrid").getGridParam().postData.clientManagerId
            }
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'clientType': vm.q.clientType,'clientTel':vm.q.clientTel,
                    'clientName': vm.q.clientName,'clientFrom': vm.q.clientFrom,
                    'clientManagerId': vm.q.clientManagerId
                },
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
        },getClientInfo: function (row) {
            var clientId =row.id;
            var clientName =row.clientName;
            openWindow({
                title: '客户详情记录',
                type: 2,
                content: encodeURI('../client/clientInfo.html?clientId=' + clientId + '&clientName='+clientName)
            })
        },
    }
});