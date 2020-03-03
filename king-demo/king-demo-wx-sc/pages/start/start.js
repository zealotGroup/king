//login.js
//获取应用实例
var app = getApp();
var my = require('../../utils/my.js');
Page({
  data: {
    remind: '加载中',
    angle: 0,
    userInfo: {}
  },
  goToIndex: function() {
    my.check_timeout_then_login()
  },
  onLoad: function() {
    let that = this;
  },
  onShow: function() {
    let that = this;
    that.setData({
      userInfo: wx.getStorageSync('userInfo')
    })
  },
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