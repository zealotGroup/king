import store from '@/store'
import { parseTime } from '@/utils'
import Vue from 'vue'
const v = new Vue()

export function copy(old) {
  return JSON.parse(JSON.stringify(old))
}

export function flushList(list) {
  list.push({ id: -1 })
  list.pop()
}

export function notifyClicking(loading, callBack) {
  if (loading) {
    v.$notify({
      title: '警告',
      message: '重复操作了',
      type: 'warning',
      duration: 1000
    })
  } else {
    callBack()
  }
}

export function cacheGet(list, row, action) {
  if (action === 'add') {
    list.unshift(row)
  } else {
    for (const v of list) {
      if (v.id === row.id) {
        const index = list.indexOf(v)
        if (action === 'replace') {
          list.splice(index, 1, row)
        } else if (action === 'remove') {
          list.splice(index, 1)
        }
        break
      }
    }
  }
  return row
}

export function formatJson(filterVal, jsonData) {
  return jsonData.map(v => filterVal.map(j => {
    if (j === 'timestamp') {
      return parseTime(v[j])
    } else {
      return v[j]
    }
  }))
}

export function checkLevel(level) {
  return store.getters.level === level
}

export function getPictureUrl(id) {
  return 'http://localhost:9528/api/admin/picture/getPicture?id=' + id + '&date=' + new Date()
}
