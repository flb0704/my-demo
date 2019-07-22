import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import myRoutes from './route-index.js'

Vue.config.productionTip = false;
Vue.use(VueRouter);

// import my router
const router = new VueRouter({
  routes : myRoutes
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
