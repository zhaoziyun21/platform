$(function () {
    $("#jqGrid").Grid({
        url: '../client/publishClientList',
        autowidth:true,
        multiselect: false,//复选框
        postData: {
            clientType: "2"
        },
        colModel: [
            {label: '客户ID', name: 'clientId', index: "clientId", key: true, hidden: true},
            {label: '客户姓名', name: 'clientName', width: 75},
            // {label: '手机号', name: 'clientTel', width: 75},
            {
                label: '操作', width: 160, align: 'center', sortable: false, formatter: function (value, col, row) {
                    a=1;
                return "<button class='btn btn-info'  onclick='vm.secondKill(" + JSON.stringify(row) + ")'><i class='fa fa-info-circle'></i>&nbsp;抢人</button>";
                // return 123;
            }
            }]
    });
    // $("#jqGrid").jqGrid("setGridHeight", "auto");
});


var vm = new Vue({
    el: '#publicClient',
    data: {
        q: {
            tel: null
        },
        showList: true,
        title: null,
        clientTelRecord: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'clientTel': vm.q.tel},
                page: page
            }).trigger("reloadGrid");
        },
        secondKill: function (row) { //第三步：定义编辑操作
            if (row.id == null) {
                return;
            }

            confirm('确定要抢这个客户？', function () {
                vm.clientFollowRecord = {
                    id:row.id,
                    clientId:row.clientId,
                    clientName:row.clientName
                }
                Ajax.request({
                    type: "POST",
                    url: "../client/secondKill",
                    contentType: "application/json",
                    params: JSON.stringify(vm.clientFollowRecord),
                    successCallback: function (r) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        }
    }
});