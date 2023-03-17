import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * 默认路由
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: '首页', icon: 'el-icon-s-data', affix: true }
      }
    ]
  },
]

/**
 * 管理员动态路由
 */
export const adminAsyncRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    name: 'User',
    meta: { title: '用户管理', icon: 'el-icon-s-claim' },
    children: [
      {
        path: 'admin',
        component: () => import('@/views/admin/index'),
        name: 'Admin',
        meta: { title: '管理员', icon: 'el-icon-s-custom' }
      },
      {
        path: 'student',
        component: () => import('@/views/student/index'),
        name: 'Student',
        meta: { title: '宿舍人员', icon: 'el-icon-s-check' }
      }
    ]
  },
  {
    path: '/dormitory',
    component: Layout,
    name: 'Dormitory',
    meta: { title: '宿舍管理', icon: 'el-icon-s-claim' },
    children: [
      {
        path: '/building',
        component: () => import('@/views/building/index'),
        name: 'Building',
        meta: { title: '楼宇信息', icon: 'el-icon-s-management' }
      },
      {
        path: '/building_room',
        component: () => import('@/views/building-room/index'),
        name: 'Building',
        meta: { title: '房间信息', icon: 'el-icon-s-management' }
      },
    ]
  },
  {
    path: '/notice',
    component: Layout,
    children: [
      {
        path: '/notice',
        component: () => import('@/views/notice/index'),
        name: 'Notice',
        meta: { title: '公告管理', icon: 'el-icon-s-management' }
      },
    ]
  },
  {
    path: '/problem',
    component: Layout,
    children: [
      {
        path: '/problem',
        component: () => import('@/views/problem/index'),
        name: 'Problem',
        meta: { title: '问题管理', icon: 'el-icon-s-management' }
      },
    ]
  },
  {
    path: '/attendance',
    component: Layout,
    children: [
      {
        path: '/attendance',
        component: () => import('@/views/attendance/index'),
        name: 'Problem',
        meta: { title: '考勤管理', icon: 'el-icon-s-management' }
      },
    ]
  },
]


// 学生动态路由
export const studentAsyncRoutes = [
  {
    path: '/student',
    component: Layout,
    children: [
      {
        path: 'student',
        component: () => import('@/views/my-room/index'),
        name: 'Student',
        meta: { title: '我的宿舍', icon: 'el-icon-s-check' }
      }
    ]
  },
  {
    path: '/my_problem',
    component: Layout,
    children: [
      {
        path: '/my_problem',
        component: () => import('@/views/my-problem/index'),
        name: 'MyProblem',
        meta: { title: '我的问题', icon: 'el-icon-s-management' }
      },
    ]
  },
  {
    path: '/problem',
    component: Layout,
    children: [
      {
        path: '/problem',
        component: () => import('@/views/problem/index'),
        name: 'Problem',
        meta: { title: '问题管理', icon: 'el-icon-s-management' }
      },
    ]
  },
  {
    path: '/profile',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/profile',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },
]


const createRouter = () => new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter () {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
