//login.js
//获取应用实例
var api = require('../../utils/api.js');
var my = require('../../utils/my.js');
var app = getApp();
Page({
  data: {
    remind: '加载中',
    angle: 0,
    login_success: false,
    nickName: null
  },
  goToIndex: function() {
    var that = this
    if (that.login_success) {
      wx.switchTab({
        url: '/pages/index/index',
      });
    } else {
      wx.login({
        success: function(wxres) {
          console.info(wxres)
          api.login(wxres.code, function(res) {
            if (res.code == 201) {
              console.log("未注册，去授权注册");
              wx.redirectTo({
                url: '/pages/authorize/index',
              });
            } else if (res.code != 200) {
              console.log("登录失败");
              wx.hideLoading();
              wx.showModal({
                title: '提示',
                content: '登录失败' + res.msg,
                showCancel: false
              });
            } else {
              console.log("登录成功");
              let userInfo = res.data.user
              app.globalData.auth_session_id = res.data.auth_session_id
              app.globalData.nickName = userInfo.nickName
              app.globalData.phoneNumber = userInfo.phoneNumber
              app.globalData.avatarUrl = userInfo.avatarUrl
              wx.switchTab({
                url: '/pages/index/index',
              });
            }
          });
        }
      })
    }
  },
  onLoad: function() {
    var that = this
    wx.login({
      success: function(wxres) {
        console.info(wxres)
        api.login(wxres.code, function(res) {
          if (res.code == 201) {
            console.log("未注册，去授权注册");
            wx.redirectTo({
              url: '/pages/authorize/index',
            });
          } else if (res.code != 200) {
            console.log("登录失败");
          } else {
            console.log("登录成功");
            that.login_success = true
            let userInfo = res.data.user
            app.globalData.token = res.data.token
            app.globalData.nickName = userInfo.nickName
            app.globalData.phoneNumber = userInfo.phoneNumber
            app.globalData.avatarUrl = userInfo.avatarUrl

            that.setData({
              avatarUrl: app.globalData.avatarUrl,
              nickName: app.globalData.nickName
            })
          }
        });
      }
    })
  },
  onShow: function() {},
  onReady: function() {
    let that = this;
    setTimeout(function() {
      that.setData({
        remind: ''
      });
    }, 1000);
    wx.onAccelerometerChange(function(res) {
      let angle = -(res.x * 30).toFixed(1);
      if (angle > 14) {
        angle = 14;
      } else if (angle < -14) {
        angle = -14;
      }
      if (that.data.angle !== angle) {
        that.setData({
          angle: angle
        });
      }
    });
  }
});