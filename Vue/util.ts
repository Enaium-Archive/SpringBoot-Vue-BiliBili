import http from "./http";
import router from "@/router";
import {Toast} from "vant";

export const len = '^.{6,16}$'
export const lenAndAz = '^[A-Za-z0-9-]{6,16}'

export async function getId() {
    const id = await http.get('/getId');
    return id.data
}

export async function getUserInfo() {
    return getUserInfoById(await getId())
}

export async function getUserInfoById(userId: string) {
    const val = await http.get('/static/getUserInfo?userId=' + userId)
    const data = val.data.data
    const userInfo = new UserInfo()

    userInfo.id = data.id
    if (data.avatarUrl !== '') {
        userInfo.avatarUrl = data.avatarUrl
    }

    userInfo.nickname = data.nickname

    if (data.gender === 1) {
        userInfo.gender = "男"
    } else if (data.gender === 0) {
        userInfo.gender = "女"
    } else if (data.gender === -1) {
        userInfo.gender = "私密"
    }
    return userInfo
}

export function setUserInfo(userInfo: UserInfo) {
    if (userInfo.gender === '男') {
        userInfo.gender = 1;
    } else if (userInfo.gender === '女') {
        userInfo.gender = 0;
    } else if (userInfo.gender === '私密') {
        userInfo.gender = -1;
    }
    return http.post('/setUserInfo', userInfo)
}

export function setVideoInfo(videoInfo: VideoInfo) {
    videoInfo.category = videoInfo.category.id
    return http.post('/setVideoInfo', videoInfo)
}

export async function getVideoInfo(videoId: number) {

    const val = await http.get('/static/getVideoInfo?videoId=' + videoId)

    const data = val.data.data
    const videoInfo = new VideoInfo()
    if (data.picUrl !== '') {
        videoInfo.picUrl = data.picUrl
    }

    const category = await http.get('/static/getCategory');
    const cat = category.data.data[data.category - 1];

    videoInfo.category = new Category(cat.id, cat.title)

    videoInfo.id = data.id
    videoInfo.userId = data.userId
    videoInfo.title = data.title
    videoInfo.description = data.description

    return videoInfo
}

export async function isLogin() {
    const r = await http.get("/isLogin");
    return r.data
}

export async function checkLogin() {
    if (!await isLogin()) {
        router.push('/login').then(r => r)
        Toast.fail('请先登录')
    }
}

export class UserInfo {
    id: number = -1
    avatarUrl: string = ''
    nickname: string = ''
    description: string = ''
    gender: any = ''
}

export class VideoInfo {
    id: number = -1
    userId: number = -1
    description: string = ''
    category: any = new Category(-1, "")
    picUrl = ''
    title = ''
}

export class Category {
    id: number
    title: string

    constructor(id: number, title: string) {
        this.id = id;
        this.title = title;
    }
}

export async function getVideoByCategory(category: number) {
    const list = await http.get('/static/getVideoInfoByCategory?category=' + category);
    return list.data.data
}

export async function getUserVideoList(userId: string) {
    const list = await http.get('/static/getUserVideoList?userId=' + userId);
    return list.data.data
}