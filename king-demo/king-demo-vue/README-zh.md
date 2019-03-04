1、本项目分级别 user，vip，svip，admin，super
  user：普通用户
  vip：会员用户
  svip：超级用户
  admin：管理用户      》仅逻辑删除数据、恢复数据
  super：超级管理用户  》可物理删除数据
  
2、全球化 
  @尽量丢弃前缀，比如 table.user.id 》 id
  
3、分功能，分目录，分文件
  @相同功能的，在一个目录，比如 api、views
  @同一个菜单栏，不同子页面，比如 lang>en.js 、lang>zh.js
  
4、
  