<template>
  <div style="width: 80%; margin: auto">
    <div class="d-flex justify-content-between">
      <router-link
        :to="{ name: 'BoardList', params: { page: 1 } }"
        class="btn"
        style="font-size: 1.2em"
        ><i class="fa fa-list" aria-hidden="true"></i> 목록</router-link
      >
    </div>
    <h1>글 작성</h1>
    <br />

    <b-form-group
      id="fieldset-horizontal"
      label="제목"
      style="text-align: left"
    >
      <b-form-input id="input-horizontal" v-model="subject"></b-form-input>
    </b-form-group>
    <b-form-group
      id="fieldset-horizontal"
      label="내용"
      style="text-align: left"
    >
      <b-form-textarea
        id="textarea-rows"
        rows="15"
        v-model="content"
      ></b-form-textarea>
    </b-form-group>
    <b-button class="btn-lg" variant="outline-info" @click="regist"
      >작성</b-button
    >
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  data() {
    return {
      subject: "",
      content: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    regist() {
      if (this.subject == null || this.subject == "") {
        alert("제목을 입력해주세요.");
        return this;
      } else if (this.content == null || this.content == "") {
        alert("내용을 입력해주세요.");
      } else {
        http
          .post("/api/notice", {
            id: this.userInfo.id,
            subject: this.subject,
            content: this.content,
          })
          .then(() => {
            this.$router.push({ name: "BoardList", params: { page: 1 } });
          })
          .catch((err) => console.log(err));
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
