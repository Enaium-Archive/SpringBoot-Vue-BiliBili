<template>
  <div>
    <nav-bar></nav-bar>
    <van-tabs @click="onClick">
      <van-tab v-for="item in category" :title="item.title"/>
    </van-tabs>
    <div>
      <div v-for="item in videoInfoList" @click="onView(item)">
        <video-info-view :video-id="item.id" desc="点击观看视频"/>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "../components/NavBar";
import VideoInfoView from "@/components/VideoInfoView";
import {Category, getVideoByCategory} from "../../util";

export default {
  name: "Home",
  components: {
    NavBar,
    VideoInfoView
  },
  data() {
    return {
      category: [],
      videoInfoList: [],
      selectCategory: new Category(0, "")
    }
  },
  created() {
    this.$http.get('/static/getCategory').then(r => this.category = r.data.data)
    getVideoByCategory(this.selectCategory.id).then(r => {
      if (r.length !== 0) {
        this.videoInfoList = r
      } else {
        this.videoInfoList = []
      }
    })
  },
  methods: {
    onClick(name, title) {
      this.selectCategory = new Category(name + 1, title)
      getVideoByCategory(this.selectCategory.id).then(r => {
        if (r.length !== 0) {
          this.videoInfoList = r
        } else {
          this.videoInfoList = []
        }
      })
    },
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

</style>