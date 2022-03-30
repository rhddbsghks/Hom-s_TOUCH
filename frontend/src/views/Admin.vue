<template>
  <div id="admin">
    <b-container class="bv-example-row mt-3 text-center" style="height: 100%">
      <h2 style="font-size: 2.4em" class="underline-hotpink">관리자 페이지</h2>

      <hr class="mt-5" />
      <div id="nav">
        <router-link :to="{ name: 'UserManage' }">회원 관리</router-link> |
        <router-link :to="{ name: 'NewsManage' }">추천뉴스 관리</router-link>
      </div>
      <router-view> </router-view>
    </b-container>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
#admin {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 80%;
}
#nav {
  margin-bottom: 7%;
}
#nav a {
  font-weight: bold;
  color: #2c3e50;
}
#nav a.router-link-exact-active {
  color: #7979ff;
  font-size: 1.1em;
}
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.123) 30%
  );
}
</style>
