var commonCityData = require('../../utils/city.js')
var api = require('../../utils/api.js');
//获取应用实例
var app = getApp()
Page({
  data: {
    provinces: [],
    citys: [],
    districts: [],
    selProvince: '请选择',
    selCity: '请选择',
    selDistrict: '请选择',
    selProvinceIndex: 0,
    selCityIndex: 0,
    selDistrictIndex: 0
  },
  bindCancel: function() {
    wx.navigateBack({})
  },
  bindSave: function(e) {
    let that = this;
    let linkMan = e.detail.value.linkMan;
    let address = e.detail.value.address;
    let mobile = e.detail.value.mobile;
    let code = e.detail.value.code;

    if (linkMan == "") {
      wx.showModal({
        title: '提示',
        content: '请填写联系人姓名',
        showCancel: false
      })
      return
    }
    if (mobile == "") {
      wx.showModal({
        title: '提示',
        content: '请填写手机号码',
        showCancel: false
      })
      return
    }
    if (this.data.selProvince == "请选择") {
      wx.showModal({
        title: '提示',
        content: '请选择地区',
        showCancel: false
      })
      return
    }
    if (this.data.selCity == "请选择") {
      wx.showModal({
        title: '提示',
        content: '请选择地区',
        showCancel: false
      })
      return
    }
    let cityId = commonCityData.cityData[this.data.selProvinceIndex].cityList[this.data.selCityIndex].id;
    let districtId;
    if (this.data.selDistrict == "请选择" || !this.data.selDistrict) {
      districtId = '';
    } else {
      districtId = commonCityData.cityData[this.data.selProvinceIndex].cityList[this.data.selCityIndex].districtList[this.data.selDistrictIndex].id;
    }
    if (address == "") {
      wx.showModal({
        title: '提示',
        content: '请填写详细地址',
        showCancel: false
      })
      return
    }
    if (code == "") {
      wx.showModal({
        title: '提示',
        content: '请填写邮编',
        showCancel: false
      })
      return
    }
    let apiAddoRuPDATE = "add";
    let apiAddid = that.data.id;
    if (apiAddid) {
      apiAddoRuPDATE = "update";
    } else {
      apiAddid = 0;
    }

    api.get_user_shipping_address(apiAddoRuPDATE, wx.getStorageSync('token'), apiAddid, commonCityData.cityData[this.data.selProvinceIndex].id, cityId, districtId, linkMan, address, mobile, code, 'true', function(data) {
      if (data.code != 0) {
        // 登录错误 
        wx.hideLoading();
        wx.showModal({
          title: '失败',
          content: data.msg,
          showCancel: false
        })
        return;
      }
      // 跳转到结算页面
      wx.navigateBack({})
    });
  },
  initCityData: function(level, obj) {
    if (level == 1) {
      let pinkArray = [];
      for (let i = 0; i < commonCityData.cityData.length; i++) {
        pinkArray.push(commonCityData.cityData[i].name);
      }
      this.setData({
        provinces: pinkArray
      });
    } else if (level == 2) {
      let pinkArray = [];
      let dataArray = obj.cityList
      for (let i = 0; i < dataArray.length; i++) {
        pinkArray.push(dataArray[i].name);
      }
      this.setData({
        citys: pinkArray
      });
    } else if (level == 3) {
      let pinkArray = [];
      let dataArray = obj.districtList
      for (let i = 0; i < dataArray.length; i++) {
        pinkArray.push(dataArray[i].name);
      }
      this.setData({
        districts: pinkArray
      });
    }

  },
  bindPickerProvinceChange: function(event) {
    let selIterm = commonCityData.cityData[event.detail.value];
    this.setData({
      selProvince: selIterm.name,
      selProvinceIndex: event.detail.value,
      selCity: '请选择',
      selCityIndex: 0,
      selDistrict: '请选择',
      selDistrictIndex: 0
    })
    this.initCityData(2, selIterm)
  },
  bindPickerCityChange: function(event) {
    let selIterm = commonCityData.cityData[this.data.selProvinceIndex].cityList[event.detail.value];
    this.setData({
      selCity: selIterm.name,
      selCityIndex: event.detail.value,
      selDistrict: '请选择',
      selDistrictIndex: 0
    })
    this.initCityData(3, selIterm)
  },
  bindPickerChange: function(event) {
    let selIterm = commonCityData.cityData[this.data.selProvinceIndex].cityList[this.data.selCityIndex].districtList[event.detail.value];
    if (selIterm && selIterm.name && event.detail.value) {
      this.setData({
        selDistrict: selIterm.name,
        selDistrictIndex: event.detail.value
      })
    }
  },
  onLoad: function(e) {
    let that = this;
    this.initCityData(1);
    let id = e.id;
    if (id) {
      // 初始化原数据
      wx.showLoading();
      api.get_user_shipping_address_detail(wx.getStorageSync('token'), id, function(data) {
        wx.hideLoading();
        if (data.code == 0) {
          that.setData({
            id: id,
            addressData: data.data,
            selProvince: data.data.provinceStr,
            selCity: data.data.cityStr,
            selDistrict: data.data.areaStr
          });
          that.setDBSaveAddressId(data.data);
          return;
        } else {
          wx.showModal({
            title: '提示',
            content: '无法获取快递地址数据',
            showCancel: false
          })
        }
      });
    }
  },
  setDBSaveAddressId: function(data) {
    let retSelIdx = 0;
    for (let i = 0; i < commonCityData.cityData.length; i++) {
      if (data.provinceId == commonCityData.cityData[i].id) {
        this.data.selProvinceIndex = i;
        for (let j = 0; j < commonCityData.cityData[i].cityList.length; j++) {
          if (data.cityId == commonCityData.cityData[i].cityList[j].id) {
            this.data.selCityIndex = j;
            for (let k = 0; k < commonCityData.cityData[i].cityList[j].districtList.length; k++) {
              if (data.districtId == commonCityData.cityData[i].cityList[j].districtList[k].id) {
                this.data.selDistrictIndex = k;
              }
            }
          }
        }
      }
    }
  },
  selectCity: function() {

  },
  deleteAddress: function(e) {
    let that = this;
    let id = e.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '确定要删除该收货地址吗？',
      success: function(res) {
        if (res.confirm) {
          api.get_user_shipping_address_delete(wx.getStorageSync('token'), id, function(data) {
            wx.navigateBack({})
          });
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  readFromWx: function() {
    let that = this;
    wx.chooseAddress({
      success: function(res) {
        let provinceName = res.provinceName;
        let cityName = res.cityName;
        let diatrictName = res.countyName;
        let retSelIdx = 0;

        for (let i = 0; i < commonCityData.cityData.length; i++) {
          if (provinceName == commonCityData.cityData[i].name) {
            let eventJ = {
              detail: {
                value: i
              }
            };
            that.bindPickerProvinceChange(eventJ);
            that.data.selProvinceIndex = i;
            for (let j = 0; j < commonCityData.cityData[i].cityList.length; j++) {
              if (cityName == commonCityData.cityData[i].cityList[j].name) {
                //that.data.selCityIndex = j;
                eventJ = {
                  detail: {
                    value: j
                  }
                };
                that.bindPickerCityChange(eventJ);
                for (let k = 0; k < commonCityData.cityData[i].cityList[j].districtList.length; k++) {
                  if (diatrictName == commonCityData.cityData[i].cityList[j].districtList[k].name) {
                    //that.data.selDistrictIndex = k;
                    eventJ = {
                      detail: {
                        value: k
                      }
                    };
                    that.bindPickerChange(eventJ);
                  }
                }
              }
            }

          }
        }
        that.setData({
          wxaddress: res,
        });
      }
    })
  }
})