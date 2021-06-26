<template>
  <div class="userDetail">
    <div>
      <div class="avatar"><img :src="userInfo.avatarUrl" alt=""></div>
      <div class="info">
        <div>
          <p>
            <span>0</span>
            <span>粉丝</span>
          </p>
          <p>
            <span>0</span>
            <span>关注</span>
          </p>
          <p>
            <span>0</span>
            <span>获赞</span>
          </p>
        </div>
        <div @click="$router.push('/editUserInfo')">
          <div class="edit">编辑资料</div>
        </div>
      </div>
    </div>
    <div>
      <h2>{{ userInfo.nickname }}</h2>
      <p>{{ userInfo.description }}</p>
    </div>
  </div>
</template>

<script>

export default {
  name: "UserDetail",
  props: ['userInfo']
}
</script>

<style lang="less">
.userDetail {
  background-color: white;
  padding: 2vw 3.33vw;

  > div:nth-child(1) {
    display: flex;

    .avatar {
      margin-right: 5.56vw;

      img {
        width: 23.61vw;
        height: 23.61vw;
        border-radius: 50%;
      }
    }

    .info {
      flex: 1;

      div:nth-child(1) {
        display: flex;

        p {
          flex: 1;
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;
          font-size: 3.61vw;
        }

        p:nth-child(1), p:nth-child(2) {
          border-right: 0.28vw solid #ccc;
        }
      }

      div:nth-child(2) {
        padding: 0.556vw 2.778vw;

        .edit {
          border: 0.28vw solid #ff9db5;
          padding: 0.56vw 2.78vw;
          display: flex;
          justify-content: center;
          align-items: center;
          color: #fb7a9f;
          border-radius: 1.11vw;
        }
      }
    }
  }

  > div:nth-child(2) {
    h2 {
      margin: 10px 0 3px 0;
    }

    p {
      margin: 2.78vw 0;
      padding: 0;
      color: #ccc;
    }
  }
}
</style>