//app.js
var api = require('./utils/api.js');
App({
  onLaunch: function() {
    let that = this;
    // 判断是否登录
    let token = wx.getStorageSync('token');
    if (!token) {
      that.goLoginPageTimeOut();
    } else {
      api.check_token(token, function(data) {
        if (data.code != 0){
          wx.removeStorageSync('token');
          that.goLoginPageTimeOut();
        }
      });
    }
  },
  sendTempleMsg: function(orderId, trigger, template_id, form_id, page, postJsonString) {
    let that = this;
    wx.request({
      url: 'https://api.it120.cc/' + that.globalData.subDomain + '/template-msg/put',
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        token: wx.getStorageSync('token'),
        type: 0,
        module: 'order',
        business_id: orderId,
        trigger: trigger,
        template_id: template_id,
        form_id: form_id,
        url: page,
        postJsonString: postJsonString
      },
      success: (res) => {}
    })
  },
  sendTempleMsgImmediately: function(template_id, form_id, page, postJsonString) {
    let that = this;
    wx.request({
      url: 'https://api.it120.cc/' + that.globalData.subDomain + '/template-msg/put',
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        token: wx.getStorageSync('token'),
        type: 0,
        module: 'immediately',
        template_id: template_id,
        form_id: form_id,
        url: page,
        postJsonString: postJsonString
      },
      success: (res) => {
        console.log(res.data);
      }
    })
  },
  goLoginPageTimeOut: function() {
    wx.navigateTo({
      url: "/pages/authorize/index"
    })
  },
  globalData: {
    subDomain: "zealot",
    userInfo: null,
    version: "1.0.0",
    shareProfile: "百款精品商品，总有一款适合您", // 首页转发的时候话术
    order_reputation_score: null,
    recharge_amount_min: null,
    kanjiaList: null, 
  }
})