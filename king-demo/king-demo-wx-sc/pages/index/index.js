//index.js
//获取应用实例
var app = getApp();
var api = require('../../utils/api.js');
var my = require('../../utils/my.js');
Page({
  data: {
    loadingGoodsList: true,

    goodsLableList: [],
    goodsLableId: 0,
    searchInput: '',

    goodsList: [],
    total: 0,

    pageInfo: {
      page: 1,
      limit: 10
    },

    autoplay: true,
    interval: 3000,
    scrollTop: 0, // 搜索框置顶

    loadingMoreHidden: true,
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this
    if (that.data.goodsList.length == that.data.total) {
      that.setData({
        loadingMoreHidden: false
      });
      return
    } else {
      var page = that.data.pageInfo.page + 1
      that.setData({
        pageInfo: {
          page: page,
          limit: 10
        }
      })
      this.getGoodsList(this.data.goodsLableId, false);
    }
  },
  onLoad: function () {
    var that = this
    wx.setNavigationBarTitle({
      title: '鑫磊五金'
    })
    // 获取商品 标签（主要）
    api.get_goods_Lable_list(function (data) {
      var goodsLableList = [{
        id: 0,
        name: '全部'
      }]
      for (var item of data.data.list) {
        goodsLableList.push(item)
      }
      that.setData({
        goodsLableList: goodsLableList,
        goodsLableId: 0
      });
    })
    that.getGoodsList(0, true);
    that.getNotice();
  },
  onShow: function () {
    var that = this
  },
  onPageScroll(e) {
    let scrollTop = this.data.scrollTop
    this.setData({
      scrollTop: e.scrollTop
    })
  },
  onShareAppMessage: function () {
    return {
      title: app.globalData.appName + '——' + app.globalData.shareProfile,
      path: '/pages/index/index',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },
  tabClick: function (e) {
    this.setData({
      goodsLableId: e.currentTarget.id
    });
    this.toSearch();
  },
  toDetailsTap: function (e) {
    wx.navigateTo({
      url: "/pages/goods-details/index?id=" + e.currentTarget.dataset.id
    })
  },
  getGoodsList: function (goodsLableId, clickSearch) {
    if (goodsLableId == 0) {
      goodsLableId = "";
    }
    console.log(goodsLableId)
    var that = this;
    var goodsList = that.data.goodsList
    if (clickSearch) {
      goodsList = []
      that.setData({
        loadingGoodsList: true
      });
    }
    api.get_goods_list(goodsLableId, that.data.searchInput, that.data.pageInfo, function (data) {
      var list = data.data.list
      var total = data.data.total
      for (var item of list) {
        for (let pic of item.pictureList) {
          item.pic = my.getPictureUrl(pic.id)
          break
        }
        goodsList.push(item)
      }
      console.info(goodsList)
      that.setData({
        goodsList: goodsList,
        total: total,
        loadingMoreHidden: true,
        loadingGoodsList: false
      });
    });
  },
  getNotice: function () {
    let that = this;
    api.get_notice_list(5, function (data) {
      if (data.code == 0) {
        that.setData({
          noticeList: data.data
        });
      }
    });
  },
  listenerSearchInput: function (e) {
    this.setData({
      searchInput: e.detail.value
    })

  },
  toSearch: function () {
    this.setData({
      total: 0,
      pageInfo: {
        page: 1,
        limit: 10
      }
    })
    this.getGoodsList(this.data.goodsLableId, true);
  }
})