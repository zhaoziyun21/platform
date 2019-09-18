var clientId;
var clientName;
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

        }
    },
    beforeCreate() {
        clientId = getQueryString("clientId");
        clientName = getQueryStringNew("clientName");
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
        }
    }
});