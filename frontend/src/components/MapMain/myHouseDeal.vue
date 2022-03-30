<template>
  <div>
    <div class="d-flex mt-5">
      <b-form-datepicker
        size="sm"
        v-model="value_start"
        :min="min"
        :max="max"
        locale="ko-kr"
        :date-format-options="{
          year: 'numeric',
          month: 'numeric',
          day: 'numeric',
        }"
      ></b-form-datepicker>
      <span class="ml-3 mr-3" style="font-size: 1.5em"> ~ </span>

      <b-form-datepicker
        size="sm"
        v-model="value_end"
        :min="min"
        :max="max"
        locale="ko-kr"
        :date-format-options="{
          year: 'numeric',
          month: 'numeric',
          day: 'numeric',
        }"
      ></b-form-datepicker>

      <b-button
        block
        style="width: 30%; background-color: #7a7ad8; border: none"
        class="ml-2"
        @click="toSearch"
        >검색</b-button
      >
    </div>
    <div
      style="width: 15%"
      class="mt-4 toggle"
      v-if="this.graph"
      @click="graph = !graph"
    >
      <i class="fas fa-chevron-up"></i>접기
    </div>
    <div
      style="width: 30%"
      class="mt-4 toggle"
      v-if="!this.graph"
      @click="graph = !graph"
    >
      <i class="fas fa-chevron-down"></i>그래프 펼치기
    </div>
    <line-chart
      :data="house_deal_group"
      :colors="['#00b', '#666', '#b00', '#0b0']"
      :download="true"
      :messages="{ empty: 'No data' }"
      adapter="chartjs"
      pointStyle="dash"
      :xmin="date_start"
      :xmax="date_end"
      v-if="graph"
      :points="false"
    ></line-chart>
    <div
      class="mt-5 toggle"
      style="width: 15%"
      v-if="this.dealList"
      @click="dealList = !dealList"
    >
      <i class="fas fa-chevron-up"></i>접기
    </div>
    <div
      class="mt-5 toggle"
      style="width: 30%"
      v-if="!this.dealList"
      @click="dealList = !dealList"
    >
      <i class="fas fa-chevron-down"></i>거래내역 펼치기
    </div>
    <b-table
      id="dealList"
      class="scrollbar"
      sticky-header
      responsive
      :items="house_deal"
      :fields="fields"
      sort-icon-left
      :sort-compare="daySort"
      style="max-height: 470px"
      v-if="dealList"
    >
      <template #cell(dealDate)="data" style="font-size: 15pt">
        {{ data.item.dealYear }}/{{ data.item.dealMonth }}/{{
          data.item.dealDay
        }}
      </template>
    </b-table>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const mapStore = "myhouseStore";
export default {
  name: "Housedeal",
  computed: {
    ...mapState(mapStore, [
      "myhouse",
      "date_start",
      "date_end",
      "house_deal",
      "house_deal_chart",
      "house_deal_group",
    ]),
  },
  data() {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    // 15th two months prior
    const minDate = new Date();
    minDate.setYear(2015);
    minDate.setMonth(0);
    minDate.setDate(1);
    // 15th in two months
    const maxDate = new Date(today);
    return {
      fields: [
        {
          key: "dealAmount",
          label: "거래 금액(만 원)",
          sortable: true,
        },
        {
          key: "area",
          label: "면적(m²)",
          sortable: true,
        },
        {
          key: "dealDate",
          label: "거래 일자",
          sortable: true,
        },
      ],

      min: minDate,
      max: new Date(maxDate),
      value_start: minDate,
      value_end: new Date(maxDate),
      graph: true,
      dealList: true,
    };
  },
  watch: {
    myhouse(newVal) {
      console.log(this.value_start);
      this.dealInfo(newVal.aptCode);
    },
  },
  mounted() {
    this.value_start = this.date_start;
    this.value_end = this.date_end;
    this.dealInfo(this.myhouse.aptCode);
  },

  methods: {
    ...mapActions(mapStore, ["dealInfo"]),
    ...mapMutations(mapStore, ["SET_HOUSE_DEAL", "GROUP_HOUSE_AREA"]),
    toSearch() {
      this.SET_HOUSE_DEAL([this.value_start, this.value_end]);
    },

    daySort(a, b, key) {
      if (key == "dealDate") {
        return 1;
      }
    },
  },
};
</script>

<style lang="scss">
.toggle:hover {
  cursor: pointer;
}
#dealList td {
  font-size: 1.5em;
}

#dealList th {
  font-size: 1.2em;
}
</style>
