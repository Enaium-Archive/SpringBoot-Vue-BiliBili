<template>
  <div>
    <van-field v-model="content" :label="label" :type="type" :placeholder="placeholder" :rule="rule"/>
  </div>
</template>

<script>
export default {
  name: "LoginText",
  props: ['label', 'type', 'placeholder', 'rule'],
  data() {
    return {
      content: ''
    }
  },
  methods: {
    handleRule() {
      this.$emit('inputChange', this.content)
    }
  },
  watch: {
    content() {
      this.handleRule()
    }
  }
}
</script>

<style scoped>

</style>