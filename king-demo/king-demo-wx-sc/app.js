//app.js
App({
  onLaunch: function() {
  },
  
  globalData: {
    // base_url: 'http://localhost:8080/api',
    base_url: 'https://www.zealot.group/api',
    token: null,
    nickName: null,
    phoneNumber: null,
    avatarUrl: null,
    version: "1.0.1",
    appName: '鑫磊五金',
    shareProfile: "五金用品，来鑫磊五金小店", // 首页转发的时候话术
  }
})