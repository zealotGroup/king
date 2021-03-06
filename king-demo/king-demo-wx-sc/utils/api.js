// utils/api.js
var app = getApp()
var subDomain = "zealot";
var baseUrl = app.globalData.base_url;
var wxurl = "/wx";
// baseUrl = 'https://api.it120.cc/' + subDomain;


module.exports = {
  get_config_value: get_config_value,
  get_banner_list: get_banner_list,
  get_discounts_coupons: get_discounts_coupons,
  get_discounts_fetch: get_discounts_fetch,
  get_goods_category_all: get_goods_category_all,
  get_shop_goods_price: get_shop_goods_price,
  get_notice_list: get_notice_list,
  get_user_shipping_address: get_user_shipping_address,
  get_user_shipping_address_detail: get_user_shipping_address_detail,
  get_user_shipping_address_delete: get_user_shipping_address_delete,

  update_phone_number: update_phone_number,

  del_batch_goods_shopcar: del_batch_goods_shopcar,
  add_goods_shopcar: add_goods_shopcar,
  update_goods_shopcar: update_goods_shopcar,
  del_goods_shopcar: del_goods_shopcar,
  get_shopcar_list: get_shopcar_list,

  get_goods_detail: get_goods_detail,
  get_goods_Lable_list: get_goods_Lable_list,
  get_goods_list: get_goods_list,

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
    success: function (res) {
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
    success: function (res) {
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
    success: function (res) {
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
    success: function (res) {
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
    success: function (res) {
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
    success: function (res) {
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
    success: function (res) {
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
    success: function (res) {
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


function get_shop_goods_price(goodsId, propertyChildIds, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shop/goods/price',
    data: {
      goodsId: goodsId,
      propertyChildIds: propertyChildIds
    },
    success: function (res) {
      console.log("login返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    }
  });
}

// 批量删除购物车商品
function del_batch_goods_shopcar(goodsIds, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shopcar/delGoodsBatch',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      goodsIds: goodsIds
    },
    success: function (res) {
      console.log("del_goods_shopcar返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  });
}
// 删除购物车商品
function del_goods_shopcar(goodsId, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shopcar/delGoods',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      goodsId: goodsId
    },
    success: function (res) {
      console.log("del_goods_shopcar返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  });
}
// 修改购物车商品
function update_goods_shopcar(goodsId, size, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shopcar/updateGoods',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      goodsId: goodsId,
      size: size
    },
    success: function (res) {
      console.log("update_goods_shopcar返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  });
}

// 添加商品进购物车
function add_goods_shopcar(goodsId, size, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shopcar/addGoods',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      goodsId: goodsId,
      size: size
    },
    success: function (res) {
      console.log("add_goods_shopcar返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  });
}


// 获取购物车列表
function get_shopcar_list(callBack) {
  wx.request({
    url: baseUrl + wxurl + '/shopcar/list',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    success: function (res) {
      console.log("get_shopcar_list返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  });
}

// 获取商品详情
function get_goods_detail(goodsId, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/index/goods/detail',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      goodsId: goodsId
    },
    success: function (res) {
      console.log("get_goods_detail返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  });
}

/**
 * 获取商品标签列表
 */
function get_goods_Lable_list(callBack) {
  wx.request({
    url: baseUrl + wxurl + '/index/goodsLable/list',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    success: function (res) {
      console.log("get_goods_Lable_list返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  })
}

/**
 * 获取商品列表
 */
function get_goods_list(goodsLableId, searchLike, pageInfo, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/index/goods/list',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      goodsLableId: goodsLableId,
      searchLike: searchLike,
      page: pageInfo.page,
      limit: pageInfo.limit
    },
    success: function (res) {
      console.log("get_goods_list返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  })
}

/**
 * 更新手机号
 */
function update_phone_number(encryptedData, iv, callBack) {
  wx.request({
    url: baseUrl + wxurl + '/my/updatePhoneNumber',
    header: {
      'auth_session_id': app.globalData.token,
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      encryptedData: encryptedData,
      iv: iv
    },
    success: function (res) {
      console.log("update_phone_number返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  })
}
/**
 * token检测
 **/
function check_token() {
  wx.request({
    url: baseUrl + wxurl + '/login',
    method: "POST",
    header: {
      'auth_session_id': app.globalData.token
    },
    success: function (res) {
      console.log("check_token返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
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
    url: baseUrl + '/oauth/wx/login',
    method: "POST",
    header: {
      'content-type': 'application/x-www-form-urlencoded'
    },
    data: {
      code: code
    },
    success: function (res) {
      console.log("login返回【" + JSON.stringify(res.data) + "】");
      filter(res.data, callBack);
    },
    fail: function (res) {
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
    url: baseUrl + '/oauth/wx/register',
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
      filter(res.data, callBack);
    },
    fail: function (res) {
      wx.showModal({
        title: '错误',
        content: '访问错误',
        showCancel: false
      })
    }
  })
}

function filter(data, callBack) {
  if (data.code == 200) {
    callBack(data);
  } else if (data.code == 201) {
    console.warn("未注册，去授权注册");
    wx.redirectTo({
      url: '/pages/authorize/index',
    });
  } else if (data.code === 202) {
    console.warn("登录超时")
    wx.showModal({
      title: '超时',
      content: '登录超时',
      showCancel: false
    })
    wx.navigateTo({
      url: '/pages/start/start'
    })
  } else {
    wx.showModal({
      title: '失败',
      content: '请求失败 ' + data.msg,
      showCancel: false
    })
  }
}