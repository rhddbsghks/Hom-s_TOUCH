import axios from "axios";

// axios 객체 생성
function newsAPI(keyword) {
  axios
    .get("https://openapi.naver.com/v1/search/news.json", {
      headers: {
        "X-Naver-Client-Id": "uLsx4YZaNFmTaH82Tqxo",
        "X-Naver-Client-Secret": "wk2zNn0x56",
      },
      params: {
        query: keyword,
        display: 5,
      },
    })
    .then((res) => console.log(res));
}

export { newsAPI };
