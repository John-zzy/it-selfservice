import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(data) {
  return request({
    url: '/user/info',
    method: 'get',
    data
  })
}

export function logout(token) {
  return request({
    url: '/user/logout',
    method: 'post',
    params: { access_token: token }
  })
}

export function userlists(data) {
  return request({
    url: '/users/list',
    method: 'post',
    data: data
  })
}

export function updateStatus(id, status) {
  return request({
    url: '/users/' + id + '/state/' + status,
    method: 'put'
  })
}

export function createUser(data) {
  return request({
    url: '/users/create',
    method: 'post',
    data: data
  })
}

export function getById(id) {
  return request({
    url: '/users/' + id,
    method: 'get'
  })
}

export function updateById(id, data) {
  return request({
    url: '/users/' + id + "/update",
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: '/users/' + id + "/delete",
    method: 'delete'
  })
}
