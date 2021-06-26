<template>
  <div id="app">
    <router-view/>
    <van-tabbar route>
      <van-tabbar-item replace to="/" icon="home-o">主页</van-tabbar-item>
      <van-tabbar-item replace to="/uploadVideo" icon="upgrade">投稿</van-tabbar-item>
      <van-tabbar-item replace to="/editUserInfo" icon="setting-o">设置信息</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<style>
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  background-color: #f4f4f4;
}

p {
  margin: 0;
  padding: 0;
}
</style>
