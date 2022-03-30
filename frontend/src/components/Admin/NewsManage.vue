<template>
  <div>
    <div
      class="scrollbar b-table-sticky-header table-responsive"
      style="overflow-y: auto; overflow-x: auto; max-height: 500px"
    >
      <table class="table" style="width: 100% height:100%">
        <thead>
          <tr>
            <th width="20%">등록일</th>
            <th width="55%" style="padding-right: 20%">제목</th>
            <th width="10%"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="news in newsList" :key="news.no">
            <td style="font-size: 1.3em">
              {{ news.등록일 }}
            </td>
            <td style="font-size: 1.2em; text-align: left">
              <a :href="news.링크" target="_blank" style="color: #60627f">
                {{ news.제목 }}</a
              >
            </td>
            <td style="width: 60px">
              <b-button
                block
                id="deleteBtn"
                class="ml-2"
                @click="deleteNews(news.번호)"
              >
                삭제
              </b-button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="d-flex flex-row-reverse">
      <b-button type="button" id="searchBtn" class="ml-1 btn" @click="setData">
        추가
      </b-button>
      <input
        type="text"
        class="ml-1 form-control"
        v-model="url"
        style="width: 30%"
        @keyup.enter="setData"
      />
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
export default {
  data() {
    return {
      newsList: [],
      url: "",
      news: { link: "", thumbnail: "", title: "" },
    };
  },
  created() {
    this.loadList();
  },
  methods: {
    regist() {
      http.post("/api/news", this.news).then(() => {
        this.news = { link: "", thumbnail: "", title: "" };
        this.url = "";
        this.loadList();
      });
    },
    setData() {
      this.news.link = this.url;
      var request = new XMLHttpRequest();
      request.onreadystatechange = () => {
        if (request.readyState == 4) {
          this.news.thumbnail = request.responseText
            .split('<meta property="og:image"')[1]
            .split('"')[1];

          this.news.title = request.responseText
            .split('<meta property="og:title"')[1]
            .split('"')[1];

          this.regist();
        }
      };
      request.open("GET", this.news.link);
      request.send();
    },
    loadList() {
      this.newsList = [];
      http.get("/api/news/recommend").then(({ data }) => {
        data.forEach((el) => {
          this.newsList.push({
            번호: el.no,
            등록일: el.regTime,
            제목: el.title,
            링크: el.link,
            썸네일: el.thumbnail,
          });
        });
      });
    },
    deleteNews(no) {
      if (confirm("해당 뉴스를 삭제하시겠습니까?"))
        http.delete(`/api/news/${no}`).then(() => this.loadList());
    },
  },
};
</script>

<style lang="scss" scoped>
thead {
  th {
    position: sticky;
    top: 0;
    z-index: 2;
    background: #d4d6ff; /* fallback for old browsers */
  }
}
tr {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: none;
}

#deleteBtn {
  border: 0px;
  background-image: linear-gradient(135deg, #f05f57 10%, #360940 100%);
  border-color: rgba(255, 255, 255, 0);
}
</style>
