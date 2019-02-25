import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  if (!Cookies.get(TokenKey)) {
    return
  } else {
    return JSON.parse(Cookies.get(TokenKey))
  }
}

export function setToken(token) {
  return Cookies.set(TokenKey, JSON.stringify(token))
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
