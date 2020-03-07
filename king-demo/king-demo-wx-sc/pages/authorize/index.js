// pages/authorize/index.js
var app = getApp();
var api = require('../../utils/api.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {},
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {},
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {},

  bindGetUserInfo: function() {
    wx.login({
      success: function(wxres) {
        let code = wxres.code; // 微信登录接口返回的 code 参数，下面注册接口需要用到
        console.info(code)
        wx.getUserInfo({
          success: function(wxres2) {
            console.info(wxres2)
            api.register(code, wxres2.encryptedData, wxres2.iv, function(res) {
              if (res.code != 200) {
                wx.showModal({
                  title: '提示',
                  content: '注册失败' + res.data.msg,
                  showCancel: false
                })
              } else {
                wx.hideLoading();
                console.log("注册且登录成功");
                let userInfo = res.data.user
                app.globalData.token = res.data.token
                app.globalData.nickName = userInfo.nickName
                app.globalData.phoneNumber = userInfo.phoneNumber
                app.globalData.avatarUrl = userInfo.avatarUrl
                wx.switchTab({
                  url: '/pages/my/index',
                });
              }
            });
          }
        })
      }
    })
  }
})