const app = getApp()
var api = require('../../utils/api.js');

Page({
  data: {
  },
  onLoad() {
    let that = this
    that.setData({
      nickName: app.globalData.nickName,
      avatarUrl: app.globalData.avatarUrl,
      phoneNumber: app.globalData.phoneNumber,
      version: app.globalData.version
    })
  },
  onShow() {
    let that = this;
  },
  aboutUs: function() {
    wx.showModal({
      title: '关于我们',
      content: '你们的支持，是我们努力的源泉',
      showCancel: false
    })
  },
  getPhoneNumber: function(e) {
    if (!e.detail.errMsg || e.detail.errMsg != "getPhoneNumber:ok") {
      wx.showModal({
        title: '提示',
        content: '无法获取手机号码',
        showCancel: false
      })
      return;
    }
    var that = this;
    api.update_phone_number(e.detail.encryptedData, e.detail.iv, function(res) {

    })
  },
  logout: function() {
    app.globalData.token = null
    wx.navigateTo({
      url: '/pages/start/start'
    })
  },
  recharge: function() {
    wx.navigateTo({
      url: "/pages/recharge/index"
    })
  },
  withdraw: function() {
    wx.navigateTo({
      url: "/pages/withdraw/index"
    })
  }
})