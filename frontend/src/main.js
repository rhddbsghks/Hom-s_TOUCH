import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueSidebarMenu from "vue-sidebar-menu";
// import Vuebar from "vuebar";
import "vue-sidebar-menu/dist/vue-sidebar-menu.css";

import VTooltip from "v-tooltip";

Vue.use(VTooltip);

Vue.config.productionTip = false;

// Vue.use(Vuebar);
Vue.use(VueSidebarMenu);

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);

Vue.config.productionTip = false;

import Chartkick from "vue-chartkick";
import Chart from "chart.js";

Vue.use(Chartkick.use(Chart));

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
