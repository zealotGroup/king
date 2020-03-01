// pages/authorize/index.js
var app = getApp();
var api = require('../../utils/api.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {},
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

  login: function() {
    let that = this;
    let token = wx.getStorageSync('token');
    if (token) {
      api.check_token(function(data) {
        if (data.data.timeout) {
          console.log("授权过期");
          wx.removeStorageSync('token')
          that.login();
          wx.navigateBack();
        }
      });
    } else {
      wx.login({
        success: function(res) {
          api.login(res.code, function(data) {
            if (data.code == 201) {
              console.log("未注册，去注册");
              that.registerUser();
            } else if (data.code != 200) {
              console.log("登录失败");
              wx.hideLoading();
              wx.showModal({
                title: '提示',
                content: '登录失败' + data.msg,
                showCancel: false
              });
            } else {
              console.log("登录成功");
              wx.setStorageSync('token', data.data.token);
              wx.navigateBack();
            }
          });
        }
      })
    }
  },
  registerUser: function() {
    let that = this;
    wx.login({
      success: function(res) {
        let code = res.code; // 微信登录接口返回的 code 参数，下面注册接口需要用到
        wx.getUserInfo({
          success: function(res) {
            console.info(res)
            let userInfo = res.userInfo;
            // 下面开始调用注册接口
            api.register(code, res.encryptedData, res.iv, function(data) {
              console.log("注册成功，开始登陆")
              wx.hideLoading();
              that.login();
            });
          }
        })
      }
    })
  }
})