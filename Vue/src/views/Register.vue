<template>
  <div>
    <login-top middle-top="注册BiliBili">
      <div slot="right" @click="$router.push('/login')">登录</div>
    </login-top>

    <login-text label="昵称" :rule="len" style="margin: 4vw 0" placeholder="请输入昵称6-16位"
                @inputChange="content => this.nickname = content"/>
    <login-text label="用户名" :rule="lenAndAz" placeholder="请输入用户名6-16位仅数字大小写"
                @inputChange="content => this.username = content"/>
    <login-text label="密码" :rule="lenAndAz" type="password"
                placeholder="请输入密码6-16仅数字大小写"
                @inputChange="content => this.password = content"/>
    <login-button title="注册" @registerSubmit="onRegister"/>

  </div>
</template>

<script>
import LoginTop from "../components/LoginTop";
import LoginText from "../components/LoginText";
import LoginButton from "../components/LoginButton";
import {Toast} from "vant";
import {len, lenAndAz} from "../../util";

export default {
  name: "Register",
  components: {
    LoginTop,
    LoginText,
    LoginButton
  },
  methods: {
    async onRegister() {
      if (this.nickname.match(this.len) && this.username.match(this.lenAndAz) && this.password.match(this.lenAndAz)) {
        const val = await this.$http.post('/register', {
          username: this.username,
          password: this.password,
          nickname: this.nickname
        })
        const ret = val.data;
        Toast({
          message: ret.msg,
          type: ret.status
        })
      } else {
        Toast.fail("输入内容有误")
      }
    }
  },
  data() {
    return {
      nickname: '',
      username: '',
      password: '',
      len: len,
      lenAndAz: lenAndAz
    }
  }
}
</script>

<style scoped>

</style>