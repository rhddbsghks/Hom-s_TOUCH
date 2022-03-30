<template>
  <div>
    <b-container class="bv-example-row">
      <b-row align-h="between" class="mt-4">
        <b-col cols="8" @click="clickApt()"
          ><h3 class="mb-4">{{ house.aptName }}</h3></b-col
        >
        <b-col cols="3">
          <img
            src="../../assets/star_chose.png"
            style="height: 36px"
            v-show="check_interest"
            @click="delete_interestedApt([userInfo.id, house.aptCode])"
          />
          <img
            src="../../assets/star_empty.png"
            style="height: 36px"
            v-show="!check_interest"
            @click="
              insert_interestedApt([userInfo.id, house.aptCode, house.aptName])
            "
          />
        </b-col>
      </b-row>
      <!-- <b-row class="d-flex x justify-content-center mb-4"
        ><b-col cols="10" align-self="stretch"
          ><b-img :src="require('@/assets/apt.png')" fluid-grow></b-img></b-col
      ></b-row> -->
      <b-row class="mb-4">
        <b-col cols="12" align-self="stretch">
          <div ref="roadview" style="width: 100%; height: 300px"></div>
        </b-col>
      </b-row>
      <b-row class="mb-2 mt-1">
        <b-col>
          <table class="table" style="border: 1px solid #dee2e6">
            <tr>
              <td
                class="col-4 table-secondary"
                style="font-size: 1.3em; background-color: #c0d3ed"
              >
                <strong>아파트 명</strong>
              </td>
              <td style="font-size: 1.2em">{{ house.aptName }}</td>
            </tr>
            <tr>
              <td
                class="col-4 table-secondary"
                style="font-size: 1.3em; background-color: #a8c5ed"
              >
                <strong>건축년도</strong>
              </td>
              <td style="font-size: 1.2em">{{ house.buildYear }}년</td>
            </tr>
            <tr>
              <td
                class="col-4 table-secondary"
                style="font-size: 1.3em; background-color: #c0d3ed"
              >
                <strong>주소</strong>
              </td>
              <td style="font-size: 1.2em">
                {{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}
                {{ house.jibun }}
              </td>
            </tr>
            <tr>
              <td
                class="col-4 table-secondary"
                style="font-size: 1.3em; background-color: #a8c5ed"
              >
                <strong>최근 거래금액</strong>
              </td>
              <td style="font-size: 1.2em">
                {{ parseInt(house.recentPrice.replace(",", "")) | price }}만 원
              </td>
            </tr>
          </table>
        </b-col>
      </b-row>
    </b-container>
    <div
      class="d-flex justify-content-around"
      style="width: 100%; text-align: center"
    >
      <div class="d-flex flex-column">
        <div class="badge" style="width: 200px" @click="clickSubway">
          <img src="../../assets/subway.png" style="height: 80px" />
          <div class="d-flex flex-column mt-2" style="font-size: 1.3em">
            <span style="display: none">{{ subway_text }}</span>
            <span>{{ subway }}</span>
            <span>({{ subwayDist }}m)</span>
          </div>
        </div>

        <div class="face d-flex justify-content-center mt-3">
          <div v-if="subwayDist < 400" class="d-flex flex-column">
            <img
              src="../../assets/green.png"
              style="max-height: 40px; margin: 10px auto"
              alt=""
            />
            <span style="font-size: 0.8em">도보 5분 이내!</span>
          </div>
          <div v-else-if="subwayDist < 1000" class="d-flex flex-column">
            <img
              src="../../assets/yellow.png"
              style="max-height: 40px; margin: 10px auto"
              alt=""
            />
            <span style="font-size: 0.8em">도보 15분 이내</span>
          </div>
          <div v-else class="d-flex flex-column">
            <img
              src="../../assets/red.png"
              style="max-height: 40px; margin: 10px auto"
              alt=""
            />
            <span style="font-size: 0.8em">너무 멀어요ㅠㅠ</span>
          </div>
          <i
            id="subtooltip"
            style="color: #6068fb; height: 16px"
            class="far fa-question-circle"
            @mouseover="subover"
            @mouseleave="subleave"
            v-b-tooltip.hove.bottom.html.v-light="subtip"
          ></i>
        </div>
      </div>

      <div class="d-flex flex-column">
        <div class="badge" style="width: 200px" @click="clickStore">
          <img src="../../assets/convenientstore.png" style="height: 80px" />
          <div class="d-flex flex-column mt-2" style="font-size: 1.3em">
            <span style="display: none">{{ store_text }}</span>
            <span>{{ convenient }}</span>
            <span>({{ convenientDist }}m)</span>
          </div>
        </div>
        <div class="face d-flex justify-content-center mt-3">
          <div v-if="convenientDist < 150" class="d-flex flex-column">
            <img
              src="../../assets/green.png"
              style="max-height: 40px; margin: 10px auto"
              alt=""
            />
            <span style="font-size: 0.8em">바로 코앞!</span>
          </div>
          <div v-else-if="convenientDist < 300" class="d-flex flex-column">
            <img
              src="../../assets/yellow.png"
              style="max-height: 40px; margin: 10px auto"
              alt=""
            />
            <span style="font-size: 0.8em">걸어갈 만 해요</span>
          </div>
          <div v-else class="d-flex flex-column">
            <img
              src="../../assets/red.png"
              style="max-height: 40px; margin: 10px auto"
              alt=""
            />
            <span style="font-size: 0.8em">쪼오금만 걸어봅시다</span>
          </div>
          <i
            id="convtooltip"
            style="color: #6068fb; height: 16px"
            class="far fa-question-circle"
            @mouseover="convover"
            @mouseleave="convleave"
            v-b-tooltip.hove.bottom.html.v-light="convtip"
          ></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import EventBus from "@/api/EventBus.js";
import { mapActions, mapState } from "vuex";
const mapStore = "mapStore";
export default {
  name: "HouseDetail",
  computed: {
    ...mapState(mapStore, ["house", "subway_text", "store_text"]),
    ...mapState("interestedAptStore", ["interestedApt"]),
    ...mapState("memberStore", ["userInfo"]),
  },
  data() {
    return {
      subway: "",
      subwayDist: 0,
      convenient: "",
      convenientDist: 0,
      check_interest: false,
      subtip: {
        title: `
        <div style= "font-family: 'RIDIBatang'; width:200px ;text-align:left; background-color:#dcdeef; border-radius:10px;padding:10px ">
          <strong style= "font-size: 1.2em;">지하철 거리 정보</strong>
          <br/>
          <div class="d-flex justify-content-left ">
            <div style="width:60px;text-align:right">~400m: </div>
            <div>&nbsp;&nbsp;&nbsp; 5분 이내</div>
          </div>
          <div class="d-flex justify-content-left ">
            <div style="width:60px;text-align:right">~1,000m: </div>
            <div>&nbsp;&nbsp;&nbsp; 15분 이내</div>
          </div>
         <div class="d-flex justify-content-left ">
            <div style="width:60px;text-align:right">1,000m~: </div>
            <div>&nbsp;&nbsp;&nbsp; 멀어요...</div>
          </div>
        </div>
      `,
      },
      convtip: {
        title: `
        <div style= "font-family: 'RIDIBatang'; width:200px ;text-align:left; background-color:#dcdeef; border-radius:10px;padding:10px ">
          <strong style= "font-size: 1.2em;">편의점 거리 정보</strong>
          <br/>
          <div class="d-flex justify-content-left">
            <div style="width:60px;text-align:right">~150m: </div>
            <div>&nbsp;&nbsp;&nbsp;코앞!</div>
          </div>
          <div class="d-flex justify-content-left" >
            <div style="width:60px;text-align:right">~300m: </div>
            <div> &nbsp;&nbsp;&nbsp;갈만한 거리</div>
          </div>
         <div class="d-flex justify-content-left" >
            <div style="width:60px;text-align:right">300m~: </div>
            <div> &nbsp;&nbsp;&nbsp;조금만 가보죠</div>
          </div>
        </div>
      `,
      },
    };
  },
  mounted() {
    this.show_road_view();
  },
  created() {
    let sub = this.subway_text.split(` (`);
    this.subway = sub[0];
    this.subwayDist = sub[1].substr(0, sub[1].length - 2);
    console.log(this.subway_text);

    let con = this.store_text.split(` (`);
    this.convenient = con[0];
    this.convenientDist = con[1].substr(0, con[1].length - 2);
  },

  updated() {
    let sub = this.subway_text.split(` (`);
    this.subway = sub[0];
    this.subwayDist = sub[1].substr(0, sub[1].length - 2);
    console.log(this.subway_text);

    let con = this.store_text.split(` (`);
    this.convenient = con[0];
    this.convenientDist = con[1].substr(0, con[1].length - 2);
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    ...mapActions("interestedAptStore", [
      "insert_interestedApt",
      "delete_interestedApt",
    ]),
    clickApt() {
      EventBus.$emit("display_apt", "안녕");
    },

    clickSubway() {
      EventBus.$emit("push-subway", "안녕");
    },
    clickStore() {
      EventBus.$emit("push-store", "안녕");
    },
    check_myInterest() {
      this.check_interest = false;
      for (let i = 0; i < this.interestedApt.length; i++) {
        if (this.interestedApt[i].aptCode == this.house.aptCode) {
          this.check_interest = true;
          break;
        }
      }
    },
    subover() {
      document.getElementById("subtooltip").classList.add("fas");
      document.getElementById("subtooltip").classList.remove("far");
    },
    subleave() {
      document.getElementById("subtooltip").classList.remove("fas");
      document.getElementById("subtooltip").classList.add("far");
    },
    convover() {
      document.getElementById("convtooltip").classList.add("fas");
      document.getElementById("convtooltip").classList.remove("far");
    },
    convleave() {
      document.getElementById("convtooltip").classList.remove("fas");
      document.getElementById("convtooltip").classList.add("far");
    },
    show_road_view() {
      var rvContainer = this.$refs.roadview; // 로드뷰를 표시할 div

      var rv = new window.kakao.maps.Roadview(rvContainer); // 로드뷰 객체 생성
      var rc = new window.kakao.maps.RoadviewClient(); // 좌표를 통한 로드뷰의 panoid를 추출하기 위한 로드뷰 help객체 생성
      var rvPosition = new window.kakao.maps.LatLng(
        this.house.lat,
        this.house.lng
      );

      rc.getNearestPanoId(rvPosition, 100, function (panoid) {
        rv.setPanoId(panoid, rvPosition); //좌표에 근접한 panoId를 통해 로드뷰를 실행합니다.
      });

      // 로드뷰 초기화 이벤트
      window.kakao.maps.event.addListener(rv, "init", function () {
        // 로드뷰에 올릴 마커를 생성합니다.
        var rMarker = new window.kakao.maps.Marker({
          position: rvPosition,
          map: rv, //map 대신 rv(로드뷰 객체)로 설정하면 로드뷰에 올라갑니다.
        });
        rMarker.setAltitude(6); //마커의 높이를 설정합니다. (단위는 m입니다.)
        rMarker.setRange(100); //마커가 보일 수 있는 범위를 설정합니다. (단위는 m입니다.)
        // 로드뷰 마커가 중앙에 오도록 로드뷰의 viewpoint 조정합니다.
        var projection = rv.getProjection(); // viewpoint(화면좌표)값을 추출할 수 있는 projection 객체를 가져옵니다.

        // 마커의 position과 altitude값을 통해 viewpoint값(화면좌표)를 추출합니다.
        var viewpoint = projection.viewpointFromCoords(
          rMarker.getPosition(),
          rMarker.getAltitude()
        );
        rv.setViewpoint(viewpoint); //로드뷰에 뷰포인트를 설정합니다.
      });
    },
  },
  watch: {
    house(newVal) {
      console.log(newVal);
      this.check_myInterest();
      this.show_road_view();
    },
    interestedApt() {
      this.check_myInterest();
    },
  },
};
</script>

<style scoped lang="scss">
table {
  font-size: 9pt !important;
}
.badge:hover {
  cursor: pointer;
}

.face {
  i:hover {
    cursor: pointer;
  }
  margin-left: 8%;
}
</style>
