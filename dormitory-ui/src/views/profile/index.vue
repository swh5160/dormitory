<template>
  <el-card class="app-container" style="margin: 20px 60px">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar/>
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user"/>
                学号
                <div class="pull-right">{{ user.sno }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone"/>
                手机号码
                <div class="pull-right">{{ user.phone }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email"/>
                用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user"/>
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd :user="user"/>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import userAvatar from './userAvatar'
import { getInfo } from '@/api/login'
import userInfo from './userInfo'
import resetPwd from './resetPwd'
// import BoxCard from '@/views/dashboard/admin/components/BoxCard'

export default {
  name: 'Profile',
  components: {
    userAvatar,
    userInfo,
    resetPwd
  },
  data () {
    return {
      user: {},
      activeTab: 'userinfo'
    }
  },
  created () {
    this.getStudentInfo()
  },
  methods: {
    async getStudentInfo () {
      const getInfoRes = await getInfo()
      this.user = { ...getInfoRes.data }
    }
  }
}
</script>
<style scoped>

</style>
