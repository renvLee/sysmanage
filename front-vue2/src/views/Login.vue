<template>
  <div class="main-container">
    <div class="login-container">
      <el-form
          ref="loginForm"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
      >
        <h3 class="title">后台管理系统</h3>
        <el-form-item prop="username">
          <el-input
              v-model="loginForm.username"
              type="text"
              placeholder="账号"
              prefix-icon="el-icon-user-solid"
              @keyup.enter.native="handleLogin"
          >
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              prefix-icon="el-icon-s-help"
              placeholder="密码"
              @keyup.enter.native="handleLogin"
          >
          </el-input>
        </el-form-item>
        <el-checkbox v-model="rememberMe" name="remember-me" style="margin-bottom: 25px">记住密码
        </el-checkbox>
        <el-form-item style="width: 100%">
          <el-button
              :loading="loading"
              size="medium"
              style="width: 100%;background-color: #01763a;color: #ffffff"
              @click.native.prevent="handleLogin"
              v-loading.fullscreen.lock="loading"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
        </el-form-item>
<!--        <el-form-item label="超级管理员:" >-->
<!--          <el-tag effect="dark" type="success">nanjustar</el-tag>-->
<!--          <el-tag effect="dark" style="margin-left: 20px">123456</el-tag>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="普通管理员:" style="margin-top: -10px">-->
<!--          <el-tag effect="dark" type="success"> admin</el-tag>-->
<!--          <el-tag effect="dark" style="margin-left: 20px">123456</el-tag>-->
<!--        </el-form-item>-->
      </el-form>
    </div>
    <div class="space-left-parent">
      <ul class="leftBox">
        <li class="l1 transition translateX_100">
          <span class="ico"></span>
          <h3 class="h3">现在时间是</h3>
          <p class="date">{{ currentTime }}</p>
        </li>
        <li class="l2 transition translateX_100" edit_params=""><h1 class="h1">员工版</h1>
          <h2 class="h2">欢迎你，请选择要进入的功能模块</h2></li>
        <li class="l3 transition translateY-100" edit_params=""><a href="h" target="_blank">
          <span class="ico"><img src="@/assets/logo/computerIcon.png"></span>
          <h3 class="h3">院内网入口</h3>
          <p class="p">协和医院内部网络系统</p>
           </a></li>
        <li class="l4 transition translateY_100" edit_params=""><a href="https://mail.pumch.cn" target="_blank">
          <span class="ico"><img src="@/assets/logo/emailIcon.png"></span>
          <h3 class="h3">协和邮箱</h3>
          <p class="p">收取与发送邮件，请进入</p>
          </a></li>
        <li class="l5 transition translateX-100" edit_params=""><a href="http://newoa.pumch.local" target="_blank">
          <span class="ico"><img src="@/assets/logo/oaIcon.png"></span>
          <h3 class="h3">OA系统</h3>
          <p class="p">工作流程　知识管理<br>
            沟通交流　辅助办公</p>
          </a></li>
        <li class="l6 transition translateX-100" edit_params=""><img title="" alt="" src="@/assets/logo/doctor1.jpg"></li>
        <li class="l7 transition translateX_100" edit_params=""><img title="" alt="" src="@/assets/logo/doctor1.jpg"></li>
        <li class="l8 transition translateY_100" edit_params=""><img title="" alt="" src="@/assets/logo/doctor2.jpg"></li>
      </ul>
      <div class="link">
        <div class="wrap cf">
          <span class="l">友情链接：</span>
          <div class="a">
            <a href="http://www.nhc.gov.cn/" target="_blank">中华人民共和国国家卫生健康委员会</a>
            <a href="http://wjw.beijing.gov.cn/" target="_blank">北京市卫生健康委员会</a>
            <a href="http://www.cams.ac.cn/" target="_blank">中国医学科学院</a>
            <a href="http://www.pumf.org.cn/" target="_blank">北京协和医学基金会</a>
          </div>
        </div>
      </div>
    </div>
    <Vcode
        :imgs="captchaImgs"
        :show="isCaptchaShow"
        @success="captchaSuccess"
        @fail="closeCaptcha"
    />
  </div>
</template>

<script>
import Vcode from "vue-puzzle-vcode";
import {messageInfo} from "@/utils/MessageInfo";
import {Login} from "@/api/login/loginApi";
import store from '@/store/index'
import {addDRouter, generateChildRoutes, initMenu} from '@/utils/menu';
import {getRequestWithToken} from "@/utils/request";

export default {
  name: "Login",
  components:{
    Vcode
  },
  data() {
    return {
      currentTime:'',
      captchaImgs:['http://m.lengde.top/2024/01/06/20240106205433.jpg','http://m.lengde.top/2024/01/06/20240106205510.jpg'],
      isCaptchaShow: false, // 验证码模态框是否出现
      loginForm: {
        username: "sg",
        password: "1234",
      },
      rememberMe: false,
      loginRules: {
        username: [
          {required: true, trigger: "blur", message: "请输入您的账号"},
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"},
        ],
      },
      loading: false,
    };
  },
  mounted() {
    // 在组件挂载后，启动定时器每秒更新一次时间
    this.updateTime();
    setInterval(this.updateTime, 1000);
  },
  methods: {
    updateTime() {
      const now = new Date();
      const formattedTime = this.formatTime(now);
      this.currentTime = formattedTime;
    },
    formatTime(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    closeCaptcha(){
      this.isCaptchaShow=false;
      messageInfo({type:"error",message:"验证失败，请重试"})
    },
    captchaSuccess(){
      this.isCaptchaShow=false;
      var that = this;
      this.loading = true;
      Login(that.loginForm).then((res) => {
        if (res.status) {
          window.localStorage.setItem("token", res.data.token);
          if(store.state.menuList.length===0) {
            console.log("调用 initMenu")
            initMenu(store);
          }
          if (store.state.userInfo.length===0) {
              console.log("调用 getInfo")
              getRequestWithToken('/getInfo',res.data.token)
                  .then(res => {
                      store.dispatch('setUserInfo', res.data)
                  });
          }
          that.loading = false;
          addDRouter();
          that.$router.replace("/home");
          messageInfo({message: "登陆成功啦！"});
        } else {
          that.loading = false;
        }
      });
    },
    handleLogin() {
      this.isCaptchaShow=true;
    },
  },
};
</script>

<style scoped>
@import "~@/assets/styles/login.css";
.main-container {
  position: relative;
  width: 100%;
  height: 100%;
}

.login-container {
  z-index: 1;
  position: absolute;
  right: 0;
  height: 100%;
  width: 30%;
  background-color: #ffffff;
  display: flex;
  /*justify-content: center;*/
  /*align-items: center;*/
  border-radius: 6px;
  box-shadow: 2px 3px 3px #888888;
  opacity: 0.9;
}

.login-form {
  position: absolute;
  margin-top: 40%;
  width: 85%;
}

.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
  font-size: 26px;
}

.space-left-parent {
  position: absolute;
  left: 0;
  width: 70%;
  height: 100%;
}

</style>