<template>
  <div>
    <div class="d-flex justify-content-between">
      <router-link
        :to="{ name: 'BoardList', params: { page: 1 } }"
        class="btn"
        style="font-size: 1.2em"
        ><i class="fa fa-list" aria-hidden="true"></i> 목록</router-link
      >
      <div
        v-if="this.board.id == this.userInfo.id || this.userInfo.id == 'admin'"
      >
        <span class="btn" @click="toModify">수정하기</span>
        <span class="btn" @click="deleteBoard">삭제하기</span>
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
          <span style="width: 150px" v-if="board.id != null && board.id != ''"
            >작성자: {{ board.name }}({{ board.id }})</span
          >
          <span style="width: 150px" v-else>(탈퇴한 회원)</span>

          <span style="width: 150px"
            >조회: <i class="fa fa-eye" aria-hidden="true"></i>
            {{ board.view }}회</span
          >
          <span style="width: 150px"
            >댓글: <i class="fa fa-commenting-o" aria-hidden="true"></i>
            {{ comments.length }}건</span
          >
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
            v-html="enterToBr(board.content)"
          ></div>
        </section>

        <!-- 댓글 리스트 -->
        <span style="float: left">댓글작성</span>
        <section
          id="bo_vc"
          style="margin-bottom: 40px; width: 100%"
          class="d-flex justify-content-between"
        >
          <b-form-group
            id="fieldset-horizontal"
            style="text-align: left; width: 80%; margin: 0px"
          >
            <b-form-textarea
              id="textarea-rows"
              rows="3"
              v-model="newComment"
            ></b-form-textarea>
          </b-form-group>
          <b-button
            class="btn-lg"
            variant="outline-info"
            style="width: 15%"
            @click="registComment"
            >작성</b-button
          >
        </section>

        <!-- 댓글 리스트 -->
        <section id="bo_vc" style="margin-bottom: 80px">
          <h4 style="text-align: left">
            <i class="far fa-comment"></i> 댓글목록
          </h4>
          <hr />
          <p
            v-if="comments.length == 0 || comments == null"
            style="color: #d15ca4"
          >
            등록된 댓글이 없습니다.
          </p>
          <div
            style="text-align: left; margin: 20px 0"
            v-for="(comment, index) in this.comments"
            :key="comment.id"
          >
            <div style="font-size: 0.8em">
              {{ comment.name }}({{ comment.id }})
              <span style="margin-left: 5px; font-size: 0.9em; color: gray">{{
                comment.regTime
              }}</span>

              <span v-if="comment.id == userInfo.id">
                <span
                  v-if="!comment.isModifing"
                  style="font-size: 0.7em; padding: 0 3px; padding-left: 10px"
                  class="btn"
                  @click="toggleModifyComment(index)"
                  >수정하기</span
                >
                <span
                  v-else
                  style="font-size: 0.7em; padding: 0 3px; padding-left: 10px"
                  class="btn"
                  @click="toggleModifyComment(index)"
                  >취소</span
                >
                <span
                  style="font-size: 0.7em; padding: 0 3px"
                  class="btn"
                  @click="deleteComment(index)"
                  >삭제하기</span
                >
              </span>
            </div>
            <div
              v-if="comment.isModifing"
              id="bo_vc"
              class="d-flex justify-content-between"
            >
              <b-form-group
                id="fieldset-horizontal"
                style="text-align: left; width: 80%; margin: 0px"
              >
                <b-form-textarea
                  id="textarea-rows"
                  rows="1"
                  v-model="comment.content"
                ></b-form-textarea>
              </b-form-group>
              <b-button
                class="btn-lg"
                variant="outline-info"
                style="width: 15%"
                @click="updateComment(index)"
                >수정</b-button
              >
            </div>

            <div v-else style="font-size: 1.1em">{{ comment.content }}</div>
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
  name: "BoardView",
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
      comments: [],
      newComment: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    http.get(`/api/notice/${this.$route.params.no}`).then(({ data }) => {
      console.log("??");
      if (data.name != null && data.name != "") {
        this.board.id = data.id;
        this.board.name = data.name;
      } else this.board.name = "탈퇴한 유저";
      this.board.subject = data.subject;
      this.board.regtime = data.regtime;
      this.board.content = data.content;
      this.board.view = data.view;
    });

    this.loadComment();
  },
  methods: {
    enterToBr(str) {
      // 문자열에 enter값을 <br />로 변경.(html상에서 줄바꿈 처리)
      return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    deleteBoard() {
      if (!confirm("해당 글을 삭제하시겠습니까?")) return;

      http
        .delete(`/api/notice/${this.$route.params.no}`)
        .then(() => {
          this.$router.push({ name: "BoardList", params: { page: 1 } });
        })
        .catch((err) => console.log(err));
    },

    toModify() {
      console.log("modify");
      console.log(this.$route.params.no);
      this.$router.push({
        name: "BoardModify",
        params: { no: this.$route.params.no },
      });
    },
    loadComment() {
      this.comments = [];
      http.get(`/api/comment/${this.$route.params.no}`).then(({ data }) => {
        console.log(data);
        data.forEach((d) => {
          this.comments.push({
            commentNo: d.commentNo,
            noticeNo: d.noticeNo,
            id: d.id,
            content: d.content,
            name: d.name,
            regTime: d.regTime,
            isModifing: false,
          });
        });
      });
    },

    registComment() {
      http
        .post(`/api/comment`, {
          noticeNo: this.$route.params.no,
          id: this.userInfo.id,
          content: this.newComment,
        })
        .then(() => {
          this.newComment = "";
          this.loadComment();
        });
    },
    updateComment(index) {
      http
        .put(`/api/comment`, {
          commentNo: this.comments[index].commentNo,
          content: this.comments[index].content,
        })
        .then(() => {
          this.loadComment();
        });
    },

    deleteComment(index) {
      http.delete(`/api/comment/${this.comments[index].commentNo}`).then(() => {
        this.loadComment();
      });
    },
    toggleModifyComment(index) {
      this.comments[index].isModifing = !this.comments[index].isModifing;
    },
  },
};
</script>

<style lang="scss" scoped>
h1 {
  color: #d15ca4;
}
</style>
