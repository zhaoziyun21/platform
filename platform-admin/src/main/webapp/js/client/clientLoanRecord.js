var clientId;
var clientName;
$(function () {
    clientId = getQueryString("clientId");
    clientName = getQueryStringNew("clientName");
    var url = '../clientFollowRecord/list';
    if (clientId) {
        url += '?clientId=' + clientId ;
    }
    $("#jqGrid").Grid({
        url: '../clientLoanRecord/list',
        colModel: [
            {label: '贷款记录ID', name: 'id', index: "id", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            {label: '贷款名称', name: 'clientTel', width: 75},
            {label: '贷款类型', name: 'loanType', width: 75},
            {label: '备注', name: 'remark', width: 75}]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        roleList: {},
        clientFollowRecord: {
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.clientFollowRecord = {};

        },
        update: function () {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }

            vm.showList = false;
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
                        vm.reload();
                    });
                }
            });
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