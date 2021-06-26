<template>
  <div>
    <div style="margin-bottom: 2.78vw;">
      <nav-bar></nav-bar>
    </div>
    <div class="uploadPic">
      <van-uploader class="uploadImg" preview-size="100vw" :after-read="afterRead"/>
      <edit-banner left="封面">
        <img :src="this.videoInfo.picUrl" style="width: 300px;height: 150px" alt="" slot="right" v-if="videoInfo.picUrl !== ''">
        <img src="../assets/bannerTop_new.png" style="width: 300px;height: 150px" alt="" slot="right" v-else>
      </edit-banner>
    </div>
    <edit-banner left="ID">
      <p slot="right">{{ videoInfo.id }}</p>
    </edit-banner>
    <edit-banner @bannerClick="titleShow = true" left="标题">
      <p slot="right">{{ videoInfo.title.substring(0, 10) + "..." }}</p>
    </edit-banner>
    <edit-banner @bannerClick="descriptionShow = true" left="描述">
      <p slot="right">{{ videoInfo.description.substring(0, 10) + "..." }}</p>
    </edit-banner>
    <edit-banner @bannerClick="categoryShow = true" left="分类">
      <p slot="right">{{ videoInfo.category.title }}</p>
    </edit-banner>
    <div style="display: flex;justify-content: center">
      <van-button @click="save" square type="primary">保存</van-button>
    </div>

    <van-dialog v-model="titleShow" title="修改标题" @confirm="editTitle" show-cancel-button>
      <van-field v-model="titleField" placeholder="长度为6-16" autofocus/>
    </van-dialog>

    <van-dialog v-model="descriptionShow" title="修改描述" @confirm="editDescription" show-cancel-button>
      <van-field
          v-model="descriptionField"
          rows="2"
          autosize
          label="留言"
          type="textarea"
          maxlength="100"
          placeholder="长度最大为100"
          show-word-limit
      />
    </van-dialog>

    <van-action-sheet v-model="categoryShow" :actions="categoryActions" @select="editCategory"/>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import EditBanner from "@/components/EditBanner";
import {checkLogin, getVideoInfo, len, setVideoInfo, VideoInfo} from "../../util";
import {Toast} from "vant";

export default {
  name: "EditVideoInfo",
  data() {
    return {
      videoInfo: new VideoInfo(),
      categoryShow: false,
      titleShow: false,
      descriptionShow: false,
      categoryActions: [],
      category: -1,
      titleField: '',
      descriptionField: ''
    }
  },
  components: {
    NavBar,
    EditBanner
  },
  created() {
    checkLogin()
    getVideoInfo(this.$route.query.videoId).then(r => {
      this.videoInfo = r
    })

    this.$http.get('/static/getCategory').then(r => {
      r.data.data.forEach((it, index) => {
        this.categoryActions[index] = {
          name: it.title,
          category: it
        }
      })
    })
  },
  methods: {
    async afterRead(file) {
      const data = new FormData()
      data.append('file', file.file)
      data.append('videoId', this.videoInfo.id)
      const r = await this.$http.put('/uploadPic', data, {
        headers: {'Content-Type': 'multipart/form-data'}
      })
      this.videoInfo.picUrl = r.data.data
    },
    editTitle() {
      this.videoInfo.title = this.titleField
    },
    editDescription() {
      this.videoInfo.description = this.descriptionField
    },
    editCategory(item) {
      this.videoInfo.category = item.category
      this.categoryShow = false
    },
    save() {
      if (this.videoInfo.title.match(len) && this.videoInfo.description.length < 100 && this.videoInfo.category.id !== -1) {
        console.log(this.videoInfo);
        setVideoInfo(this.videoInfo).then(r => {
          console.log(r.data);
        })
      } else {
        Toast.fail("请输入正确")
      }
    }
  }
}
</script>

<style lang="less" scoped>
.uploadPic {
  position: relative;
  overflow: hidden;

  .uploadImg {
    opacity: 0;
    position: absolute;
  }
}
</style>