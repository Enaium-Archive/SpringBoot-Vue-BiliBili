<template>
  <div>
    <nav-bar></nav-bar>
    <video id="myVideo" class="video-js vjs-default-skin"/>
    <p>标题 {{ videoInfo.title }}</p>
    <p>描述 {{ videoInfo.description }}</p>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import {VideoInfo} from "../../util";

export default {
  name: "VideoPlay",
  components: {
    NavBar
  },
  data() {
    return {
      videoInfo: new VideoInfo()
    }
  },
  created() {
    this.$http.get('/getVideoUrl?videoId=' + this.$route.query.videoId).then(r => {
      this.$video("myVideo", {
        controls: true,
        preload: "auto",
        fluid: true,
        width: "350px",
        height: "300px",
        sources: [
          {
            src: r.data.data,
            type: 'video/mp4'
          }
        ]
      });
    })
    this.$http.get('/static/getVideoInfo?videoId=' + this.$route.query.videoId).then(r => this.videoInfo = r.data.data)
  }
}
</script>

<style scoped>

</style>