import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import createPersistedState from "vuex-persistedstate";

import memberStore from "@/store/modules/memberStore.js";

import mapStore from "@/store/modules/mapStore.js";

import myhouseStore from "@/store/modules/myhouseStore.js";
import interestedAptStore from "@/store/modules/interestedAptStore.js";

const store = new Vuex.Store({
  modules: {
    mapStore,
    memberStore,
    myhouseStore,
    interestedAptStore,
  },
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});

export default store;
