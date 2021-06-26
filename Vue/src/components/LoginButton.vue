<template>
  <div class="parent">
    <div class="LoginButton" @click="registerClick">{{ title }}</div>
  </div>
</template>

<script>
export default {
  name: "LoginButton",
  props: ['title'],
  methods: {
    registerClick() {
      this.$emit('registerSubmit')
    }
  }
}
</script>

<style scoped>
.parent {
  padding: 4.17vw 2.78vw;
}

.LoginButton {
  height: 12.5vw;
  background-color: #ff9db5;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 6.39vw;
}
</style>