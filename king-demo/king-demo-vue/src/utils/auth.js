import Cookies from 'js-cookie'

const TokenKey = 'TL-Token'
const expiresBase = 24 * 60 * 60 * 1000// 1天的耗秒数量

export function getToken() {
  const token = Cookies.get(TokenKey)
  if (!token) {
    return
  } else {
    return JSON.parse(token)
  }
}

export function setToken(data) {
  const timeout = data.timeout / expiresBase
  // expires 单位为天
  return Cookies.set(TokenKey, JSON.stringify(data), { expires: timeout })
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function frushToken() {
  const token = getToken()
  if (!token) {
    return
  } else {
    setToken(token)
  }
}
