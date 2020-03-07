//index.js
//获取应用实例
var app = getApp();
var api = require('../../utils/api.js');
Page({
  data: {
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    loadingHidden: false, // loading
    userInfo: {},
    swiperCurrent: 0,
    selectCurrent: 0,
    categories: [],
    activeCategoryId: 0,
    goods: [],
    scrollTop: 0,
    loadingMoreHidden: true,

    hasNoCoupons: true,
    coupons: [],
    searchInput: '',
  },

  tabClick: function(e) {
    this.setData({
      activeCategoryId: e.currentTarget.id
    });
    this.getGoodsList(this.data.activeCategoryId);
  },
  //事件处理函数
  swiperchange: function(e) {
    //console.log(e.detail.current)
    this.setData({
      swiperCurrent: e.detail.current
    })
  },
  toDetailsTap: function(e) {
    wx.navigateTo({
      url: "/pages/goods-details/index?id=" + e.currentTarget.dataset.id
    })
  },
  tapBanner: function(e) {
    if (e.currentTarget.dataset.id != 0) {
      wx.navigateTo({
        url: "/pages/goods-details/index?id=" + e.currentTarget.dataset.id
      })
    }
  },
  bindTypeTap: function(e) {
    this.setData({
      selectCurrent: e.index
    })
  },
  onLoad: function() {
    var that = this
    wx.setNavigationBarTitle({
      title: wx.getStorageSync('mallName')
    })
    api.get_banner_list("hot", function(data) {
      if (data.code == 404) {
        console.log("无资源，请在后台添加 banner 轮播图片");
      } else {
        that.setData({
          banners: data.data
        });
      }
    });

    api.get_goods_category_all(function(data) {
      var categories = [{
        id: 0,
        name: "全部"
      }];
      if (data.code == 0) {
        for (var i = 0; i < data.data.length; i++) {
          categories.push(data.data[i]);
        }
      }
      that.setData({
        categories: categories,
        activeCategoryId: 0
      });
      that.getGoodsList(0);
    });
    that.getCoupons();
    that.getNotice();
  },
  onPageScroll(e) {
    let scrollTop = this.data.scrollTop
    this.setData({
      scrollTop: e.scrollTop
    })
  },
  getGoodsList: function(categoryId) {
    if (categoryId == 0) {
      categoryId = "";
    }
    console.log(categoryId)
    var that = this;
    api.get_goods_list(categoryId, that.data.searchInput, function(data) {
      that.setData({
        goods: [],
        loadingMoreHidden: true
      });
      var goods = [];
      if (data.code != 0 || data.data.length == 0) {
        that.setData({
          loadingMoreHidden: false,
        });
        return;
      }
      for (var i = 0; i < data.data.length; i++) {
        goods.push(data.data[i]);
      }
      that.setData({
        goods: goods,
      });
    });
  },
  getCoupons: function() {
    var that = this;
    api.get_discounts_coupons("", function(data) {
      if (data.code == 0) {
        that.setData({
          hasNoCoupons: false,
          coupons: data.data
        });
      }
    });
  },
  gitCoupon: function(e) {
    let that = this;
    let id = e.currentTarget.dataset.id;
    let token = wx.getStorageSync('token');
    api.get_discounts_fetch(id, token, function(data) {
      if (data.code == 20001 || data.code == 20002) {
        wx.showModal({
          title: '错误',
          content: '来晚了',
          showCancel: false
        })
        return;
      }
      if (data.code == 20003) {
        wx.showModal({
          title: '错误',
          content: '你领过了，别贪心哦~',
          showCancel: false
        })
        return;
      }
      if (data.code == 30001) {
        wx.showModal({
          title: '错误',
          content: '您的积分不足',
          showCancel: false
        })
        return;
      }
      if (data.code == 20004) {
        wx.showModal({
          title: '错误',
          content: '已过期~',
          showCancel: false
        })
        return;
      }
      if (data.code == 0) {
        wx.showToast({
          title: '领取成功，赶紧去下单吧~',
          icon: 'success',
          duration: 2000
        })
      } else {
        wx.showModal({
          title: '错误',
          content: data.msg,
          showCancel: false
        })
      }
    });
  },
  onShareAppMessage: function() {
    return {
      title: app.globalData.appName + '——' + app.globalData.shareProfile,
      path: '/pages/index/index',
      success: function(res) {
        // 转发成功
      },
      fail: function(res) {
        // 转发失败
      }
    }
  },
  getNotice: function() {
    let that = this;
    api.get_notice_list(5, function(data) {
      if (data.code == 0) {
        that.setData({
          noticeList: data.data
        });
      }
    });
  },
  listenerSearchInput: function(e) {
    this.setData({
      searchInput: e.detail.value
    })

  },
  toSearch: function() {
    this.getGoodsList(this.data.activeCategoryId);
  }
})