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
            {label: '客户类型', name: 'clientStar', width: 75, formatter: function (value) {
                    switch (value){
                        case '1':
                            value = "1星";break;
                        case '2':
                            value = "2星";break;
                        case '3':
                            value = "3星";break;
                        case '4':
                            value = "4星";break;
                        case '5':
                            value = "5星";break;
                        default:value = "暂无";
                    }
                    return value;
                }},
            {label: '申请金额', name: 'applyAmount', width: 75,formatter: function (value) {
                    if(value  > 0){
                        var str = value+'万';
                        return str;
                    }else{
                        return "";
                    }

                }},
            {
                label: '创建时间', name: 'createTime', index: "createTime", width: 80, formatter: function (value) {
                    return transDate(value);
                }
            },
            {
                label: '操作', width: 160, align: 'center', sortable: false, formatter: function (value, col, row) {
                    a=1;
                return "<button class='btn btn-info'  onclick='vm.secondKill(" + JSON.stringify(row) + ")'><i class='fa fa-info-circle'></i>&nbsp;抢人</button>";
            }
            }]
    });
    $("#jqGrid").jqGrid("setGridHeight", "auto");
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