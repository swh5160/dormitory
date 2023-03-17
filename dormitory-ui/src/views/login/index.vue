<template>
  <div class="login-container">
    <el-card shadow="hover">
      <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        autocomplete="on"
        label-position="left"
      >

        <div class="title-container" style="height: 100px;">
          <img class="img" src="../../assets/images/logo_55.png"/>
          <h3 class="title">宿舍查寝系统</h3>
        </div>

        <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
          <el-input
            ref="username"
            v-model="loginForm.username"
            placeholder="Username"
            name="username"
            type="text"
            tabindex="1"
            autocomplete="on"
          >
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
          </el-input>
        </el-form-item>

        <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
          <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password"/>
          </span>
            <el-input
              :key="passwordType"
              ref="password"
              v-model="loginForm.password"
              :type="passwordType"
              placeholder="Password"
              name="password"
              tabindex="2"
              autocomplete="on"
              @keyup.native="checkCapslock"
              @blur="capsTooltip = false"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
            </el-input>
            <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
          </span>
          </el-form-item>
        </el-tooltip>
        <el-form-item prop="code">
          <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
                    @keyup.enter.native="handleLogin"/>
          <div style="width: 33%;float: right">
            <img :src="captcha" @click="getCaptcha" alt="暂无"/>
          </div>
        </el-form-item>

        <el-form-item>
          <el-row>
            <el-col>
              <el-radio v-model="loginForm.type" :label="0">管理员</el-radio>
              <el-radio v-model="loginForm.type" :label="1">学生</el-radio>

            </el-col>
          </el-row>
        </el-form-item>
        <el-button
          :loading="loading"
          type="primary"
          style="width:100%;margin-bottom:30px;"
          @click.native.prevent="handleLogin"
        >登录
        </el-button>
      </el-form>
    </el-card>

  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import { getCaptcha } from '@/api/login'

export default {
  name: 'Login',
  data () {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('密码长度需要在5位以上'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'admin',
        password: 'admin',
        type: 0,
        uuid: undefined,
        captcha: ''
      },
      // 验证码地址
      captcha: '',
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      showDialog: false,
      redirect: undefined,
      otherQuery: {}
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  mounted () {
    if (this.loginForm.username === '') {
      this.$refs.username.focus()
    } else if (this.loginForm.password === '') {
      this.$refs.password.focus()
    }
    this.getCaptcha()
  },
  methods: {
    // 获取验证码
    getCaptcha () {
      getCaptcha().then(res => {
        const { img, uuid } = res.data
        this.captcha = img
        this.loginForm.uuid = uuid
      })
    },
    checkCapslock (e) {
      const { key } = e
      this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
    },
    showPwd () {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin () {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
              this.loading = false
            })
            .catch(() => {
              // 获取验证码
              this.getCaptcha()
              this.loading = false
            })
        } else {
          return false
        }
      })
    },
    getOtherQuery (query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../../assets/images/login.jpeg");
  background-size: cover;
}
.img{
  width: 200px;
  padding-left: 0px;
}

.title {
  //margin: 0 auto 30px auto;
  padding-left: 268px;
  position: relative;
  top: -79px;
  //text-align: center;
  color: #014064;
  font-size: 1.7rem;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 500px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial, serif;
  font-size: 12px;
  letter-spacing: 1px;
}

.getCaptcha {
  display: block;
  width: 100%;
  height: 40px;
}

.forget-password {
  font-size: 14px;
  color: rgb(29, 142, 250);
}

.forget-password:hover {
  color: rgb(46, 195, 251);
}

.other-login {
  text-align: left;
  margin-top: 24px;
  line-height: 22px;

  .item-icon {
    font-size: 24px;
    color: rgba(0, 0, 0, 0.2);
    margin-left: 16px;
    vertical-align: middle;
    cursor: pointer;
    transition: color 0.3s;

    &:hover {
      color: #050a0e;
    }
  }
}
</style>
