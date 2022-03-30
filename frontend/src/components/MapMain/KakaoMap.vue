<template>
  <div class="map_wrap">
    <div ref="map" class="kmap" style="width: 100%; height: 100%"></div>
    <ul id="category" ref="category">
      <li id="BK9" data-order="0">
        <span class="category_bg bank"></span>
        은행
      </li>
      <li id="MT1" data-order="1">
        <span class="category_bg mart"></span>
        마트
      </li>
      <li id="PM9" data-order="2">
        <span class="category_bg pharmacy"></span>
        약국
      </li>
      <li id="SW8" data-order="3">
        <span class="category_bg oil"></span>
        지하철
      </li>
      <li id="CE7" data-order="4">
        <span class="category_bg cafe"></span>
        카페
      </li>
      <li id="CS2" data-order="5">
        <span class="category_bg store"></span>
        편의점
      </li>
    </ul>
    <div ref="contentNode"></div>
    <div ref="contentNode_house"></div>
    <div ref="contentNode_store"></div>
    <div ref="contentNode_subway"></div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import EventBus from "@/api/EventBus.js";
const mapStore = "mapStore";
let kakao = window.kakao;
export default {
  props: { options: Object, toggle: Boolean, mytoggle: Boolean },
  data() {
    return {
      mapInstance: null,
      markers_house: [],
      markers_category: [],
      marker_store: null,
      marker_subway: null,
      placeOverlay: null,
      houseOverlay: null,
      storeOverlay: null,
      subwayOverlay: null,
      contentNode: null,
      contentNode_house: null,
      contentNode_store: null,
      contentNode_subway: null,
      currCategory: "",
      ps: null,
    };
  },

  created() {
    EventBus.$on("push-subway", (payload) => {
      if (this.marker_subway == null) this.displaySubwayMarker();
      else this.removeMarker_subway();
      console.log(payload);
    });
    EventBus.$on("push-store", (payload) => {
      if (this.marker_store == null) this.displayStoreMarker();
      else this.removeMarker_store();
      console.log(payload);
    });
    EventBus.$on("display_apt", (payload) => {
      this.displayPlacehouse(this.house);
      console.log(payload);
    });
  },
  computed: {
    ...mapState(mapStore, [
      "houses",
      "house",
      "store",
      "subway",
      "store_test",
      "subway_text",
    ]),
  },
  mounted() {
    kakao = kakao || window.kakao;
    console.log(this.$refs.map); // should be not null

    var container = this.$refs.map;

    const { center, level } = this.options;
    this.mapInstance = new kakao.maps.Map(container, {
      center: new kakao.maps.LatLng(center.lat, center.lng),
      level,
    });

    this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
    this.houseOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
    this.storeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
    this.subwayOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
    this.contentNode = this.$refs.contentNode;
    this.contentNode_house = this.$refs.contentNode_house;
    this.contentNode_store = this.$refs.contentNode_store;
    this.contentNode_subway = this.$refs.contentNode_subway;

    this.ps = new kakao.maps.services.Places(this.mapInstance);
    //마커를 표시합니다
    this.displayMarker();

    // 지도에 idle 이벤트를 등록합니다
    kakao.maps.event.addListener(this.mapInstance, "idle", this.searchPlaces);

    // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
    this.contentNode.className = "placeinfo_wrap";
    this.contentNode_house.className = "placeinfo_wrap";
    this.contentNode_store.className = "placeinfo_wrap";
    this.contentNode_subway.className = "placeinfo_wrap";
    // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
    // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
    this.addEventHandle(
      this.contentNode,
      "mousedown",
      kakao.maps.event.preventMap
    );
    this.addEventHandle(
      this.contentNode,
      "touchstart",
      kakao.maps.event.preventMap
    );
    this.addEventHandle(
      this.contentNode_house,
      "mousedown",
      kakao.maps.event.preventMap
    );
    this.addEventHandle(
      this.contentNode_house,
      "touchstart",
      kakao.maps.event.preventMap
    );
    this.addEventHandle(
      this.contentNode_store,
      "mousedown",
      kakao.maps.event.preventMap
    );
    this.addEventHandle(
      this.contentNode_store,
      "touchstart",
      kakao.maps.event.preventMap
    );
    this.addEventHandle(
      this.contentNode_subway,
      "mousedown",
      kakao.maps.event.preventMap
    );
    this.addEventHandle(
      this.contentNode_subway,
      "touchstart",
      kakao.maps.event.preventMap
    );
    // 커스텀 오버레이 컨텐츠를 설정합니다
    this.placeOverlay.setContent(this.contentNode);
    this.houseOverlay.setContent(this.contentNode_house);
    this.storeOverlay.setContent(this.contentNode_store);
    this.subwayOverlay.setContent(this.contentNode_subway);

    // 각 카테고리에 클릭 이벤트를 등록합니다
    this.addCategoryClickEvent();

    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 커스텀 오버레이를 숨깁니다.
    kakao.maps.event.addListener(this.mapInstance, "click", () => {
      this.placeOverlay.setMap(null);
      this.houseOverlay.setMap(null);
      this.storeOverlay.setMap(null);
      this.subwayOverlay.setMap(null);
    });
  },
  methods: {
    ...mapActions(mapStore, ["detailHouse", "setStore", "setSubway"]),
    //근처 편의점, 지하철 마커
    displayStoreMarker() {
      //편의점
      this.removeMarker_store();
      var marker = this.addMarker_store(
        new kakao.maps.LatLng(this.store.y, this.store.x)
      );
      kakao.maps.event.addListener(marker, "click", () => {
        this.displayPlaceStore(this.store);
      });
    },
    displaySubwayMarker() {
      //지하철
      this.removeMarker_subway();
      var marker2 = this.addMarker_subway(
        new kakao.maps.LatLng(this.subway.y, this.subway.x)
      );
      kakao.maps.event.addListener(marker2, "click", () => {
        this.displayPlaceSubway(this.subway);
      });
    },
    displayMarker() {
      this.removeMarkers_apt();
      console.log("[아파트 목록]", this.houses);
      this.houses.forEach((house, index) => {
        console.log("[아파트 목록]", index, house);
        // create markers
        var marker = this.addMarker_h(
          new kakao.maps.LatLng(house.lat, house.lng),
          index
        );
        var move_lng = 0;
        var t = this.mapInstance.getLevel();
        if (this.toggle) move_lng += 0.0003 / 2 ** -t;
        if (this.mytoggle) move_lng += 0.0003 / 2 ** -t;
        this.mapInstance.panTo(
          new kakao.maps.LatLng(
            parseFloat(house.lat),
            parseFloat(house.lng) + move_lng
          )
        );
        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "click", () => {
          this.displayPlacehouse(house);
          this.detailHouse(house);
        });
      });
    },

    displayPlaceStore(place) {
      console.log("store_palce");
      var content =
        '<div class="placess">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content +=
          '    <span title="' +
          place.address_name +
          '">' +
          place.address_name +
          "</span>";
      }

      content +=
        '    <span class="tel">' +
        place.phone +
        "</span>" +
        "</div>" +
        '<div class="after"></div>';

      this.contentNode_store.innerHTML = content;
      this.storeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.storeOverlay.setMap(this.mapInstance);
    },
    displayPlaceSubway(place) {
      console.log("subway_palce");
      console.log(place);
      var content =
        '<div class="placess">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content +=
          '    <span title="' +
          place.address_name +
          '">' +
          place.address_name +
          "</span>";
      }

      content +=
        '    <span class="tel">' +
        place.phone +
        "</span>" +
        "</div>" +
        '<div class="after"></div>';

      this.contentNode_subway.innerHTML = content;
      this.subwayOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.subwayOverlay.setMap(this.mapInstance);
    },
    displayPlacehouse(place) {
      console.log(place);
      console.log(this.toggle);
      console.log(this.mytoggle);
      var move_lng = 0;
      var t = this.mapInstance.getLevel();
      if (this.toggle) move_lng += 0.0003 / 2 ** -t;
      if (this.mytoggle) move_lng += 0.0003 / 2 ** -t;
      //지도 클릭한 매물로 이동
      console.log(move_lng);
      this.mapInstance.panTo(
        new kakao.maps.LatLng(
          parseFloat(place.lat),
          parseFloat(place.lng) + move_lng
        )
      );

      this.currCategory = "";

      var content = '<div class="placehouse">';
      content +=
        '    <span title="' +
        place.aptName +
        '" class="title">' +
        place.aptName +
        "</span>";
      content +=
        '<span title="' +
        place.recentPrice +
        '">최근 거래가 : ' +
        place.recentPrice +
        "만 원</span>";
      content += "</div>" + '<div class="after"></div>';

      this.contentNode_house.innerHTML = content;

      this.houseOverlay.setPosition(
        new kakao.maps.LatLng(place.lat, place.lng)
      );
      this.houseOverlay.setMap(this.mapInstance);
    },

    //편의점 마커를 생성하고 지도위에 표시하는 함수입니다.
    addMarker_store(position) {
      var imageSrc =
          "https://user-images.githubusercontent.com/49037411/143152043-d0a1163f-926d-4caa-9a66-d00c184ae660.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(44, 45), // 마커 이미지의 크기
        imgOptions = {
          offset: new kakao.maps.Point(22, 45), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        // 마커를 생성합니다
        marker = new kakao.maps.Marker({
          position: position,
          image: markerImage,
        });
      console.log(marker);
      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(this.mapInstance);
      // 생성된 마커를 배열에 추가합니다
      this.marker_store = marker;
      return marker;
    },
    //지하철 마커를 생성하고 지도위에 표시하는 함수입니다.
    addMarker_subway(position) {
      var imageSrc =
          "https://user-images.githubusercontent.com/49037411/143152144-83e4e887-18ae-4dc0-aaf9-1e21498c5c8a.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(44, 45), // 마커 이미지의 크기
        imgOptions = {
          offset: new kakao.maps.Point(22, 45), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        // 마커를 생성합니다
        marker = new kakao.maps.Marker({
          position: position,
          image: markerImage,
        });
      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(this.mapInstance);
      // 생성된 마커를 배열에 추가합니다
      this.marker_subway = marker;
      return marker;
    },
    // 마커를 생성하고 지도위에 표시하는 함수입니다
    addMarker_h(position, idx) {
      var imageSrc =
          "http://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_normal.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(52, 53), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(644, 946), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(
            410 - 52 * parseInt((idx % 45) / 15),
            ((idx % 45) % 15) * 52 + 5
          ), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(26, 53), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        // 마커를 생성합니다
        marker = new kakao.maps.Marker({
          position: position,
          image: markerImage,
        });
      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(this.mapInstance);

      // 생성된 마커를 배열에 추가합니다
      this.markers_house.push(marker);
      return marker;
    },

    // 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
    setMarkers(map) {
      for (var i = 0; i < this.markers_house.length; i++) {
        this.markers_house[i].setMap(map);
      }
    },
    removeMarkers_apt() {
      for (var i = 0; i < this.markers_house.length; i++) {
        this.markers_house[i].setMap(null);
      }
      this.markers_house = [];
    },
    //편의점 마커 삭제
    removeMarker_store() {
      if (this.marker_store != null) this.marker_store.setMap(null);
      this.marker_store = null;
    },
    //지하철 마커 삭제
    removeMarker_subway() {
      if (this.marker_subway != null) this.marker_subway.setMap(null);
      this.marker_subway = null;
    },

    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },

    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },

    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status, pagination) {
      console.log(pagination);
      console.log("data" + data);
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },

    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order;
      var category = this.$refs.category.children;
      for (let i = 0; i < category.length; i++) {
        if (category[i].className === "on") {
          order = i;
          break;
        }
      }

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker_c(
          new kakao.maps.LatLng(places[i].y, places[i].x),
          order
        );

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        ((marker, place) => {
          kakao.maps.event.addListener(marker, "click", () => {
            this.displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker_c(position, order) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.mapInstance); // 지도 위에 마커를 표출합니다
      this.markers_category.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers_category.length; i++) {
        this.markers_category[i].setMap(null);
      }
      this.markers_category = [];
    },

    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      console.log("displayInfo");
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content +=
          '    <span title="' +
          place.address_name +
          '">' +
          place.address_name +
          "</span>";
      }

      content +=
        '    <span class="tel">' +
        place.phone +
        "</span>" +
        "</div>" +
        '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.mapInstance);
    },

    // 각 카테고리에 클릭 이벤트를 등록합니다
    addCategoryClickEvent() {
      var category = this.$refs.category,
        children = category.children;
      console.log(this.$refs.category);
      for (var i = 0; i < children.length; i++) {
        children[i].onclick = (child) => {
          console.log(child.path);
          console.log(child.path[0].children.length);
          if (child.path[0].children.length == 0) {
            this.onClickCategory(child.path[1]);
          } else if (child.path[0].children.length == 1) {
            this.onClickCategory(child.path[0]);
          }
        };
        // children[i].addEventListener("click", () => {
        //   this.onClickCategory(children[i]);
        // });
      }
    },

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(child) {
      console.log("click");
      var id = child.id;
      var className = child.className;
      console.log(child);
      this.placeOverlay.setMap(null);

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker();
      } else {
        this.currCategory = id;
        this.changeCategoryClass(child);
        this.searchPlaces();
      }
    },

    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(el) {
      var category = this.$refs.category,
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (el) {
        el.className = "on";
      }
    },

    //장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB_ss(data, status, pagination) {
      console.log("searchcb" + status + " " + pagination);

      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces_ss(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        if (this.currCategory == "SW8") {
          this.setSubway(["없음", null]);
        }
        if (this.currCategory == "CS2") {
          this.setStore(["없음", null]);
        }
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        if (this.currCategory == "SW8") {
          this.setSubway(["없음", null]);
        }
        if (this.currCategory == "CS2") {
          this.setStore(["없음", null]);
        }
      }
    },
    displayPlaces_ss(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var store_min = 1000;
      var submay_min = 2000;
      var distance;
      for (var i = 0; i < places.length; i++) {
        ((place) => {
          if (place.category_group_code == "SW8") {
            console.log("sw8");
            console.log(place);
            distance = this.calcDistance(
              this.house.lat,
              this.house.lng,
              place.y,
              place.x
            );
            if (parseFloat(submay_min) > parseFloat(distance)) {
              submay_min = distance;
              this.setSubway([
                place.place_name + " (" + distance + "m)",
                place,
              ]);
            }
          } else if (place.category_group_code == "CS2") {
            distance = this.calcDistance(
              this.house.lat,
              this.house.lng,
              place.y,
              place.x
            );
            if (parseFloat(store_min) > parseFloat(distance)) {
              store_min = distance;
              this.setStore([place.place_name + " (" + distance + "m)", place]);
            }
          }
        })(places[i]);
      }
    },

    //거리 계산 함수
    calcDistance(lat1, lon1, lat2, lon2) {
      var theta = lon1 - lon2;
      var dist =
        Math.sin(this.deg2rad(lat1)) * Math.sin(this.deg2rad(lat2)) +
        Math.cos(this.deg2rad(lat1)) *
          Math.cos(this.deg2rad(lat2)) *
          Math.cos(this.deg2rad(theta));
      dist = Math.acos(dist);
      dist = this.rad2deg(dist);
      dist = dist * 60 * 1.1515;
      dist = dist * 1.609344;
      return Number(dist * 1000).toFixed(2);
    },

    deg2rad(deg) {
      return (deg * Math.PI) / 180;
    },
    rad2deg(rad) {
      return (rad * 180) / Math.PI;
    },
  },
  watch: {
    houses(newVal) {
      console.log(newVal);
      this.placeOverlay.setMap(null);
      this.houseOverlay.setMap(null);
      this.storeOverlay.setMap(null);
      this.subwayOverlay.setMap(null);
      this.removeMarker_subway();
      this.removeMarker_store();
      this.displayMarker();
    },
    house(newVal) {
      this.setSubway(["없음 (2000m)", null]);
      this.currCategory = "SW8";
      this.ps.categorySearch(this.currCategory, this.placesSearchCB_ss, {
        location: new kakao.maps.LatLng(newVal.lat, newVal.lng),
        radius: 2000,
      });
      this.setStore(["없음 (1000m)", null]);
      this.currCategory = "CS2";
      this.ps.categorySearch(this.currCategory, this.placesSearchCB_ss, {
        location: new kakao.maps.LatLng(newVal.lat, newVal.lng),
        radius: 1000,
      });

      this.displayPlacehouse(newVal);
      this.storeOverlay.setMap(null);
      this.subwayOverlay.setMap(null);
      this.removeMarker_subway();
      this.removeMarker_store();
    },
    "options.level"(cur, prev) {
      console.log(`[LEVEL CHANGED] ${prev} => ${cur}`); // for testing
      this.mapInstance.setLevel(cur);
    },
    "options.center"(cur) {
      // console.log("[NEW CENTER]", cur.lat, cur.lng); // for test
      this.mapInstance.setCenter(new kakao.maps.LatLng(cur.lat, cur.lng));
    },
  },
};
</script>

<style>
.kmap {
  height: 100%;
}
#map {
  width: 100%;
  height: 100%;
}
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100% !important;
}
#category {
  position: absolute;
  top: 10px;
  left: 100px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px #00000066;
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}

#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap,
.placehouse_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placess,
.placehouse,
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placess:nth-of-type(n),
.placehouse:nth-of-type(n),
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placehouse_wrap .after,
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placess a,
.placess a:hover,
.placess a:active,
.placehouse a,
.placehouse a:hover,
.placehouse a:active,
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placess a,
.placess span,
.placehouse a,
.placehouse span,
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placess span,
.placehouse span,
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placess .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #50d950;
  background: #50d950
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placehouse .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #5050d9;
  background: #5050d9
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
