<template>
  <div style="margin: 40px auto">
    <b-container fluid class="text-center">
      <b-row>
        <b-col cols="8">
          가격(만 원)
          <b-row align-h="between">
            <b-col cols="5">
              <input
                style="width: 100%"
                type="number"
                v-model="value_price[0]"
              />
            </b-col>
            <b-col cols="2">~</b-col>
            <b-col cols="5">
              <input
                style="width: 100%"
                type="number"
                v-model="value_price[1]"
              />
            </b-col>
          </b-row>
          <VueSlider
            :min="0"
            :max="1000000"
            :interval="1000"
            v-model="value_price"
          />
        </b-col>
        <b-col align-self="center">
          <b-button id="searchBtn" block @click="toSearch">검색</b-button>
        </b-col>
      </b-row>
    </b-container>
    <b-container
      style="height: 100%"
      v-if="houses && houses.length != 0"
      class="bv-example-row mt-3"
    >
      <house-list-row
        v-for="(house, index) in houses"
        :key="index"
        :house="house"
        :index="index"
        v-on="$listeners"
      />
    </b-container>
    <b-container v-else class="bv-example-row mt-3">
      <b-row>
        <b-col><b-alert show> 목록이 없습니다.</b-alert></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import VueSlider from "vue-slider-component";
import HouseListRow from "@/components/MapMain/HouseListRow.vue";
import { mapState, mapActions } from "vuex";

import "vue-slider-component/theme/default.css";

const mapStore = "mapStore";
export default {
  name: "HouseList",
  components: {
    HouseListRow,
    VueSlider,
  },
  data() {
    return {
      value_price: [0, 1000000],
    };
  },
  computed: {
    ...mapState(mapStore, ["houses", "price_start", "price_end"]),
  },
  mounted() {
    this.value_price = [this.price_start, this.price_end];
  },
  methods: {
    ...mapActions(mapStore, ["setHouseList"]),
    toSearch() {
      this.setHouseList(this.value_price);
    },
  },
  watch: {
    // value_price(newVal) {
    //   console.log(newVal);
    //   this.setHouseList(newVal);
    // },
  },
};
</script>

<style lang="scss">
#searchBtn {
  background: #4e54c8; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #8f94fb,
    #4e54c8
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to right, #8f94fb, #4e54c8);
  border: 0px;
}

#searchBtn:hover {
  background: #3138c7; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #6e75fc,
    #323ac9
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to right, #646cff, #333bce);
  border: 0px;
}

#deleteBtn {
  border: 0px;
  background-image: linear-gradient(135deg, #f05f57 10%, #360940 100%);
  border-color: rgba(255, 255, 255, 0);
  border: 0px;
}
#deleteBtn:hover {
  background-image: linear-gradient(135deg, #a8453f 10%, #2d0d33 100%);
  border-color: rgba(255, 255, 255, 0);
  border: 0px;
}

#cancelBtn {
  border: 0px;
  background-image: linear-gradient(135deg, #706666 10%, #3f3741 100%);
  border-color: rgba(255, 255, 255, 0);
  border: 0px;
}
#cancelBtn:hover {
  background-image: linear-gradient(135deg, #363232 10%, #332b35 100%);
  border-color: rgba(255, 255, 255, 0);
  border: 0px;
}

#dropdown-right button {
  /* background-color: aqua; */
  background: #4e54c8; /* fallback for old browsers */

  background: linear-gradient(to right, #8f94fb9f, #4e54c8ab);
  border: 0px;
  width: 70px;
  height: 30px;
  border-radius: 10px;
  z-index: 10;
  font-size: 1em;
  padding: 0px;
}
#nick {
  div:hover {
    cursor: pointer;
  }
}
</style>
