// utils/api.js
var subDomain = "zealot";
var baseUrl = "http://localhost:8080/api";
var wxurl = "/wx";
// baseUrl = 'https://api.it120.cc/' + subDomain;


module.exports = {
  get_config_value: get_config_value,
  get_order_reputation_score: get_order_reputation_score,
  get_kanjiaList: get_kanjiaList,
  get_banner_list: get_banner_list,
  get_discounts_coupons: get_discounts_coupons,
  get_discounts_fetch: get_discounts_fetch,
  get_goods_list: get_goods_list,
  get_goods_category_all: get_goods_category_all,
  get_shop_goods_detail: get_shop_goods_detail,
  get_shop_goods_price: get_shop_goods_price,
  get_shop_goods_reputation: get_shop_goods_reputation,
  get_media_video_detail: get_media_video_detail,
  get_shop_goods_kanjia_join: get_shop_goods_kanjia_join,
  get_notice_list: get_notice_list,
  get_user_shipping_address: get_user_shipping_address,
  get_user_shipping_address_detail: get_user_shipping_address_detail,
  get_user_shipping_address_delete: get_user_shipping_address_delete,

  check_token: check_token,
  login: login,
  register: register
};

/**
 * 获取商城名称
 **/
function get_config_value(key, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/config/get-value',
    data: {
      key: key
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}
/**
 * 获取赠送积分规则 
 **/
function get_order_reputation_score(code, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/score/send/rule',
    data: {
      code: code
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}


/**
 * 获取砍价设置
 **/
function get_kanjiaList(callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/kanjia/list',
    data: {},
    success: function(res) {
      callBack(res.data);
    }
  })
}

/**
 * 获取轮番图
 */
function get_banner_list(key, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/banner/list',
    data: {
      type: key
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

/**
 * 获取所有商品
 */
function get_goods_category_all(callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/category/all',
    success: function(res) {
      callBack(res.data);
    }
  })
}

/**
 * 获取商品列表
 */
function get_goods_list(categoryId, nameLike, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/list',
    data: {
      categoryId: categoryId,
      nameLike: nameLike
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

function get_discounts_coupons(type, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/discounts/coupons',
    data: {
      type: type
    },
    success: function(res) {
      callBack(res.data);

    }
  })
}

function get_discounts_fetch(id, token, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/discounts/fetch',
    data: {
      id: id,
      token: token
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

function get_notice_list(pageSize, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/notice/list',
    data: {
      pageSize: pageSize
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

function get_user_shipping_address(type, token, id, provinceId, cityId, districtId, linkMan, address, mobile, code, isDefault, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/user/shipping-address/' + type,
    data: {
      token: token,
      id: id,
      provinceId: provinceId,
      cityId: cityId,
      districtId: districtId,
      linkMan: linkMan,
      address: address,
      mobile: mobile,
      code: code,
      isDefault: isDefault
    },
    success: function(res) {
      callBack(res.data)
    }
  })
}

function get_user_shipping_address_detail(token, id, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/user/shipping-address/detail',
    data: {
      token: token,
      id: id
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

function get_user_shipping_address_delete(token, id, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/user/shipping-address/delete',
    data: {
      token: token,
      id: id
    },
    success: (res) => {
      callBack(res.data);
    }
  })
}

function get_shop_goods_detail(id, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/detail',
    data: {
      id: id
    },
    success: function(res) {
      callBack(res.data);
    }
  });
}

function get_shop_goods_price(goodsId, propertyChildIds, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/price',
    data: {
      goodsId: goodsId,
      propertyChildIds: propertyChildIds
    },
    success: function(res) {
      callBack(res.data);
    }
  });
}

function get_shop_goods_reputation(goodsId, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/reputation',
    data: {
      goodsId: goodsId
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

function get_media_video_detail(videoId, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/media/video/detail',
    data: {
      videoId: videoId
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

function get_shop_goods_kanjia_join(kjid, token, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/kanjia/join',
    data: {
      kjid: kjid,
      token: token
    },
    success: function(res) {
      callBack(res.data);
    }
  })
}

/**
 * 判断后台是否登录
 **/
function check_token(callBack) {
  wx.request({
    url: baseUrl + '/checkToken',
    header: {
      'auth-token': wx.getStorageInfoSync("token")
    },
    success: function(res) {
      console.log("check_token返回【" + JSON.stringify(res.data) + "】");
      if (res.data.code != 200) {
        wx.showModal({
          title: '失败',
          content: '后台处理失败：' + res.data.msg,
          showCancel: false
        })
      } else {
        callBack(res.data.data);
      }
    },
    fail: function(res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  })
}

/**
 * 后台登录
 **/
function login(code, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/login',
    method: "POST",
    header: {
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      code: code
    },
    success: function(res) {
      console.log("login返回【" + JSON.stringify(res.data) + "】");
      callBack(res.data);
    },
    fail: function(res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  })
}

/**
 * 后台注册
 **/
function register(code, encryptedData, iv, callBack) {
  // 下面开始调用注册接口
  wx.request({
    url: baseUrl + wxurl + '/register',
    method: "POST",
    header: {
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      code: code,
      encryptedData: encryptedData,
      iv: iv
    }, // 设置请求的 参数
    success: (res) => {
      console.log("register返回：【" + JSON.stringify(res.data) + "】");
      callBack(res.data);
    },
    fail: function(res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  })
}