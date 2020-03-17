var api = require('./api.js');
var app = getApp();
module.exports = {
    check_timeout: check_timeout,
    getPictureUrl: getPictureUrl
}
function check_timeout() {
  var that = this
  if (app.globalData.token) {
    api.check_token(function (data) {
      if (data.data.timeout) {
        that.switchTab('/pages/start/start')
      }
    })
  } else {
    that.switchTab('/pages/start/start')
  }
}

function switchTab (url) {
  wx.navigateTo({
    url: url
  })
}

function getPictureUrl(id) {
    return app.globalData.base_url + '/admin/picture/getPicture?id=' + id + '&date=' + new Date()
}