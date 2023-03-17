// 引入login.js
import { getInfo, login } from '@/api/login'

import { getToken, removeToken, setToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'
// md5加密密码
import md5 from 'js-md5'

const state = {
  token: getToken(),
  // 存储用户信息
  user: {},
  // 角色信息
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USER: (state, user) => {
    state.user = user
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // 用户登录
  login ({ commit }, userInfo) {
    const { username, password, type, code, uuid } = userInfo
    return new Promise((resolve, reject) => {
      login({
        username: username.trim(),
        password: md5(password),
        type: type,
        uuid: uuid,
        code: code
      }).then(response => {
        const { data } = response
        // 设置token
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取用户信息
  getInfo ({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response
        commit('SET_USER', data)
        // 将权限信息放入
        commit('SET_ROLES', [data.role])
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 用户退出
  logout ({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resetRouter()
      dispatch('tagsView/delAllViews', null, { root: true })
      resolve()
    })
  },

  // remove token
  resetToken ({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
