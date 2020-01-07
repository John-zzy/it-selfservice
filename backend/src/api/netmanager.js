import request from '@/utils/request'

export function getSwiListFun() {
  return request({
    url: '/netmanager/switree',
    method: 'get'
  })
}

export function getSwiLists() {
  return request({
    url: '/netmanager/swilist',
    method: 'get'
  })
}

export function getLists() {
  return request({
    url: '/netmanager/lists',
    method: 'get'
  })
}

export function getIpPageList(data) {
  return request({
    url: '/netmanager/list',
    method: 'post',
    data: data
  })
}

export function getIpListBySwiAddress(swiAddress) {
  return request({
    url: '/netmanager/list/' + swiAddress,
    method: 'get'
  })
}

export function getDetailById(id) {
  return request({
    url: '/netmanager/list/' + id + '/detail',
    method: 'get'
  })
}

export function updateDetail(id, data) {
  return request({
    url: '/netmanager/list/' + id + '/update',
    method: 'put',
    data: data
  })
}

export function updatePortStatus(id, status) {
  return request({
    url: '/netmanager/list/' + id + '/update/' + status,
    method: 'put'
  })
}

export function deletePort(id) {
  return request({
    url: '/netmanager/list/' + id + '/delete',
    method: 'put'
  })
}

export function createSwitch(data) {
  return request({
    url: '/netmanager/create',
    method: 'post',
    data: data
  })
}

export function getSwitchDetail(id) {
  return request({
    url: '/netmanager/switch/' + id,
    method: 'get'
  })
}

export function updateSwitchDetail(id, data) {
  return request({
    url: '/netmanager/switch/' + id + '/update',
    method: 'put',
    data: data
  })
}

export function deleteSwitch(id) {
  return request({
    url: '/netmanager/switch/' + id + '/delete',
    method: 'delete'
  })
}
