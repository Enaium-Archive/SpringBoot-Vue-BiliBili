<template>
  <div>
    <img :src="videoInfo.picUrl" style="width: 97.22vw;height: 55.56vw;border-radius: 3%" alt="">
    <p>{{ videoInfo.title }}</p>
  </div>
</template>

<script>
import {getVideoInfo, VideoInfo} from "../../util";

export default {
  name: "VideoInfoView",
  props: ['videoId', 'desc'],
  data() {
    return {
      videoInfo: new VideoInfo()
    }
  },
  created() {
    getVideoInfo(this.videoId).then(r => {
      this.videoInfo = r
    })
  }
}
</script>

<style scoped>

</style>