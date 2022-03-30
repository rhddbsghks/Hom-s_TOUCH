import http from "@/util/http-common.js";

const interestedAptStore = {
  namespaced: true,
  state: {
    interestedApt: [],
  },
  mutations: {
    SET_INTERESTED_APT_LIST(state, aptList) {
      state.interestedApt = aptList;
    },
  },
  actions: {
    get_interestedApt({ commit }, id) {
      const params = { id: id };
      http
        .get("/api/interesteApt/list", { params })
        .then((response) => {
          console.log(response.data);
          commit("SET_INTERESTED_APT_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    insert_interestedApt({ dispatch }, [id, aptCode, aptName]) {
      console.log("insert");
      const params = { id: id, aptCode: aptCode, aptName: aptName };
      console.log(params);
      http
        .post("/api/interesteApt/insert", {
          id: id,
          aptCode: aptCode,
          aptName: aptName,
        })
        .then((response) => {
          console.log(response);
          dispatch("get_interestedApt", id);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    delete_interestedApt({ dispatch }, [id, aptCode]) {
      console.log("insert");
      const params = { id: id, aptCode: aptCode };
      console.log(params);
      http
        .post("/api/interesteApt/delete", {
          id: id,
          aptCode: aptCode,
        })
        .then((response) => {
          console.log(response);
          dispatch("get_interestedApt", id);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    update_interestedApt({ dispatch }, [id, aptCode, aptNickName]) {
      console.log("insert");
      const params = { id: id, aptCode: aptCode, aptNickName: aptNickName };
      console.log(params);
      http
        .put("/api/interesteApt/update", {
          id: id,
          aptCode: aptCode,
          aptNickName: aptNickName,
        })
        .then((response) => {
          console.log(response);
          dispatch("get_interestedApt", id);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default interestedAptStore;
