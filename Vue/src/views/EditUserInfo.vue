<template>
  <div>
    <div style="margin-bottom: 2.78vw;">
      <nav-bar></nav-bar>
    </div>
    <div class="uploadAvatar">
      <van-uploader class="uploadImg" preview-size="100vw" :after-read="afterRead"/>
      <edit-banner left="头像">
        <img class="avatar" :src="data.avatarUrl" alt="" slot="right" v-if="data.avatarUrl !== ''">
        <img class="avatar" src="../assets/avatar.jpg" alt="" slot="right" v-else>
      </edit-banner>
    </div>
    <edit-banner left="昵称" @bannerClick="nicknameShow = true">
      <p slot="right">{{ data.nickname }}</p>
    </edit-banner>
    <edit-banner left="ID">
      <p slot="right">{{ data.id }}</p>
    </edit-banner>
    <edit-banner left="性别" @bannerClick="genderShow =true">
      <p slot="right">{{ data.gender }}</p>
    </edit-banner>
    <edit-banner left="描述" @bannerClick="descriptionShow = true">
      <p slot="right">{{ data.description }}</p>
    </edit-banner>

    <div style="display: flex" @click="$router.push('/logout')">
      <van-button type="danger" style="flex:1">登出</van-button>
    </div>

    <van-dialog v-model="nicknameShow" style="" title="修改昵称" @confirm="editNickname" show-cancel-button>
      <van-field v-model="nickname" autofocus/>
    </van-dialog>

    <van-action-sheet v-model="genderShow" :actions="genderActions" @select="editGender"/>

    <van-dialog v-model="descriptionShow" style="" title="修改描述" @confirm="editDescription" show-cancel-button>
      <van-field v-model="description" autofocus/>
    </van-dialog>


  </div>
</template>

<script>
import NavBar from "../components/NavBar";
import EditBanner from "../components/EditBanner";
import {getUserInfo, len, setUserInfo, UserInfo} from "../../util";

export default {
  name: "EditUserInfo",
  components: {
    NavBar,
    EditBanner
  },
  data() {
    return {
      data: new UserInfo(),
      nicknameShow: false,
      descriptionShow: false,
      genderShow: false,
      nickname: '',
      description: '',
      genderActions: [{name: '私密'}, {name: '女'}, {name: '男'}]
    }
  },
  created() {
    getUserInfo().then(r => {
      this.data = r
    })
  },
  methods: {
    async afterRead(file) {
      const data = new FormData()
      data.append('file', file.file)
      const promise = await this.$http.put('/uploadAvatar', data, {
        headers: {'Content-Type': 'multipart/form-data'}
      });
      this.data.avatarUrl = promise.data.data
      await setUserInfo(this.data)
    },
    editNickname() {
      if (this.nickname.match(len)) {
        this.data.nickname = this.nickname
        setUserInfo(this.data)
      }
    },
    editGender(item) {
      this.data.gender = item.name
      setUserInfo(this.data)
      this.genderShow = false
    },
    editDescription() {
      if (this.description.match(len)) {
        this.data.description = this.description
        setUserInfo(this.data)
      }
    }
  }
}
</script>

<style lang="less">
.avatar {
  width: 12.78vw;
  height: 12.78vw;
  border-radius: 50%;
}

.uploadAvatar {
  position: relative;
  overflow: hidden;

  .uploadImg {
    opacity: 0;
    position: absolute;
  }
}
</style>