import axios from 'axios'
import router from "./src/router";
import {Toast} from 'vant';

export const http = axios.create({
    baseURL: 'http://localhost:14514/api'
})

export default http

http.interceptors.request.use(config => {
    if (localStorage.getItem('1145141919810')) {
        config.headers.token = localStorage.getItem('1145141919810')
    }
    return config
}, error => Promise.reject(error))
http.interceptors.response.use(response => {
    if (response.data.status === 'token_error') {
        router.push('/login').then(r => r)
        Toast.fail('请先登录')
    } else if (response.data.status === 'error') {
        Toast.fail(response.data.msg)
    }
    return response
}, error => Promise.reject(error))