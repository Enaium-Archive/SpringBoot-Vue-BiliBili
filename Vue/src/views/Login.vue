<template>
  <div>
    <login-top middle-top="登录BiliBili">
      <div slot="right" @click="$router.push('/register')">注册</div>
    </login-top>

    <login-text label="用户名" rule="^[A-z]^[A-Za-z0-9-]{6,16}" placeholder="请输入用户名6-16位仅数字大小写"
                @inputChange="content => this.username = content"/>
    <login-text label="密码" rule="^[A-z]^[A-Za-z0-9-]{6,16}" type="password" placeholder="请输入密码6-16仅数字大小写"
                @inputChange="content => this.password = content"/>
    <login-button title="登录" @registerSubmit="onLogin"/>
  </div>
</template>

<script>
import LoginTop from "../components/LoginTop";
import LoginText from "../components/LoginText";
import LoginButton from "../components/LoginButton";
import {Toast} from "vant";

export default {
  name: "Login",
  components: {
    LoginTop,
    LoginText,
    LoginButton
  },
  methods: {
    async onLogin() {
      if (this.username && this.password) {
        const val = await this.$http.post('/login', {
          username: this.username,
          password: this.password
        })
        const ret = val.data;
        Toast({
          message: ret.msg,
          type: ret.status
        })
        if (ret.status === 'success') {
          localStorage.setItem("1145141919810", ret.data)
        }
      }
    }
  },
  data() {
    return {
      username: '',
      password: ''
    }
  }
}
</script>

<style scoped>

</style>