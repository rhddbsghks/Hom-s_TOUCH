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
    <h1>글 수정</h1>
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
    <b-button
      class="btn-lg"
      variant="outline-info"
      :to="{ name: 'QnACreate' }"
      @click="modify"
      >수정</b-button
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
      no: "",
      subject: "",
      content: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    console.log(this.$route.params.no);
    http.get(`/api/notice/${this.$route.params.no}`).then(({ data }) => {
      this.no = this.$route.params.no;
      this.subject = data.subject;
      this.content = data.content;
    });
  },
  methods: {
    modify() {
      console.log("파랑");
      console.log(this.no);
      http
        .put(`/api/notice/${this.no}`, {
          noticeNo: this.no,
          subject: this.subject,
          content: this.content,
        })
        .then(() => {
          this.$router.push({ name: "BoardList", params: { page: 1 } });
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>

<style lang="scss" scoped></style>
