<template>
  <div>
    <b-form
      id="searchform"
      class="form-inline justify-content-end mt-4"
      method="get"
      onSubmit="return false;"
    >
      <input type="hidden" name="pg" value="1" />
      <b-form-select v-model="key" :options="keys"></b-form-select>
      <input
        type="text"
        class="ml-1 form-control"
        id="sword"
        name="word"
        v-model="word"
        @keyup.enter="search"
      />
      <b-button type="button" id="searchBtn" class="ml-1 btn" @click="search">
        검색
      </b-button>
    </b-form>

    <!-- <b-table
      small
      outlined
      hover
      class="mt-4"
      head-variant="light"
      style="font-size: 1em"
      :items="items"
      selectable
      @row-selected="showView"
    >
    </b-table> -->

    <ul style="padding: 0px; margin: auto" class="mt-5 mb-5">
      <li
        style="padding-left: 4%; padding-right: 10%; border-bottom: solid 0.5px"
      >
        <div
          class="notice-item"
          style="
            display: flex;
            align-items: center;
            justify-content: space-between;
          "
        >
          <div>
            <span
              >번호
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제목</span
            >
          </div>

          <div
            style="
              width: 40%;
              display: flex;
              align-items: center;
              justify-content: space-between;
            "
          >
            <span>작성자</span><span>조회수</span><span>날짜</span>
          </div>
        </div>
      </li>

      <li
        style="padding-left: 4%; padding-right: 8%"
        v-for="notice in notices"
        :key="notice.id"
      >
        <router-link
          :to="{ name: 'NoticeView', params: { no: notice.noticeNo } }"
        >
          <div
            class="notice-item"
            id="notice"
            style="
              display: flex;
              align-items: center;
              justify-content: space-between;
            "
          >
            <div class="d-flex justify-content-between" style="width: 50%">
              <div>
                <span>
                  <strong class="notice_icon" style="color: rgb(250, 70, 124)"
                    ><i class="fa fa-volume-up" aria-hidden="true"></i>
                    [공지사항]</strong
                  >
                </span>
              </div>
              <div style="width: 75%; text-align: left">
                <span>
                  <a style="font-size: 1.15em; margin: 0">{{
                    notice.subject
                  }}</a></span
                >
              </div>
            </div>

            <div
              style="
                width: 50%;
                display: flex;
                align-items: center;
                justify-content: space-between;
              "
            >
              <div style="width: 200px; color: #d81e22">
                <span>{{ notice.name }}</span>
              </div>
              <span style="width: 90px; text-align: left"
                ><i
                  style="color: #005bfe"
                  class="fa fa-eye"
                  aria-hidden="true"
                ></i
                ><span
                  style="margin: 0 5px 0 3px; color: #005bfe"
                  class="cnt_hit"
                  >{{ notice.view }}</span
                ></span
              ><span style="width: 60px"> {{ notice.regTime }}</span>
            </div>
          </div>
        </router-link>
      </li>

      <li
        style="padding-left: 4%; padding-right: 8%"
        v-for="item in items"
        :key="item.id"
      >
        <router-link :to="{ name: 'BoardView', params: { no: item.noticeNo } }">
          <div
            class="notice-item"
            style="
              display: flex;
              align-items: center;
              justify-content: space-between;
            "
          >
            <div class="d-flex justify-content-between" style="width: 50%">
              <div>
                <span> {{ item.noticeNo }} &nbsp;</span>
              </div>
              <div style="width: 75%; text-align: left">
                <span
                  ><a style="font-size: 1.15em; margin: 0">{{
                    item.subject
                  }}</a>
                </span>
              </div>
            </div>

            <div
              style="
                width: 50%;
                display: flex;
                align-items: center;
                justify-content: space-between;
              "
            >
              <div style="width: 200px">
                <span v-if="item.name != null && item.name != ''">{{
                  item.name
                }}</span>
                <span v-else>(탈퇴한 회원)</span>
              </div>
              <span style="width: 90px; text-align: left"
                ><i
                  style="color: #005bfe"
                  class="fa fa-eye"
                  aria-hidden="true"
                ></i
                ><span
                  style="margin: 0 5px 0 3px; color: #005bfel"
                  class="cnt_hit"
                  >{{ item.view }}</span
                ></span
              ><span style="width: 60px"> {{ item.regTime }}</span>
            </div>
          </div>
        </router-link>
      </li>
    </ul>
    <div
      class="d-flex justify-content-between"
      style="position: absolute; width: 33%; left: 44%; bottom: 5%"
    >
      <pagination style="margin: 0px" />

      <b-button
        v-if="this.userInfo.id == 'admin'"
        variant="outline-primary"
        style="position: relative"
        :to="{ name: 'BoardCreate' }"
        >공지사항 작성</b-button
      >
      <b-button v-else variant="outline-info" :to="{ name: 'BoardCreate' }"
        >글 작성</b-button
      >
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState } from "vuex";
import Pagination from "./Pagination.vue";

const memberStore = "memberStore";
export default {
  name: "BoardList",
  data() {
    return {
      items: [],
      notices: [],
      word: null,
      keys: {
        name: "작성자",
        subject: "제목",
        content: "내용",
      },
      key: "name",
      pageLimit: 7,
      pageOffset: 0,
    };
  },
  components: { Pagination },
  created() {
    this.pageOffset = this.$route.params.page;
    console.log("헬로");
    http
      .get("/api/notice", {
        params: { pg: this.pageOffset, spp: this.pageLimit },
      })
      .then(({ data }) => {
        console.log(data);
        this.items = data;
      });

    http.get("/api/notice/notice").then(({ data }) => {
      console.log(data);
      this.notices = data;
    });
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  watch: {
    "$route.params": function () {
      this.items = [];
      http
        .get("/api/notice", {
          params: { pg: this.$route.params.page, spp: this.pageLimit },
        })
        .then(({ data }) => {
          this.items = data;
        });
    },
  },
  methods: {
    showView(item) {
      console.log(item[0].번호);
      this.$router.push({ name: "QnAView", params: { no: item[0].번호 } });
    },

    search() {
      this.items = [];
      const params = { key: this.key, word: this.word };
      http.get("/api/notice", { params }).then(({ data }) => {
        this.items = data;
      });
    },

    reload() {
      location.reload();
    },
  },
};
</script>

<style lang="scss" scoped>
ul {
  list-style: none;
  margin-top: 3%;
}

.notice-item {
  margin: 2% 0;
}

#notice {
  a {
    color: rgb(250, 70, 124);
  }
}
a {
  color: rgb(87, 91, 114);
}
a:hover {
  text-decoration: none;
}
li:hover {
  background-color: #dadce083;
  cursor: pointer;
}
</style>
