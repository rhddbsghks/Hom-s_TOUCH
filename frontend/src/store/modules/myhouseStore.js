import http from "@/util/http-common.js";

const now = new Date();
const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
const minDate = new Date(2015, 0, 1);

const mapStore = {
  namespaced: true,
  state: {
    myhouse: null,
    aptCode: null,
    store_text: "",
    subway_text: "",
    subway: null,
    store: null,
    price_start: 0,
    price_end: 1000,

    date_start: minDate,
    date_end: new Date(today),
    house_deal: [],
    house_deal_origin: [],

    house_deal_group_last: [],
    house_deal_group: [],
    house_type: [],
  },
  mutations: {
    SET_DETAIL_HOUSE(state, house) {
      state.myhouse = house;
    },

    SET_STORE(state, store) {
      state.store_text = store[0];
      state.store = store[1];
    },
    SET_SUBWAY(state, subway) {
      state.subway_text = subway[0];
      state.subway = subway[1];
    },
    SET_DONGCODE(state, dongCode) {
      state.dongCode = dongCode;
      console.log(dongCode);
    },
    GET_HOUSE_DEAL(state, house_deal) {
      state.house_deal = [];
      state.house_deal_chart = [];
      state.date_start = new Date(state.date_start);
      state.date_end = new Date(state.date_end);
      state.house_deal_origin = house_deal;
      for (let i = 0; i < state.house_deal_origin.length; i++) {
        var date = new Date(
          state.house_deal_origin[i].dealYear,
          state.house_deal_origin[i].dealMonth - 1,
          state.house_deal_origin[i].dealDay
        );

        if (
          state.date_start.getTime() <= date.getTime() &&
          state.date_end.getTime() >= date.getTime()
        ) {
          state.house_deal.push(state.house_deal_origin[i]);
        }
      }
    },
    SET_HOUSE_DEAL_CHART(state, value) {
      console.log(value);
      for (let i = 0; i < state.house_deal_origin.length; i++) {
        var date = new Date(
          state.house_deal_origin[i].dealYear,
          state.house_deal_origin[i].dealMonth - 1,
          state.house_deal_origin[i].dealDay
        );
        for (let j = 0; j < state.house_type.length; j++) {
          if (state.house_type[j] == state.house_deal_origin[i].area) {
            state.house_deal_group_last[j].deal = state.house_deal_origin[
              i
            ].dealAmount.replace(",", "");
          }
          state.house_deal_group[j].data.push([
            date,
            state.house_deal_group_last[j].deal,
          ]);
        }
      }
      console.log(state.house_deal_group);
    },
    SET_HOUSE_DEAL(state, value_date) {
      state.house_deal = [];
      state.house_deal_chart = [];
      state.date_start = new Date(value_date[0]);
      state.date_end = new Date(value_date[1]);
      for (let i = 0; i < state.house_deal_origin.length; i++) {
        var date = new Date(
          state.house_deal_origin[i].dealYear,
          state.house_deal_origin[i].dealMonth - 1,
          state.house_deal_origin[i].dealDay
        );

        if (
          state.date_start.getTime() <= date.getTime() &&
          state.date_end.getTime() >= date.getTime()
        ) {
          state.house_deal.push(state.house_deal_origin[i]);
        }
      }
    },
    GROUP_HOUSE_AREA(state, g) {
      state.house_type = [];
      state.house_deal_group = [];
      state.house_deal_group_last = [];
      for (let i = 0; i < state.house_deal_origin.length; i++) {
        state.house_type.push(state.house_deal_origin[i].area);
      }
      state.house_type = [...new Set(state.house_type)];
      state.house_type.sort();
      state.house_deal_group = [];
      for (let i = 0; i < state.house_type.length; i++) {
        state.house_deal_group.push({
          name: state.house_type[i],
          data: [],
        });
        state.house_deal_group_last.push({
          deal: 0,
        });
      }
      console.log("group " + g + state.house_type);
      console.log(state.house_deal_group[1]);
    },
  },
  actions: {
    detailHouse({ commit }, aptCode) {
      const params = { aptCode: aptCode };
      http
        .get("/map/apt_detail", { params })
        .then((response) => {
          console.log(response.data);
          commit("SET_DETAIL_HOUSE", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    setStore({ commit }, store) {
      commit("SET_STORE", store);
    },
    setSubway({ commit }, subway) {
      commit("SET_SUBWAY", subway);
    },
    dealInfo({ commit }, aptCode) {
      const params = { aptCode: aptCode };
      http
        .get("/map/apt_deal", { params })
        .then((response) => {
          commit("GET_HOUSE_DEAL", response.data);
          commit("GROUP_HOUSE_AREA", "OK");
          commit("SET_HOUSE_DEAL_CHART", "set_chart");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default mapStore;
