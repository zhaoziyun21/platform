var clientId;
var clientName;
var clientLoanRecordId;
$(function () {
    clientId = getQueryString("clientId");
    clientName = getQueryStringNew("clientName");
    var url = '../clientLoanRecord/list';
    if (clientId) {
        url += '?clientId=' + clientId ;
    }
    $("#jqGrid").Grid({
        url: url,
        colModel: [
            {label: '贷款记录ID', name: 'id', index: "id", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            {label: '贷款名称', name: 'loanName', width: 75},
            {label: '贷款类型', name: 'loanType', width: 75},
            {label: '贷款总金额', name: 'loanAmount', width: 75},
            {label: '服务费点位', name: 'servicePoint', width: 75},
            {label: '按揭期数', name: 'mortgageNums', width: 75},
            {label: '按揭金额', name: 'mortgageAmount', width: 75},
            {label: '客户经理', name: 'clientManagerName', width: 75},
            {label: '备注', name: 'remark', width: 75}]
    });
});

var vm = new Vue({
    el: '#clientLoanRecord',
    data: {
        title: null,
        roleList: {},
        clientLoanRecord: {
        },
        ruleValidate: {
            loanAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '贷款总金额只能是整数或者小数', trigger: 'change'}
            ],
            servicePoint: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '服务费点位只能是整数或者小数', trigger: 'change'}
            ],
            mortgageNums: [
                { pattern: /^[0-9]+$/,message: '按揭期数只能是整数', trigger: 'change'}
            ],
            mortgageAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '按揭金额只能是整数或者小数', trigger: 'change'}
            ]




        }
    },beforeCreate() {
        this.$nextTick(function () {
            vm.clientLoanRecord = {
                clientId :getQueryString("clientId"),
                clientName :getQueryStringNew("clientName")};
            if(getQueryStringNew("clientLoanRecordId") != undefined){
                Ajax.request({
                    url: "../clientLoanRecord/info/" + getQueryStringNew("clientLoanRecordId"),
                    async: true,
                    successCallback: function (r) {
                        vm.clientLoanRecord = r.tblClientLoanRecord;
                    }
                });
            }
        })
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.title = "新增";
            vm.clientLoanRecord = {
                clientId :clientId,
                clientName :clientName};

        },
        update: function () {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }

            vm.title = "修改";

            Ajax.request({
                url: "../clientLoanRecord/info/" + id,
                async: true,
                successCallback: function (r) {
                    vm.clientLoanRecord = r.tblClientLoanRecord;
                }
            });

        },
        saveOrUpdate: function (event) {
            var url = vm.clientLoanRecord.id == null ? "../clientLoanRecord/save" : "../clientLoanRecord/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.clientLoanRecord),
                contentType: "application/json",
                type: 'POST',
                successCallback: function () {
                    alert('操作成功', function (index) {
                        window.parent.vm.reload();
                        var index  = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    });
                }
            });
        },returnBack: function () {
            window.parent.vm.reload();
            var index  = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        }
    }
});