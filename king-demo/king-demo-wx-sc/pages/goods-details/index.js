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

    hasMoreSelect: false,
    selectSize: "选择：",
    selectSizePrice: 0,
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
      shopType: "addShopCar"
    })
    this.bindGuiGeTap();
  },
  tobuy: function () {
    this.setData({
      shopType: "tobuy"
    });
    this.bindGuiGeTap();
  },
  /**
   * 规格选择弹出框
   */
  bindGuiGeTap: function () {
    this.setData({
      hideShopPopup: false
    })
  },
  /**
   * 规格选择弹出框隐藏
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
   * 选择商品规格
   * @param {Object} e
   */
  labelItemTap: function (e) {
    let that = this;
    // 取消该分类下的子栏目所有的选中状态
    let childs = that.data.goodsDetail.properties[e.currentTarget.dataset.propertyindex].childsCurGoods;
    for (let i = 0; i < childs.length; i++) {
      that.data.goodsDetail.properties[e.currentTarget.dataset.propertyindex].childsCurGoods[i].active = false;
    }
    // 设置当前选中状态
    that.data.goodsDetail.properties[e.currentTarget.dataset.propertyindex].childsCurGoods[e.currentTarget.dataset.propertychildindex].active = true;
    // 获取所有的选中规格尺寸数据
    let needSelectNum = that.data.goodsDetail.properties.length;
    let curSelectNum = 0;
    let propertyChildIds = "";
    let propertyChildNames = "";
    for (let i = 0; i < that.data.goodsDetail.properties.length; i++) {
      childs = that.data.goodsDetail.properties[i].childsCurGoods;
      for (let j = 0; j < childs.length; j++) {
        if (childs[j].active) {
          curSelectNum++;
          propertyChildIds = propertyChildIds + that.data.goodsDetail.properties[i].id + ":" + childs[j].id + ",";
          propertyChildNames = propertyChildNames + that.data.goodsDetail.properties[i].name + ":" + childs[j].name + "  ";
        }
      }
    }
    let canSubmit = false;
    if (needSelectNum == curSelectNum) {
      canSubmit = true;
    }
    // 计算当前价格
    if (canSubmit) {
      api.get_shop_goods_price(that.data.goodsDetail.basicInfo.id, propertyChildIds, function (data) {
        that.setData({
          selectSizePrice: data.data.price,
          totalScoreToPay: data.data.score,
          propertyChildIds: propertyChildIds,
          propertyChildNames: propertyChildNames,
          buyNumMax: data.data.stores,
          buyNumber: (data.data.stores > 0) ? 1 : 0
        });
      });
    }
    this.setData({
      goodsDetail: that.data.goodsDetail,
      canSubmit: canSubmit
    })
  },
  /**
   * 加入购物车
   */
  addShopCar: function () {
    if (this.data.goodsDetail.properties && !this.data.canSubmit) {
      if (!this.data.canSubmit) {
        wx.showModal({
          title: '提示',
          content: '请选择商品规格！',
          showCancel: false
        })
      }
      this.bindGuiGeTap();
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
    //组建购物车
    var shopCarInfo = this.bulidShopCarInfo();

    this.setData({
      shopCarInfo: shopCarInfo,
      shopNum: shopCarInfo.shopNum
    });

    // 写入本地存储
    wx.setStorage({
      key: 'shopCarInfo',
      data: shopCarInfo
    })
    this.closePopupTap();
    wx.showToast({
      title: '加入购物车成功',
      icon: 'success',
      duration: 2000
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
      this.bindGuiGeTap();
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
    var buyNowInfo = this.buliduBuyNowInfo();
    // 写入本地存储
    wx.setStorage({
      key: "buyNowInfo",
      data: buyNowInfo
    })
    this.closePopupTap();

    wx.navigateTo({
      url: "/pages/to-pay-order/index?orderType=buyNow"
    })
  },
  /**
   * 组建购物车信息
   */
  bulidShopCarInfo: function () {
    // 加入购物车
    var shopCarMap = {};
    shopCarMap.goodsId = this.data.goodsDetail.basicInfo.id;
    shopCarMap.pic = this.data.goodsDetail.basicInfo.pic;
    shopCarMap.name = this.data.goodsDetail.basicInfo.name;
    // shopCarMap.label=this.data.goodsDetail.basicInfo.id; 规格尺寸 
    shopCarMap.propertyChildIds = this.data.propertyChildIds;
    shopCarMap.label = this.data.propertyChildNames;
    shopCarMap.price = this.data.selectSizePrice;
    shopCarMap.score = this.data.totalScoreToPay;
    shopCarMap.left = "";
    shopCarMap.active = true;
    shopCarMap.number = this.data.buyNumber;
    shopCarMap.logisticsType = this.data.goodsDetail.basicInfo.logisticsId;
    shopCarMap.logistics = this.data.goodsDetail.logistics;
    shopCarMap.weight = this.data.goodsDetail.basicInfo.weight;

    var shopCarInfo = this.data.shopCarInfo;
    if (!shopCarInfo.shopNum) {
      shopCarInfo.shopNum = 0;
    }
    if (!shopCarInfo.shopList) {
      shopCarInfo.shopList = [];
    }
    var hasSameGoodsIndex = -1;
    for (var i = 0; i < shopCarInfo.shopList.length; i++) {
      var tmpShopCarMap = shopCarInfo.shopList[i];
      if (tmpShopCarMap.goodsId == shopCarMap.goodsId && tmpShopCarMap.propertyChildIds == shopCarMap.propertyChildIds) {
        hasSameGoodsIndex = i;
        shopCarMap.number = shopCarMap.number + tmpShopCarMap.number;
        break;
      }
    }

    shopCarInfo.shopNum = shopCarInfo.shopNum + this.data.buyNumber;
    if (hasSameGoodsIndex > -1) {
      shopCarInfo.shopList.splice(hasSameGoodsIndex, 1, shopCarMap);
    } else {
      shopCarInfo.shopList.push(shopCarMap);
    }
    shopCarInfo.kjId = this.data.kjId;
    return shopCarInfo;
  },
  /**
   * 组建立即购买信息
   */
  buliduBuyNowInfo: function () {
    var shopCarMap = {};
    shopCarMap.goodsId = this.data.goodsDetail.basicInfo.id;
    shopCarMap.pic = this.data.goodsDetail.basicInfo.pic;
    shopCarMap.name = this.data.goodsDetail.basicInfo.name;
    // shopCarMap.label=this.data.goodsDetail.basicInfo.id; 规格尺寸 
    shopCarMap.propertyChildIds = this.data.propertyChildIds;
    shopCarMap.label = this.data.propertyChildNames;
    shopCarMap.price = this.data.selectSizePrice;
    shopCarMap.score = this.data.totalScoreToPay;
    shopCarMap.left = "";
    shopCarMap.active = true;
    shopCarMap.number = this.data.buyNumber;
    shopCarMap.logisticsType = this.data.goodsDetail.basicInfo.logisticsId;
    shopCarMap.logistics = this.data.goodsDetail.logistics;
    shopCarMap.weight = this.data.goodsDetail.basicInfo.weight;

    var buyNowInfo = {};
    if (!buyNowInfo.shopNum) {
      buyNowInfo.shopNum = 0;
    }
    if (!buyNowInfo.shopList) {
      buyNowInfo.shopList = [];
    }

    buyNowInfo.shopList.push(shopCarMap);
    buyNowInfo.kjId = this.data.kjId;
    return buyNowInfo;
  },
})