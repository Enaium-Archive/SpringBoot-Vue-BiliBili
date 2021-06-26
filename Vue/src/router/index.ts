import Vue from 'vue'
import VueRouter, {RouteConfig} from 'vue-router'
import Home from '@/views/Home.vue'
import Register from "@/views/Register.vue";
import Login from "@/views/Login.vue";
import UserInfo from "@/views/UserInfo.vue";
import IsLogin from "@/views/IsLogin.vue";
import EditUserInfo from "@/views/EditUserInfo.vue";
import Logout from "@/views/Logout.vue";
import UploadVideo from "@/views/UploadVideo.vue";
import EditVideoInfo from "@/views/EditVideoInfo.vue";
import MyVideoList from "@/views/MyVideoList.vue";
import VideoPlay from "@/views/VideoPlay.vue";

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/userInfo',
        name: 'UserInfo',
        component: UserInfo
    }, {
        path: '/isLogin',
        name: 'IsLogin',
        component: IsLogin
    },
    {
        path: '/editUserInfo',
        name: 'EditUserInfo',
        component: EditUserInfo
    },
    {
        path: '/logout',
        name: 'Logout',
        component: Logout
    },
    {
        path: '/uploadVideo',
        name: 'UploadVideo',
        component: UploadVideo
    },
    {
        path: '/editVideoInfo',
        name: 'EditVideoInfo',
        component: EditVideoInfo
    },
    {
        path: '/myVideoList',
        name: 'MyVideoList',
        component: MyVideoList
    },
    {
        path: '/videoPlay',
        name: 'VideoPlay',
        component: VideoPlay
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router
