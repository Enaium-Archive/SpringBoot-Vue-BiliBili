<template>
  <div class="LoginTop">
    <div></div>
    <div>
      {{ middleTop }}
    </div>
    <div>
      <slot name="right"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginTop",
  props: ['middleTop']
}
</script>

<style lang="less">
.LoginTop {
  height: 12vw;
  background-color: white;
  display: flex;
  font-size: 3vw;

  div {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>