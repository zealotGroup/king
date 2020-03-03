//app.js
App({
  onLaunch: function () {
    let userInfo = wx.getStorageSync('userInfo');
    if (!userInfo) {
      wx.switchTab({
        url: '/pages/authorize/index',
      });
    } else {
      wx.switchTab({
        url: '/pages/start/start',
      });
    }
  },
  globalData: {
    userInfo: null,
    version: "1.0.0",
    shareProfile: "百款精品商品，总有一款适合您", // 首页转发的时候话术
  }
})