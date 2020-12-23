//index.js
//获取应用实例
var app = getApp();
var WxParse = require('../../wxParse/wxParse.js');
var api = require('../../utils/api.js');
var my = require('../../utils/my.js');

Page({
  data: {
    autoplay: true,
    interval: 3000,
    duration: 1000,
    goodsDetail: {},
    swiperCurrent: 0,

    totalScoreToPay: 0,
    shopNum: 0,
    hideShopPopup: true,
    buyNumber: 0,
    buyNumMin: 1,
    buyNumMax: 0,

    propertyChildIds: "",
    propertyChildNames: "",
    canSubmit: false, //  选中规格尺寸时候是否允许加入购物车
    shopCarInfo: {},
    shopType: "addShopCar", //购物类型，加入购物车或立即购买，默认为加入购物车
  },

  //事件处理函数
  swiperchange: function (e) {
    this.setData({
      swiperCurrent: e.detail.current
    })
  },
  onLoad: function (e) {
    var that = this
    api.get_goods_detail(e.id, function (data) {
      var goodsDetail = data.data.vo
      let pics = []
      for (let item of goodsDetail.pics) {
        pics.push(my.getPictureUrl(item.id))
      }
      goodsDetail.pics = pics
      if (pics.length > 0) {
        goodsDetail.pic = pics[0]
      }
      that.setData({
        goodsDetail: goodsDetail,
        buyNumber: goodsDetail.size,
        buyNumMax: goodsDetail.goodsMaxSize,
        buyNumber: (goodsDetail.goodsMaxSize > 0) ? 1 : 0
      });
      WxParse.wxParse('article', 'html', goodsDetail.info, that, 5);
    });
  },
  goShopCar: function () {
    wx.reLaunch({
      url: "/pages/shop-cart/index"
    });
  },
  toAddShopCar: function () {
    this.setData({
      shopType: "addShopCar",
      hideShopPopup: false
    })

  },
  tobuy: function () {
    this.setData({
      shopType: "tobuy",
      hideShopPopup: false
    });
  },
  /**
   * 弹出框-隐藏
   */
  closePopupTap: function () {
    this.setData({
      hideShopPopup: true
    })
  },
  numJianTap: function () {
    if (this.data.buyNumber > this.data.buyNumMin) {
      let currentNum = this.data.buyNumber;
      currentNum--;
      this.setData({
        buyNumber: currentNum
      })
    }
  },
  numJiaTap: function () {
    if (this.data.buyNumber < this.data.buyNumMax) {
      let currentNum = this.data.buyNumber;
      currentNum++;
      this.setData({
        buyNumber: currentNum
      })
    }
  },

  /**
   * 加入购物车
   */
  addShopCar: function () {
    let that = this;
    if (that.data.goodsDetail.properties && !that.data.canSubmit) {
      if (!that.data.canSubmit) {
        wx.showModal({
          title: '提示',
          content: '请选择商品规格！',
          showCancel: false
        })
      }
      return;
    }
    if (that.data.buyNumber < 1) {
      wx.showModal({
        title: '提示',
        content: '购买数量不能为0！',
        showCancel: false
      })
      return;
    }
    api.add_goods_shopcar(that.data.goodsDetail.goodsId, that.data.buyNumber, function (data) {
      var goodsDetail = that.data.goodsDetail
      goodsDetail.shopcarGoodsSize = data.data.shopcarGoodsSize
      that.setData({
        goodsDetail: goodsDetail
      });
      that.closePopupTap();
      wx.showToast({
        title: '加入购物车成功',
        icon: 'success',
        duration: 2000
      })
    })
  },
  /**
   * 立即购买
   */
  buyNow: function () {
    if (this.data.goodsDetail.properties && !this.data.canSubmit) {
      if (!this.data.canSubmit) {
        wx.showModal({
          title: '提示',
          content: '请选择商品规格！',
          showCancel: false
        })
      }
      wx.showModal({
        title: '提示',
        content: '请先选择规格尺寸哦~',
        showCancel: false
      })
      return;
    }
    if (this.data.buyNumber < 1) {
      wx.showModal({
        title: '提示',
        content: '购买数量不能为0！',
        showCancel: false
      })
      return;
    }
    //组建立即购买信息
    this.closePopupTap();

    wx.navigateTo({
      url: "/pages/to-pay-order/index?orderType=buyNow"
    })
  }
})