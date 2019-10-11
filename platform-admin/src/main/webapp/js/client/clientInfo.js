var clientId;
var clientName;
var page = 1;
$(function () {
    var clientFollowRecordUrl = '../clientFollowRecord/list';
    if (clientId) {
        clientFollowRecordUrl += '?clientId=' + clientId ;
    }

    $("#clientFollowRecordJqGrid").Grid({
        url: clientFollowRecordUrl,
        multiselect: false,//复选框
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '客户名字', name: 'clientName', index: 'clientName', width: 80},
            {label: '跟单记录', name: 'followRemark', index: 'followRemark', width: 80},
            {label: '客户经理', name: 'clientManagerName', index: 'clientManagerName', width: 80},
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            { label: '操作',  width: 80, formatter: function (value, col, row) {
                return   "<a  onclick='vm.updateClientFollowRecord(" + JSON.stringify(row) + ")'>修改</a>";
            }}]
    });
    $("#clientFollowRecordJqGrid").jqGrid("setGridHeight", "auto");
});


var vm = new Vue({
    el: '#client',
    data: {
        client: {
        },
        user:{

        },ruleValidate: {
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
    beforeCreate() {
        clientId = getQueryString("clientId");
        clientName = getQueryStringNew("clientName");
        page = getQueryStringNew("page");
        this.$nextTick(function () {
                Ajax.request({
                    url: "../client/info/" + clientId+"?clientId="+clientId,
                    async: false,
                    successCallback: function (r) {
                        vm.client = r.client;
                        vm.user = r.user;
                    }
                });
        })
    },
    methods: {
        reload: function (event) {
            $("#clientFollowRecordJqGrid").jqGrid('setGridParam', {
                page: $("#clientFollowRecordJqGrid").jqGrid('getGridParam', 'page')
            }).trigger("reloadGrid");
        },
        addClientFollowRecord: function () {
            openWindow({
                title: '新增跟单记录',
                type: 2,
                content: encodeURI('../client/clientFollowRecord.html?clientId=' + clientId + '&clientName='+clientName)
            })
        },
        updateClientFollowRecord: function (row) {
            var id =row.id;
            var clientId =row.clientId;
            var clientName =row.realName;
            openWindow({
                title: '修改跟单记录',
                type: 2,
                content: encodeURI('../client/clientFollowRecord.html?clientId=' + clientId + '&clientName='+clientName + '&clientFollowRecordId='+ id)
            })
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },returnBack:function(){
            var url = "../client/client.html?page="+page;
            location.href = url;
        },handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },saveOrUpdate: function () {
            var url = vm.client.id == null ? "../client/save" : "../client/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.client),
                contentType: "application/json",
                type: 'POST',
                successCallback: function () {
                    alert('操作成功', function (index) {
                        vm.returnBack();
                    });
                }
            });
        }
    }
});