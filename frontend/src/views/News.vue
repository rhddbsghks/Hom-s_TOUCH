<template>
  <div>
    <div style="width: 80%; margin: 50px auto">
      <h1 style="text-align: center; margin-bottom: 50px">
        <strong>추천 뉴스</strong>
        <div class="d-flex justify-content-around">
          <div style="padding-top: 60px">
            <recommend-carousel
              v-if="추천 != null && 추천.length > 0"
              id="recommend"
              :newsList="추천"
            ></recommend-carousel>
            <h3 v-else>등록된 뉴스가 없습니다.</h3>
          </div>
        </div>
      </h1>
      <div></div>

      <hr
        style="
          height: 10px;
          margin: 5% 10%;
          border: 0;
          box-shadow: 0 10px 10px -15px #8c8b8b inset;
        "
      />
      <h1 style="text-align: center; margin-bottom: 50px">
        <strong>오늘의 뉴스</strong>
      </h1>
      <div class="d-flex justify-content-around">
        <div>
          <h2 class="category">
            <strong>부동산</strong>
          </h2>
          <news-carousel :newsList="부동산"></news-carousel>
        </div>
        <div>
          <h2 class="category">
            <strong>재개발</strong>
          </h2>
          <news-carousel :newsList="재개발"></news-carousel>
        </div>
      </div>
      <div
        class="d-flex justify-content-around"
        style="margin-top: 5%; margin-bottom: 10%"
      >
        <div>
          <h2 class="category">
            <strong>1인 가구</strong>
          </h2>
          <news-carousel :newsList="가구"></news-carousel>
        </div>
        <div>
          <h2 class="category">
            <strong>분양</strong>
          </h2>
          <news-carousel :newsList="분양"></news-carousel>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import NewsCarousel from "../components/News/NewsCarousel.vue";
import RecommendCarousel from "../components/News/RecommendCarousel.vue";
export default {
  components: {
    NewsCarousel,
    RecommendCarousel,
  },
  data() {
    return {
      부동산: [],
      재개발: [],
      가구: [],
      분양: [],
      추천: [],
      slide: 0,
      sliding: null,
    };
  },
  created() {
    http.get("/api/news/search/부동산 정책").then(({ data }) => {
      data.items.forEach((el, index) => {
        this.부동산.push({
          link: el.originallink,
          title: "",
          thumbnail: "",
        });
        this.setThumnailLink(this.부동산, index, el.link);
      });
    });
    http.get("/api/news/search/재개발").then(({ data }) => {
      data.items.forEach((el, index) => {
        this.재개발.push({
          link: el.originallink,
          title: "",
          thumbnail: "",
        });
        this.setThumnailLink(this.재개발, index, el.link);
      });
    });
    http.get("/api/news/search/1인 가구").then(({ data }) => {
      data.items.forEach((el, index) => {
        this.가구.push({
          link: el.originallink,
          title: "",
          thumbnail: "",
        });
        this.setThumnailLink(this.가구, index, el.link);
      });
    });
    http.get("/api/news/search/분양").then(({ data }) => {
      data.items.forEach((el, index) => {
        this.분양.push({
          link: el.originallink,
          title: "",
          thumbnail: "",
        });
        this.setThumnailLink(this.분양, index, el.link);
      });
    });
    http
      .get("/api/news/recommend")
      .then(({ data }) => (this.추천 = data.slice(0, 5)));
  },
  methods: {
    setThumnailLink(category, index, link) {
      var request = new XMLHttpRequest();
      request.onreadystatechange = () => {
        let metaTxt = "";

        if (request.readyState == 4) {
          metaTxt = request.responseText;

          if (
            metaTxt.split("og:image")[1].split('"')[1].substr(0, 3) == "htt"
          ) {
            category[index].thumbnail = metaTxt
              .split("og:image")[1]
              .split('"')[1];
          } else {
            category[index].thumbnail = metaTxt
              .split("og:image")[1]
              .split('"')[2];
          }

          category[index].title = metaTxt
            .split("og:title")[1]
            .split('"')[2]
            .replaceAll("&lt;", "<")
            .replaceAll("&gt;", ">")
            .replaceAll("&quot;", '"')
            .replaceAll("&#39;", `'`);
        }
      };
      request.open("GET", link);
      request.send();
    },
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>

<style lang="scss">
#recommend {
  h2 {
    font-size: 3rem;
  }
}
.img-wrapper :hover {
  cursor: pointer;
}
.carousel-caption {
  position: relative !important;
  left: 0 !important;
  padding: 0 !important;
  bottom: 20% !important;

  h2 {
    padding: 5px 40px;
  }
}
.category {
  border-bottom: 6px solid #6565b6d0;
  width: 200px;
  text-align: center;
  margin: 30px auto;
}
</style>
