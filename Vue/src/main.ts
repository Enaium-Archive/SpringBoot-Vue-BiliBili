import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/vant.js'
import http from '../http';
// @ts-ignore
import video from 'video.js';
import 'video.js/dist/video-js.css'


Vue.prototype.$http = http
Vue.prototype.$video = video
Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
