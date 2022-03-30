<template>
  <b-row
    style="margin: 15px 0"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="2" class="text-center align-self-center">
      <span class="marker_image" ref="image"></span>
    </b-col>
    <b-col cols="10" class="align-self-center" style="font-size: 10pt">
      <strong style="font-size: 1.1em">{{ house.aptName }}</strong
      ><br />
      최근 거래 :
      {{ house.recentPrice }}만 원 &nbsp;&nbsp;&nbsp;&nbsp;
      {{ house.recentDeal }}
    </b-col>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";
const mapStore = "mapStore";
export default {
  name: "HouseListRow",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
    index: Number,
  },
  // created() {
  //   if(this.house.img!=null&&this.house.img!='')
  //     this.house.img
  //   console.log(this.house);
  // },
  mounted() {
    this.$refs.image.className =
      "marker_image marker_posiont_" + (this.index % 45);
  },
  methods: {
    ...mapActions(mapStore, ["detailHouse"]),
    colorChange(flag) {
      this.isColor = flag;
    },
    selectHouse() {
      this.detailHouse(this.house);
      console.log(this);
      this.$emit("toDetail");
    },

    Marker_Image() {
      var imageSrc =
        "http://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_normal.png";
      return imageSrc;
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
.marker_image {
  float: left;
  width: 36px;
  height: 37px;
  background: url(http://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_normal.png)
    no-repeat;
}
.marker_posiont_30 {
  background-position: -46px -10px;
}
.marker_posiont_31 {
  background-position: -46px -56px;
}
.marker_posiont_32 {
  background-position: -46px -102px;
}
.marker_posiont_33 {
  background-position: -46px -148px;
}
.marker_posiont_34 {
  background-position: -46px -194px;
}
.marker_posiont_35 {
  background-position: -46px -240px;
}
.marker_posiont_36 {
  background-position: -46px -286px;
}
.marker_posiont_37 {
  background-position: -46px -332px;
}
.marker_posiont_38 {
  background-position: -46px -378px;
}
.marker_posiont_39 {
  background-position: -46px -424px;
}
.marker_posiont_40 {
  background-position: -46px -470px;
}
.marker_posiont_41 {
  background-position: -46px -516px;
}
.marker_posiont_42 {
  background-position: -46px -562px;
}
.marker_posiont_43 {
  background-position: -46px -608px;
}
.marker_posiont_44 {
  background-position: -46px -654px;
}
.marker_posiont_15 {
  background-position: -90px -10px;
}
.marker_posiont_16 {
  background-position: -90px -56px;
}
.marker_posiont_17 {
  background-position: -90px -102px;
}
.marker_posiont_18 {
  background-position: -90px -148px;
}
.marker_posiont_19 {
  background-position: -90px -194px;
}
.marker_posiont_20 {
  background-position: -90px -240px;
}
.marker_posiont_21 {
  background-position: -90px -286px;
}
.marker_posiont_22 {
  background-position: -90px -332px;
}
.marker_posiont_23 {
  background-position: -90px -378px;
}
.marker_posiont_24 {
  background-position: -90px -424px;
}
.marker_posiont_25 {
  background-position: -90px -470px;
}
.marker_posiont_26 {
  background-position: -90px -516px;
}
.marker_posiont_27 {
  background-position: -90px -562px;
}
.marker_posiont_28 {
  background-position: -90px -608px;
}
.marker_posiont_29 {
  background-position: -90px -654px;
}
.marker_posiont_0 {
  background-position: -134px -10px;
}
.marker_posiont_1 {
  background-position: -134px -56px;
}
.marker_posiont_2 {
  background-position: -134px -102px;
}
.marker_posiont_3 {
  background-position: -134px -148px;
}
.marker_posiont_4 {
  background-position: -134px -194px;
}
.marker_posiont_5 {
  background-position: -134px -240px;
}
.marker_posiont_6 {
  background-position: -134px -286px;
}
.marker_posiont_7 {
  background-position: -134px -332px;
}
.marker_posiont_8 {
  background-position: -134px -378px;
}
.marker_posiont_9 {
  background-position: -134px -424px;
}
.marker_posiont_10 {
  background-position: -134px -470px;
}
.marker_posiont_11 {
  background-position: -134px -516px;
}
.marker_posiont_12 {
  background-position: -134px -562px;
}
.marker_posiont_13 {
  background-position: -134px -608px;
}
.marker_posiont_14 {
  background-position: -134px -654px;
}
</style>
