<!DOCTYPE html>
<#assign contextPath = request.contextPath>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>bljweb</title>
    <script src="https://cdn.bootcss.com/vue/2.6.10/vue.min.js"></script>
    <!-- Vue-resource 1.5.1 -->
    <script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
    <script src="https://cdn.bootcss.com/element-ui/2.7.2/index.js"></script>
    <link href="https://cdn.bootcss.com/element-ui/2.7.2/theme-chalk/index.css" rel="stylesheet">
</head>
<body class="layout">
<div id="myapp">
    <el-row>
        <el-col :span="12" :offset="6">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="设备IP/设备名">
                    <el-input v-model="form.server"></el-input>
                </el-form-item>
                <el-form-item label="系统账号">
                    <el-input v-model="form.account"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item label="信息">
                    <el-input v-model="form.mess"></el-input>
                </el-form-item>
                <el-button size="mini" round @click="query">查询</el-button>
                <el-button size="mini" round @click="add">新增</el-button>
                <el-button size="mini" round @click="del">删除</el-button>
            </el-form>
        </el-col>
    </el-row>
</div>
<script>
    new Vue({
        el: '#myapp',
        data: {
            form: {
                server: '',
                account: '',
                password: '',
                mess: ''
            }
        },
        methods: {
            query: function () {
                var vm = this;
                vm.$http.post('${contextPath}/query', {
                    server: vm.form.server,
                    account: vm.form.account
                }, {emulateJSON: true}).then(
                    function (res) {
                        vm.form.password = res.body.password;
                        if (vm.form.password){
                            alert("成功查询");
                        }else {
                            alert("无密码")
                        }
                    },
                    function (res) {
                    });
            },
            add: function () {
                //1.发起请求，给xx主机新增xx账号密码  post
                //2.后台判断是否能够找到唯一一台主机
                //2.1 找不到：告诉用户无此主机  return resultBean status=false,message=无此主机
                //2.2 找到唯一一条记录
                //2.2.1 判断提交的账号是否已有密码  return resultBean status=true,message=该账号已有密码，是否覆盖？
                //2.2.2 不覆盖：到此结束
                //2.2.3 覆盖：用新密码覆盖老密码  重新发起一次post请求，带一个参数，force=true,后台直接更新密码
                //2.2.3.1 更新完密码后，return resultBean status=true,message=null

                var vm = this;
                vm.$http.post('${contextPath}/', {
                    server: vm.form.server,
                    account: vm.form.account,
                    password: vm.form.password
                }, {emulateJSON: true}).then(
                    function (res) {
                        //判断返回的rb
                        //res --> bodyText  body
                        //res --> status 200,404
                        //res --> message 200/404 信息
                        //res -->
                        if(!res.body.status){
                            alert(res.body.message);

                        }else{
                            if(res.body.message===""){
                                alert('新增成功！');
                            }else{
                                alert(res.body.message+"123");
                            }
                        }
                    },
                    function (res) {
                    });
            },
            del: function () {
                var vm = this;
                vm.$http.post('${contextPath}/delete', {
                    server: vm.form.server,
                    account: vm.form.account
                }, {emulateJSON: true}).then(
                    function (res) {
                        if (res.status === 200){
                            alert("密码删除成功")
                        }
                    },
                    function (res) {
                    });
            }
        }
    });
</script>
</body>
</html>