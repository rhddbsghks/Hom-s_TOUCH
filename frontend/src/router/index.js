import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Main from "../views/Main.vue";
import Board from "../views/Board.vue";
import User from "../views/User.vue";
import store from "@/store/index.js";
import MapMain from "../views/MapMain.vue";
import News from "../views/News.vue";
import Admin from "../views/Admin.vue";
import Interest from "../views/Interest.vue";
import BoardList from "../components/Board/BoardList.vue";
import BoardCreate from "../components/Board/BoardCreate.vue";
import BoardView from "../components/Board/BoardView.vue";
import NoticeView from "../components/Board/NoticeView.vue";
import BoardModify from "../components/Board/BoardModify.vue";
import NewsManage from "../components/Admin/NewsManage.vue";
import UserManage from "../components/Admin/UserManage.vue";

Vue.use(VueRouter);

const noHome = function (to, from, next) {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  let token = sessionStorage.getItem("access-token");

  if (checkUserInfo != null && token) {
    router.push({ name: "Main" });
  } else next();
};

const routes = [
  {
    path: "/",
    name: "Home",
    beforeEnter: noHome,
    component: Home,
  },
  {
    path: "/main",
    name: "Main",
    component: Main,
    redirect: "/main/map",
    children: [
      {
        path: "user",
        name: "User",
        component: User,
      },
      {
        path: "board",
        name: "Board",
        component: Board,
        redirect: { name: "BoardList", params: { page: 1 } },
        children: [
          {
            path: "list/:page",
            name: "BoardList",
            component: BoardList,
          },
          {
            path: "create",
            name: "BoardCreate",
            component: BoardCreate,
          },
          {
            path: "view/:no",
            name: "BoardView",
            component: BoardView,
          },
          {
            path: "noticeview/:no",
            name: "NoticeView",
            component: NoticeView,
          },
          {
            path: "modify/:no",
            name: "BoardModify",
            component: BoardModify,
          },
        ],
      },
      {
        path: "map",
        name: "MapMain",
        component: MapMain,
      },
      {
        path: "news",
        name: "News",
        component: News,
      },
      {
        path: "interest",
        name: "Interest",
        component: Interest,
      },
      {
        path: "admin",
        name: "Admin",
        component: Admin,
        redirect: { name: "UserManage", params: { page: 1 } },
        children: [
          {
            path: "usermanage/:page",
            name: "UserManage",
            component: UserManage,
          },
          {
            path: "newsmanage",
            name: "NewsManage",
            component: NewsManage,
          },
        ],
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
