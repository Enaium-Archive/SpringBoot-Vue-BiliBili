# SpringBoot-Vue-BiliBili

登录认证

个人信息

视频上传

## 前端

Vue 、Vant、TypeScript、Less

![image-20210626101413289](https://gitee.com/Enaium/imgbed/raw/master/image-20210626101413289.png)

![image-20210626101646874](https://gitee.com/Enaium/imgbed/raw/master/image-20210626101646874.png)

![image-20210626101704463](https://gitee.com/Enaium/imgbed/raw/master/image-20210626101704463.png)

## 后端

Sa-Token、MyBatisPlus、Maria

## SQL Script

```sql
create table category_list
(
	id bigint not null
		primary key,
	title text not null
);

create table user_info_list
(
	id bigint not null
		primary key,
	avatar_url text null,
	description tinytext null,
	gender tinyint null,
	nickname tinytext not null
);

create table user_list
(
	id bigint auto_increment
		primary key,
	username tinytext not null,
	password tinytext not null
);

create table user_video_list
(
	id bigint not null
		primary key,
	video_id_list text not null
);

create table video_info_list
(
	id bigint not null,
	user_id bigint null,
	description text not null,
	category bigint not null,
	pic_url text not null,
	title text not null
);

create table video_list
(
	id bigint auto_increment
		primary key,
	video_url text not null
);
```

