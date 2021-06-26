<template>
  <div>
    <nav-bar></nav-bar>
    <van-list
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
    >
      <div v-for='(item, index) in list' :key="index">
        <div @click="onView(item)">
          <video-info-view :video-id="item.id" desc="点击编辑信息"></video-info-view>
        </div>
      </div>
    </van-list>
  </div>
</template>

<script>
import NavBar from "../components/NavBar";
import {getVideoInfo} from "../../util";
import VideoInfoView from "@/components/VideoInfoView";

export default {
  name: "MyVideoList",
  components: {
    NavBar,
    VideoInfoView
  },
  data() {
    return {
      list: [],
      loading: false,
      finished: false,
    }
  },
  methods: {
    async onLoad() {
      const r = await this.$http.get('/getMyVideoList');
      const videoList = r.data.data
      for (let i = 0; i < (videoList.length > 10 ? 10 : videoList.length); i++) {
        const videoInfo = await getVideoInfo(videoList[i]);
        this.list.push(videoInfo);
      }

      this.loading = false;
      if (this.list.length >= videoList.length) {
        this.finished = true;
      }
    },
    onView(item) {
      this.$router.push({
        path: '/editVideoInfo',
        query: {
          videoId: item.id
        }
      })
    }
  }
}
</script>

<style scoped>

</style>