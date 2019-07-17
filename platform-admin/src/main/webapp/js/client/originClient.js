$(function () {
    $("#jqGrid").Grid({
        url: '../clientTelRecord/list',
        colModel: [
            {label: '客户ID', name: 'clientId', index: "clientId", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            {label: '手机号', name: 'tel', width: 75},
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
            tel:""
        },
        showList: true,
        title: null,
        roleList: {},
        clientTelRecord: {},
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
                    'tel':vm.q.tel
                },
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        importRecord: function () {
            //待定
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