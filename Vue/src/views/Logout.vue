<template>
  <div>
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: "Logout",
  data() {
    return {
      msg: ''
    }
  },
  created() {
    this.$http.get('/logout').then(r => this.msg = r.data.msg)
  }
}
</script>

<style scoped>

</style>