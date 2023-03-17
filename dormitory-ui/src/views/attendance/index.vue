<template>
  <div class="app-container">
    <avue-crud :data="records"
               :option="option"
               :page.sync="page"
               :search.sync="search"
               @size-change="sizeChange"
               @current-change="currentChange"
               @search-change="searchChange"
               @search-reset="resetChange"
    >
      <template slot="gender" slot-scope="{row}">
        <el-tag type="success" v-if="row.gender === 0">女</el-tag>
        <el-tag v-else>男</el-tag>
      </template>
      <template slot="grade" slot-scope="{row}">
        <el-tag type="success" v-if="row.grade === 1">大一</el-tag>
        <el-tag type="success" v-if="row.grade === 2">大二</el-tag>
        <el-tag type="success" v-if="row.grade === 3">大三</el-tag>
        <el-tag v-if="row.grade === 4">大四</el-tag>
      </template>

      <template slot="clock" slot-scope="{row}">
        <el-tag type="success" v-if="row.clock">已打卡</el-tag>
        <el-tag type="primary" v-else>未打卡</el-tag>
      </template>
    </avue-crud>


  </div>
</template>

<script>
import { getAttendancePage } from '@/api/attendance'

export default {
  data () {
    return {
      page: {
        total: 0,
        layout: 'total, sizes, prev, pager, next, jumper',
      },
      records: [],
      search: {
        clock: undefined,
        sno: undefined,
        name: undefined
      },
      option: {
        menu: false,
        addBtn: false,
        align: 'center',
        searchMenuSpan: 6,
        index: true,
        indexLabel: '序号',
        column: [
          {
            type: 'select',
            label: '是否打卡',
            search: true,
            prop: 'clock',
            dicData: [
              {
                label: '未打卡',
                value: false
              }, {
                label: '已打卡',
                value: true
              }
            ]
          },
          {
            label: '学号',
            search: true,
            prop: 'sno',
            rules: [
              { required: true, message: '请输入学号', trigger: 'blur' },
              { min: 9, max: 9, message: '请输入9个字符' },
            ]
          },
          {
            label: '学院',
            prop: 'academy',
            rules: [
              { required: true, message: '请输入学院', trigger: 'blur' },
            ]
          },
          {
            label: '专业',
            prop: 'major',
            rules: [
              { required: true, message: '请输入专业', trigger: 'blur' },
            ]
          },
          {
            type: 'select',
            label: '年级',
            prop: 'grade',
            rules: [
              { required: true, message: '请选择年级', trigger: 'blur' },
            ],
            dicData: [
              {
                label: '大一',
                value: 1
              }, {
                label: '大二',
                value: 2
              }, {
                label: '大三',
                value: 3
              }
              , {
                label: '大四',
                value: 4
              }
            ]
          },
          {
            label: '所在班级',
            prop: 'inClass',
            rules: [
              { required: true, message: '请输入所在班级', trigger: 'blur' },
            ]
          },
          {
            label: '姓名',
            prop: 'name',
            search: true,
            rules: [
              { required: true, message: '请输入姓名', trigger: 'blur' },
            ]
          },
          {
            type: 'select',
            label: '性别',
            prop: 'gender',
            rules: [
              { required: true, message: '请选择性别', trigger: 'blur' },
            ],
            dicData: [
              {
                label: '男',
                value: 1
              }, {
                label: '女',
                value: 0
              }, {
                label: '无',
                value: 2
              }]
          },
          {
            label: '年龄',
            prop: 'age',
            rules: [
              { required: true, message: '请输入年龄', trigger: 'blur' },
            ],
          },
          // , {
          //   label: '头像',
          //   prop: 'avatar'
          // }
        ]
      },
    }
  },
  created () {
    this.getList()
  },
  methods: {
    // 获取列表
    getList () {
      // 搜索条件
      this.search.current = this.page.currentPage
      this.search.size = this.page.pageSize
      getAttendancePage(this.search).then(res => {
        this.records = res.data.records
        this.page.total = res.data.total
      })
    },
    // 清空按钮
    resetChange (item) {
      this.search.username = ''
      this.getList()
    },
    // 查询条件
    searchChange (params, done) {
      this.search.username = params.username
      this.getList()
      done()
    },
    // 监听pageSize变化
    sizeChange (val) {
      this.page.currentPage = 1
      this.page.pageSize = val
      this.getList()
    },
    // 监听当前页变化
    currentChange (val) {
      this.page.currentPage = val
      this.getList()
    }
  }
}
</script>

<style scoped>

</style>
