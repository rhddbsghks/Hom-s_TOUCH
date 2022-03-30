<template>
  <div style="">
    <sidebar-menu
      :menu="menu"
      :collapsed="true"
      width="250px"
      widthCollapsed="70px"
      theme="white-theme"
    />
    <!-- <div>dㅏ</div> -->
    <router-link :to="{ name: 'Main' }"
      ><h1 id="logo" style="position: absolute; left: 150px; top: 10px">
        Hom's TOUCH
      </h1></router-link
    >

    <div
      id="username"
      style="position: absolute; right: 0px; top: 10px; z-index: 10"
    >
      {{ userInfo.name }} 님
      <span id="logout" @click="logoutProcess">로그아웃</span>
    </div>

    <div style="height: 70px"></div>

    <!-- 메뉴들 라우터 뷰 -->
    <router-view style="margin-left: 70px"></router-view>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";

export default {
  name: "Main",
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  updated() {
    console.log(this.userInfo.id);
    if (this.userInfo.id == "admin") {
      this.menu[6].hidden = false;
    }
  },
  created() {
    console.log(this.userInfo.id);
    if (this.userInfo.id == "admin") {
      this.menu[6].hidden = false;
    }
  },
  data() {
    return {
      menu: [
        {
          header: true,
          title: "",
        },

        {
          href: "/main/map",
          title: "거래 정보 검색",
          icon: "far fa-building fa-lg ",
        },
        {
          href: "/main/user",
          title: "마이페이지",
          icon: "fa fa-user-alt  fa-lg",
        },
        {
          href: "/main/board",
          title: "자유게시판",
          icon: "fas fa-chalkboard  fa-lg",
        },
        {
          href: "/main/interest",
          title: "관심 아파트 관리",
          icon: "far fa-star  fa-lg",
        },
        {
          href: "/main/news",
          title: "오늘의 뉴스",
          icon: "far fa-list-alt  fa-lg",
        },

        {
          href: "/main/admin",
          title: "관리자페이지",
          id: "hello",
          icon: "fas fa-user-cog  fa-lg",
          hidden: true,
        },
      ],
    };
  },
  methods: {
    ...mapActions(memberStore, ["logout"]),
    logoutProcess() {
      this.logout();
      this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style lang="scss">
#username {
  text-align: right;
  padding-top: 20px;
  margin-bottom: 40px;
  margin-right: 120px;
  height: 30px;
  font-size: 1.2em;

  #logout {
    font-size: 0.5em;
    color: rgb(129, 129, 129);
  }
  #logout:hover {
    cursor: pointer;
  }
}

.vsm--arrow {
  margin-bottom: 30px;
}

.vsm--title {
  margin-bottom: 30px;
  margin-left: 20px;
  text-align: left;
  width: 200px;
}
.vsm--header {
  padding-top: 60px !important;
}

.v-sidebar-menu.vsm_expanded {
  background: rgb(238, 174, 202);
  background: radial-gradient(
    circle,
    rgba(238, 174, 202, 1) 0%,
    rgba(148, 187, 233, 1) 100%
  );
}
.v-sidebar-menu.vsm_collapsed {
  background: rgb(238, 174, 202);
  background: radial-gradient(
    circle,
    rgba(238, 174, 202, 1) 0%,
    rgba(148, 187, 233, 1) 100%
  );
}

.v-sidebar-menu.vsm_rtl {
  background-color: khaki !important;
}

.v-sidebar-menu .vsm--mobile-bg {
  background-color: rgba(242, 242, 242, 0.5) !important;
}

.v-sidebar-menu.vsm_white-theme .vsm--toggle-btn {
  background-color: transparent !important;
}

.v-sidebar-menu.vsm_white-theme {
  .vsm--link_level-1 .vsm--icon {
    height: 50px !important;
    line-height: 50px !important;
    width: 50px !important;
    border-radius: 20px !important;
  }
}

.v-sidebar-menu.vsm_white-theme .vsm--link_mobile-item {
  color: black !important;
  margin: auto !important;
  height: 70px;
  span {
    margin: auto;
  }
}

.vsm--list .vsm--item {
  height: 110px;
  display: flex !important;
}

.v-sidebar-menu.vsm_white-theme.vsm_expanded
  .vsm--item_open
  .vsm--link_level-1 {
  color: black !important;
  background-color: rgba(242, 242, 242, 0.5) !important;
}

// 선택된 아이콘 색
.v-sidebar-menu.vsm_white-theme
  .vsm--link_level-1.vsm--link_exact-active
  .vsm--icon,
.v-sidebar-menu.vsm_white-theme .vsm--link_level-1.vsm--link_active .vsm--icon {
  color: #4675af !important;
  background-color: #ffffffd2 !important;
}

//선택 안된 아이콘
.v-sidebar-menu.vsm_white-theme .vsm--link_level-1 .vsm--icon {
  background-color: #4675af1f !important;
}

.v-sidebar-menu.vsm_white-theme {
  .vsm--link {
    &_level-1 {
      &.vsm--link_exact-active,
      &.vsm--link_active {
        box-shadow: 3px 0px 0px 0px #af8cac inset !important;
      }
    }
  }
}

.vsm--list {
  margin-top: 30px;
}
.vsm--mobile-item {
  height: 110px;
  .vsm--item {
    height: 100%;
    display: flex !important;
    flex-direction: column;
    justify-content: center;
  }
}

.v-sidebar-menu.vsm_white-theme .vsm--mobile-bg {
  background-color: #ddddebc0 !important;
}

@import url(https://fonts.googleapis.com/css?family=Righteous);
#logo {
  display: inline-block;
  color: #5c8cf1e5;
  font-family: "Righteous", serif;
  text-shadow: 0.03em 0.03em 0 rgb(39, 46, 83);
  z-index: 10;
}
</style>
