import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css'

import Element from 'element-ui'
import './styles/element-variables.scss'
import '@/assets/styles/index.scss' // 全局样式
import '@/assets/styles/flex.css' // 引入弹性盒子布局样式
import '@/assets/styles/custom.scss' // 自定义样式

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import Avue from '@smallwei/avue'
import '@smallwei/avue/lib/index.css'

import './icons' // icon
import './permission' // permission control
import './utils/error-log' // error log
import { resetForm, } from '@u/linc'
import * as filters from './filters'
import Pagination from '@c/Pagination'
// 绑定插件
Vue.prototype.resetForm = resetForm
Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'success' })
}
Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'error' })
}
Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg)
}
Vue.prototype.msgWarning = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'warning' })
}
Vue.prototype.msgNoticeSuccess = function (msg) {
  this.$notify({ showClose: true, message: msg, type: 'success', duration: 2000 })
}
Vue.prototype.msgNoticeError = function (msg) {
  this.$notify({ showClose: true, message: msg, type: 'error', duration: 2000 })
}
Vue.prototype.msgNoticeInfo = function (msg) {
  this.$notify({ showClose: true, message: msg, type: 'info', duration: 2000 })
}
Vue.prototype.msgNoticeWarning = function (msg) {
  this.$notify({ showClose: true, message: msg, type: 'warning', duration: 2000 })
}


Vue.use(Element, {
  size: Cookies.get('size') || 'mini'
})
Vue.use(Avue)
Vue.component('Pagination', Pagination)



Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})
Vue.config.productionTip = false
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
