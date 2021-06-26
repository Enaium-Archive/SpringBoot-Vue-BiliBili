<template>
  <div class="NavBar">
    <div class="logo">
      <img src="../assets/logo.png" @click="$router.push('/')" alt="">
    </div>
    <div>
      <van-search class="search" placeholder="搜索"/>
    </div>
    <div>
      <img src="../assets/avatar.jpg" @click="$router.push('/login')" alt="" v-if="!isLogin">
      <img :src="userInfo.avatarUrl" @click="$router.push('/userInfo')" alt="" v-else>
      <div>
        <p>下载APP</p>
      </div>
    </div>
  </div>
</template>

<script>

import {getUserInfo, isLogin, UserInfo} from "../../util";

export default {
  name: "NavBar",
  data() {
    return {
      isLogin: false,
      userInfo: new UserInfo()
    }
  },
  created() {
    isLogin().then(isLoginIt => {
      this.isLogin = isLoginIt
      if (isLoginIt) {
        getUserInfo().then(userInfoIt => {
          this.userInfo = userInfoIt
        })
      }
    })
  }
}
</script>

<style lang="less">
.NavBar {
  height: 12.5vw;
  background-color: white;
  display: flex;

  .logo {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 33.33vw;

    img {
      width: 50%;
    }
  }

  div:nth-child(2) {
    flex: 1;
    display: flex;
    align-items: center;

    .search {
      margin: 0;
      padding: 0;
    }
  }

  div:nth-child(3) {
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 24px;
      height: 24px;
    }

    p {
      padding: 1.39vw 2.78vw;
      margin: 0 1.39vw;
      background-color: #ff9db5;
      color: white;
      font-size: 3.61vw;
      border-radius: 1.39vw;
    }
  }
}
</style>