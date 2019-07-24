var clientId;
var clientName;
$(function () {
    clientId = getQueryString("clientId");
    clientName = getQueryStringNew("clientName");
    var url = '../clientSignRecord/list';
    if (clientId) {
        url += '?clientId=' + clientId ;
    }
    $("#jqGrid").Grid({
        url: url,
        colModel: [
            {label: '签单记录ID', name: 'id', index: "id", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            {label: '签单类型', name: 'signType', width: 75},
            {label: '签单金额', name: 'signAmount', width: 75},
            {label: '服务费点位', name: 'servicePoint', width: 75},
            {label: '按揭期数', name: 'mortgageNums', width: 75},
            {label: '按揭金额', name: 'mortgageAmount', width: 75},
            {label: '客户经理姓名', name: 'clientManagerName', width: 75},
            {label: '签单日期', name: 'createTime', width: 75},
            {label: '备注', name: 'remark', width: 75}]
    });
});

var vm = new Vue({
    el: '#clientSignRecord',
    data: {
        showList: true,
        title: null,
        roleList: {},
        clientSignRecord: {
        },ruleValidate: {
            signAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '签单金额只能是整数或者小数', trigger: 'change'}
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
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.clientSignRecord = {
                clientId :clientId,
                clientName :clientName};

        },
        update: function () {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            Ajax.request({
                url: "../clientSignRecord/info/" + id,
                async: true,
                successCallback: function (r) {
                    vm.clientSignRecord = r.tblClientSignRecord;
                }
            });

        },
        saveOrUpdate: function (event) {
            var url = vm.clientSignRecord.id == null ? "../clientSignRecord/save" : "../clientSignRecord/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.clientSignRecord),
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
                // postData: {'clientTel': vm.q.clientTel},
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