<template>
  <div>
    <b-container class="bv-example-row mt-3 text-center">
      <router-link :to="{ name: 'Board' }" style="color: rgba(5, 5, 5, 90)"
        ><h2 style="font-size: 2.4em" class="underline-hotpink">
          자유게시판
        </h2></router-link
      >

      <hr class="mt-5" />
      <router-view> </router-view>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "Board",
};
</script>

<style lang="scss" scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.123) 30%
  );
}
</style>
