var clientId;
var clientName;
$(function () {
    clientId = getQueryString("clientId");
    clientName = getQueryStringNew("clientName");
    var url = '../clientFollowRecord/list';
    if (clientId) {
        url += '?clientId=' + clientId ;
    }

    $("#clientFollowRecordJqGrid").Grid({
        url: url,
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '客户名字', name: 'clientName', index: 'clientName', width: 80},
            {label: '跟单记录', name: 'followRemark', index: 'followRemark', width: 80},
            {label: '客户经理', name: 'clientManagerName', index: 'clientManagerName', width: 80},
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 80, formatter: function (value) {
                    return transDate(value);
                }
            }]
    });
});

var vm = new Vue({
    el: '#clientFollowRecord',
    data: {
        showClientFollowRecordList: true,
        title: null,
        clientFollowRecord: {
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showClientFollowRecordList = false;
            vm.title = "新增";
            vm.clientFollowRecord = {
                clientId :clientId,
                clientName :clientName};
        },
        update: function (event) {
            var id = getSelectedRow("#clientFollowRecordJqGrid");
            if (id == null) {
                return;
            }
            vm.showClientFollowRecordList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.clientFollowRecord.id == null ? "../clientFollowRecord/save" : "../clientFollowRecord/update";

            Ajax.request({
                type: "POST",
                url: url,
                contentType: "application/json",
                params: JSON.stringify(vm.clientFollowRecord),
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows("#clientFollowRecordJqGrid");
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {

                Ajax.request({
                    type: "POST",
                    url: "../usercoupon/delete",
                    contentType: "application/json",
                    params: JSON.stringify(ids),
                    successCallback: function (r) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        getInfo: function (id) {
            Ajax.request({
                url: "../clientFollowRecord/info/" + id,
                async: true,
                successCallback: function (r) {
                    vm.clientFollowRecord = r.tblClientFollowRecord;
                }
            });
        },
        reload: function (event) {
            vm.showClientFollowRecordList = true;
            var page = $("#clientFollowRecordJqGrid").jqGrid('getGridParam', 'page');
            $("#clientFollowRecordJqGrid").jqGrid('setGridParam', {
                // postData: {'userName': vm.q.userName, 'couponName': vm.q.couponName},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        }
    }
});