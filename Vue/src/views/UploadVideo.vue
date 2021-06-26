<template>
  <div>
    <nav-bar></nav-bar>
    <van-empty description="投稿" image="https://img01.yzcdn.cn/vant/custom-empty-image.png">
      <van-uploader style="display: flex;justify-content: center" accept="video/mp4" :after-read="afterRead">
        <van-button icon="plus" type="primary">上传视频</van-button>
      </van-uploader>
      <div style="margin-bottom: 2.78vw;"></div>
      <van-button icon="ellipsis" type="primary" @click="$router.push('/myVideoList')">视频列表</van-button>
    </van-empty>
  </div>
</template>

<script>
import NavBar from "../components/NavBar";
import {Toast} from "vant";
import {checkLogin} from "../../util";

export default {
  name: "UploadVideo",
  components: {NavBar},
  created() {
    checkLogin()
  },
  methods: {
    async afterRead(file) {
      if (!file.content.toString().startsWith("data:video/mp4")) {
        Toast.fail('只能上传mp4')
        return
      }

      this.$http.post('/createVideo').then(r => {
        this.videoId = r.data.data
        const data = new FormData()
        data.append('file', file.file)
        data.append('videoId', this.videoId)
        this.$http.put('/uploadVideo', data, {
          headers: {'Content-Type': 'multipart/form-data'}
        }).then(r1 => Toast({
          message: r1.data.msg,
          type: r1.data.status
        }))
      })
    }
  }
}
</script>

<style scoped>

</style>