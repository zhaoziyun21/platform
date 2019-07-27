var clientId;
var clientName;
var clientPropertyRecordId;
$(function () {
    clientId = getQueryString("clientId");
    clientName = getQueryStringNew("clientName");
    var url = '../clientPropertyRecord/list';
    if (clientId) {
        url += '?clientId=' + clientId ;
    }
    $("#jqGrid").Grid({
        url: url,
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
            {label: '备注', name: 'remark', width: 75}]
    });
});

var vm = new Vue({
    el: '#clientPropertyRecord',
    data: {
        showList: true,
        title: null,
        roleList: {},
        clientPropertyRecord: {
        },ruleValidate: {
            propertyAmount: [
                { pattern: /^[0-9]+(\.([0-9]{1,3}))$|^[0-9]+$/,message: '签单金额只能是整数或者小数', trigger: 'change'}
            ]
        }
    },beforeCreate() {
        this.$nextTick(function () {
            vm.clientPropertyRecord = {
                clientId :getQueryString("clientId"),
                clientName :getQueryStringNew("clientName")};
            if(getQueryStringNew("clientPropertyRecordId") != undefined){
                Ajax.request({
                    url: "../clientPropertyRecord/info/" + getQueryStringNew("clientPropertyRecordId"),
                    async: false,
                    successCallback: function (r) {
                        vm.clientPropertyRecord = r.tblClientPropertyRecord;
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
            vm.showList = false;
            vm.title = "新增";
            vm.clientPropertyRecord = {
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
                url: "../clientPropertyRecord/info/" + id,
                async: true,
                successCallback: function (r) {
                    vm.clientPropertyRecord = r.tblClientPropertyRecord;
                }
            });

        },
        saveOrUpdate: function (event) {
            var url = vm.clientPropertyRecord.id == null ? "../clientPropertyRecord/save" : "../clientPropertyRecord/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.clientPropertyRecord),
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
        },
        reload: function (event) {
            parent.vm.reload();
            parent.vm.handleReset('formValidate');
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