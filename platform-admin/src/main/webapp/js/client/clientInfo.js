var clientId;
var clientName;
var clientInfo = {};
$(function () {
    clientId = getQueryString("clientId");
    clientName = getQueryStringNew("clientName");

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

    var clientLoanRecordUrl = '../clientLoanRecord/list';
    if (clientId) {
        clientLoanRecordUrl += '?clientId=' + clientId ;
    }
    $("#clientLoanRecordJqGrid").Grid({
        url: clientLoanRecordUrl,
        multiselect: false,//复选框
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
            {label: '备注', name: 'remark', width: 75},
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            { label: '操作',  width: 80, formatter: function (value, col, row) {
                return   "<a  onclick='vm.updateClientLoanRecord(" + JSON.stringify(row) + ")'>修改</a>";
            }}]
    });
    $("#clientLoanRecordJqGrid").jqGrid("setGridHeight", "auto");

    var clientPropertyRecordUrl = '../clientPropertyRecord/list';
    if (clientId) {
        clientPropertyRecordUrl += '?clientId=' + clientId ;
    }
    $("#clientPropertyRecordJqGrid").Grid({
        url: clientPropertyRecordUrl,
        multiselect: false,//复选框
        colModel: [
            {label: '资产记录ID', name: 'id', index: "id", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            {label: '资产名称', name: 'propertyName', width: 75},
            {label: '资产类型', name: 'propertyType', width: 75},
            {label: '资产金额', name: 'propertyAmount', width: 75},
            {label: '购买时间', name: 'buyTime', width: 75, formatter: function (value) {
                return value == null ? '' : value.split(' ')[0];
            }},
            {label: '客户经理', name: 'clientManagerName', width: 75},
            {label: '备注', name: 'remark', width: 75},
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            { label: '操作',  width: 80, formatter: function (value, col, row) {
                return   "<a  onclick='vm.updateClientPropertyRecord(" + JSON.stringify(row) + ")'>修改</a>";
            }}]
    });
    $("#clientPropertyRecordJqGrid").jqGrid("setGridHeight", "auto");

    var clientSignRecordUrl = '../clientSignRecord/list';
    if (clientId) {
        clientSignRecordUrl += '?clientId=' + clientId ;
    }
    $("#clientSignRecordJqGrid").Grid({
        url: clientSignRecordUrl,
        multiselect: false,//复选框
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
            {label: '备注', name: 'remark', width: 75},
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            { label: '操作',  width: 80, formatter: function (value, col, row) {
                return   "<a  onclick='vm.updateClientSignRecord(" + JSON.stringify(row) + ")'>修改</a>";
            }}]
    });
    $("#clientSignRecordJqGrid").jqGrid("setGridHeight", "auto");
});


var vm = new Vue({
    el: '#client',
    data: {
        client: {
        }
    },
    beforeCreate() {
        this.$nextTick(function () {
                Ajax.request({
                    url: "../client/info/" + clientId+"?clientId="+clientId,
                    async: false,
                    successCallback: function (r) {
                        vm.client = r.client;
                    }
                });
        })
    },
    methods: {
        reload: function (event) {
            $("#clientFollowRecordJqGrid").jqGrid('setGridParam', {
                page: $("#clientFollowRecordJqGrid").jqGrid('getGridParam', 'page')
            }).trigger("reloadGrid");
            $("#clientLoanRecordJqGrid").jqGrid('setGridParam', {
                page: $("#clientLoanRecordJqGrid").jqGrid('getGridParam', 'page')
            }).trigger("reloadGrid");
            $("#clientSignRecordJqGrid").jqGrid('setGridParam', {
                page: $("#clientSignRecordJqGrid").jqGrid('getGridParam', 'page')
            }).trigger("reloadGrid");
            $("#clientPropertyRecordJqGrid").jqGrid('setGridParam', {
                page: $("#clientPropertyRecordJqGrid").jqGrid('getGridParam', 'page')
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
        addClientLoanRecord: function () {
            openWindow({
                title: '新增贷款记录',
                type: 2,
                content: encodeURI('../client/clientLoanRecord.html?clientId=' + clientId + '&clientName='+clientName)
            })
        },
        updateClientLoanRecord: function (row) {
            var id =row.id;
            var clientId =row.clientId;
            var clientName =row.realName;
            openWindow({
                title: '修改贷款记录',
                type: 2,
                content: encodeURI('../client/clientLoanRecord.html?clientId=' + clientId + '&clientName='+clientName + '&clientLoanRecordId='+ id)
            })
        },
        addClientPropertyRecord: function () {
            openWindow({
                title: '新增资产记录',
                type: 2,
                content: encodeURI('../client/clientPropertyRecord.html?clientId=' + clientId + '&clientName='+clientName)
            })
        },
        updateClientPropertyRecord: function (row) {
            var id =row.id;
            var clientId =row.clientId;
            var clientName =row.realName;
            openWindow({
                title: '修改资产记录',
                type: 2,
                content: encodeURI('../client/clientPropertyRecord.html?clientId=' + clientId + '&clientName='+clientName + '&clientPropertyRecordId='+ id)
            })
        },
        addClientSignRecord: function () {
            openWindow({
                title: '新增签单记录',
                type: 2,
                content: encodeURI('../client/clientSignRecord.html?clientId=' + clientId + '&clientName='+clientName)
            })
        },
        updateClientSignRecord: function (row) {
            var id =row.id;
            var clientId =row.clientId;
            var clientName =row.realName;
            openWindow({
                title: '修改签单记录',
                type: 2,
                content: encodeURI('../client/clientSignRecord.html?clientId=' + clientId + '&clientName='+clientName + '&clientSignRecordId='+ id)
            })
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        }
    }
});