<template>
  <div>
    <nav-bar></nav-bar>
    <img class="background" src="@/assets/bannerTop_new.png" alt="">
    <user-detail :user-info="userInfo"></user-detail>
    <div>
      <div v-for="item in videoInfoList" @click="onView(item)">
        <video-info-view :video-id="item.id" desc="点击观看视频"/>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "../components/NavBar";
import UserDetail from "../components/UserDetail";
import {
  Category,
  getUserInfo,
  getUserInfoById,
  getUserVideoList,
  getVideoByCategory,
  getVideoInfo,
  UserInfo
} from "../../util";
import VideoInfoView from "@/components/VideoInfoView";

export default {
  name: "UserInfo",
  components: {
    NavBar,
    UserDetail,
    VideoInfoView
  },
  data() {
    return {
      userInfo: new UserInfo(),
      videoInfoList: [],
      userId: -1
    }
  },
  created() {
    const userId = this.$route.query.userId
    if (userId !== undefined) {
      this.userId = userId
      getUserInfoById(this.userId).then(r => {
        this.userInfo = r
        getList(this.userId)
      })
    } else {
      getUserInfo().then(r => {
        this.userInfo = r
        getList(r.id)
      })
    }

    const _this = this

    async function getList(userId) {
      const userVideoList = await getUserVideoList(userId);
      userVideoList.forEach(it => {
        getVideoInfo(it).then(r=>{
          _this.videoInfoList.push(r)
        })
      })
    }
  },
  methods: {
    onView(item) {
      this.$router.push({
        path: '/videoPlay',
        query: {videoId: item.id}
      })
    }
  }
}
</script>

<style scoped>
.background {
  height: 36.11vw;
  width: 100%;
}
</style>