//login.js
//获取应用实例
var app = getApp();
var api = require('../../utils/api.js');
Page({
  data: {
    remind: '加载中',
    angle: 0,
    userInfo: {}
  },
  goToIndex: function() {
    api.check_token(wx.getStorageInfoSync("token"), function(data) {
      if (data.code != 200) {
        
      }
    })
    wx.switchTab({
      url: '/pages/index/index',
    });
  },
  onLoad: function() {
    let that = this;
    // 获取商城名称
    api.get_config_value("mallName", function(data) {
      if (data.code == 0) {
        wx.setStorageSync('mallName', data.data.value);
        wx.setNavigationBarTitle({
          title: data.data.value
        })
      }
    });

    api.get_config_value("recharge_amount_min", function(data) {
      if (data.code == 0) {
        app.globalData.recharge_amount_min = data.data.value;
      }
    });
    //获取赠送积分规则 
    api.get_order_reputation_score("123", function(data) {
      if (data.code == 0) {
        app.globalData.order_reputation_score = data.data[0].score;
      }
    });
    // 获取砍价设置
    api.get_kanjiaList(function(data) {
      if (data.code == 0) {
        app.globalData.kanjiaList = data.data.result;
      }
    });
  },
  onShow: function() {
    let that = this;
    let userInfo = wx.getStorageSync('userInfo')
    if (!userInfo) {
      wx.navigateTo({
        url: "/pages/authorize/index"
      })
    } else {
      that.setData({
        userInfo: userInfo
      })
    }
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