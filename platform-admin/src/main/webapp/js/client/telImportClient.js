

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        roleList: {},
        clientTels: '',
        ruleValidate: {
            clientTels: [
                {required: true, message: '手机号不能为空', trigger: 'blur'}
            ]
        }
    },
    methods: {
        reload: function (event) {

        },
        saveOrUpdate: function (event) {
            var url = "../client/batchSave";
            Ajax.request({
                url: url,
                params: {'clientTels': vm.clientTels},
                type: 'POST',
                successCallback: function () {
                    alert('操作成功', function (index) {
                        var index = parent.layer.getFrameIndex(window.name);
                        var layer = parent.layer === undefined ? layui.layer : parent.layer;
                        parent.vm.reload();
                        layer.close(index);
                    });
                }
            });
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        }
    }
});