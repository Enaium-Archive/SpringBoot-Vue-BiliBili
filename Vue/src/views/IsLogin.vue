<template>

</template>

<script>
export default {
  name: "IsLogin",
  created() {
    this.$http.get('/isLogin').then(e => {
      console.log(e.data)
    })
  }
}
</script>

<style scoped>

</style>