// utils/api.js
var subDomain = "zealot";

module.exports = {
  get_config_value: get_config_value,
  get_order_reputation_score: get_order_reputation_score,
  get_kanjiaList: get_kanjiaList,
  get_banner_list: get_banner_list,
  get_goods_list: get_goods_list,
  get_discounts_coupons: get_discounts_coupons,
  get_discounts_fetch: get_discounts_fetch,
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
  request_login: request_login,
  request_register: request_register
};

/**
 * 获取商城名称
 **/
function get_config_value(key, callBack) {
  wx.request({
    url: 'https://api.it120.cc/' + subDomain + '/config/get-value',
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
    url: 'https://api.it120.cc/' + subDomain + '/score/send/rule',
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
    url: 'https://api.it120.cc/' + subDomain + '/shop/goods/kanjia/list',
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
    url: 'https://api.it120.cc/' + subDomain + '/banner/list',
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
    url: 'https://api.it120.cc/' + subDomain + '/shop/goods/category/all',
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
    url: 'https://api.it120.cc/' + subDomain + '/shop/goods/list',
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
    url: 'https://api.it120.cc/' + subDomain + '/discounts/coupons',
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
    url: 'https://api.it120.cc/' + subDomain + '/discounts/fetch',
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
    url: 'https://api.it120.cc/' + subDomain + '/notice/list',
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
    url: 'https://api.it120.cc/' + subDomain + '/user/shipping-address/' + type,
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
    url: 'https://api.it120.cc/' + subDomain + '/user/shipping-address/detail',
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
    url: 'https://api.it120.cc/' + subDomain + '/user/shipping-address/delete',
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
    url: 'https://api.it120.cc/' + subDomain + '/shop/goods/detail',
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
    url: 'https://api.it120.cc/' + subDomain + '/shop/goods/price',
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
    url: 'https://api.it120.cc/' + subDomain + '/shop/goods/reputation',
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
    url: 'https://api.it120.cc/' + subDomain + '/media/video/detail',
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
    url: 'https://api.it120.cc/' + subDomain + '/shop/goods/kanjia/join',
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
function check_token(token, callBack) {
  wx.request({
    url: 'https://api.it120.cc/' + subDomain + '/user/check-token',
    data: {
      token: token
    },
    success: function(res) {
      console.log("后台判断是否登录接口返回：【" + JSON.stringify(res.data) + "】"); //res.data.code == 0
      callBack(res.data);
    }
  })
}

/**
 * 后台登录
 **/
function request_login(code, callBack) {
  wx.request({
    url: 'https://api.it120.cc/' + subDomain + '/user/wxapp/login',
    data: {
      code: code
    },
    success: function(res) {
      console.log("request_login返回【" + JSON.stringify(res.data) + "】");
      callBack(res.data);
    }
  })
}

/**
 * 后台注册
 **/
function request_register(code, encryptedData, iv, callBack) {
  // 下面开始调用注册接口
  wx.request({
    url: 'https://api.it120.cc/' + subDomain + '/user/wxapp/register/complex',
    data: {
      code: code,
      encryptedData: encryptedData,
      iv: iv
    }, // 设置请求的 参数
    success: (res) => {
      console.log("request_register返回：【" + JSON.stringify(res.data) + "】");
      callBack(res.data);
    }
  })
}