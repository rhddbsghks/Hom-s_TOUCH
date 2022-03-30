<template>
  <div>
    <div class="d-flex justify-content-between">
      <router-link
        :to="{ name: 'BoardList', params: { page: 1 } }"
        class="btn"
        style="font-size: 1.2em"
        ><i class="fa fa-list" aria-hidden="true"></i> 목록</router-link
      >
      <div v-if="this.userInfo.id == 'admin'">
        <span class="btn" @click="toModify">수정하기</span
        ><span class="btn" @click="deleteBoard">삭제하기</span>
      </div>
    </div>
    <div id="container">
      <article style="width: 70%; margin: auto">
        <header>
          <h2 id="bo_v_title">
            <span>{{ board.subject }}</span>
          </h2>
          <p><i class="far fa-clock"></i> 21-11-22 08:58</p>
        </header>

        <section
          id="bo_v_info"
          class="d-flex justify-content-between"
          style="width: 90%; margin: auto; font-size: 0.9em; color: gray"
        >
          <span style="width: 150px"
            >작성자: {{ board.name }}({{ board.id }})</span
          >

          <span style="width: 150px"
            >조회: <i class="fa fa-eye" aria-hidden="true"></i>
            {{ board.view }}회</span
          >
          <span style="width: 150px"></span>
        </section>

        <section id="bo_v_atc">
          <div id="bo_v_img"></div>

          <div
            id="bo_v_con"
            class="mt-5 mb-5"
            style="
              min-height: 300px;
              margin: auto;
              text-align: left;
              font-size: 1.2em;
            "
          >
            {{ board.content }}
          </div>
        </section>
      </article>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "NoticeView",
  data() {
    return {
      board: {
        id: "",
        name: "",
        subject: "",
        regtime: "",
        content: "",
        view: "",
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    http.get(`/api/notice/${this.$route.params.no}`).then(({ data }) => {
      this.board.id = data.id;
      this.board.name = data.name;
      this.board.subject = data.subject;
      this.board.regtime = data.regtime;
      this.board.content = data.content;
      this.board.view = data.view;
    });
  },
  methods: {
    deleteBoard() {
      if (!confirm("해당 글을 삭제하시겠습니까?")) return;

      http
        .delete(`/api/notice/${this.$route.params.no}`)
        .then(() => {
          this.$router.push({ name: "BoardList", params: { page: 1 } });
        })
        .catch((err) => console.log(err));
    },

    modifyQnA() {
      console.log("modify");
      this.$router.push({
        name: "QnAModify",
        params: { no: this.$route.params.no },
      });
    },

    toModify() {
      console.log("modify");
      console.log(this.$route.params.no);
      this.$router.push({
        name: "BoardModify",
        params: { no: this.$route.params.no },
      });
    },
  },
};
</script>

<style lang="scss" scoped>
h1 {
  color: #d15ca4;
}
</style>
