<template>
  <div @click="bannerClick">
    <div class="editBanner">
      <span>{{ left }}</span>
      <span>
        <slot name="right"></slot>
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "EditBanner",
  props: ['left'],
  methods: {
    bannerClick() {
      this.$emit("bannerClick")
    }
  }
}
</script>

<style scoped>
.editBanner {
  background-color: white;
  color: #333;
  padding: 4.17vw;
  font-size: 4.44vw;
  border-bottom: 1px solid #fafafa;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>