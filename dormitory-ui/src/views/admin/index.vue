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
               @row-save="rowSave"
               @row-update="rowUpdate"
               @row-del="rowDel"
    >
      <template slot="gender" slot-scope="{row}">
        <el-tag type="success" v-if="row.gender === 0">女</el-tag>
        <el-tag v-else>男</el-tag>
      </template>
    </avue-crud>


  </div>
</template>

<script>
import { deleteById, getPage, saveOrUpdate } from '@/api/admin'

export default {
  data () {
    return {
      page: {
        total: 0,
        layout: 'total, sizes, prev, pager, next, jumper',
      },
      records: [],
      search: {
        username: '',
        name: ''
      },
      option: {
        align: 'center',
        searchMenuSpan: 8,
        index: true,
        indexLabel: '序号',
        column: [
          {
            label: '用户名',
            prop: 'username',
            search: true,
            rules: [
              { required: true, message: '请输入用户名', trigger: 'blur' },
              { min: 5, max: 6, message: '请输入5个字符以上' },
            ]
          }
          , {
            label: '姓名',
            prop: 'name',
            search: true,
            rules: [
              { required: true, message: '请输入姓名', trigger: 'blur' },
            ]
          }
          , {
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
          }
          , {
            label: '年龄',
            prop: 'age',
            rules: [
              { required: true, message: '请输入年龄', trigger: 'blur' },
            ],
          }
          , {
            label: '手机号',
            prop: 'phone'
          }
          , {
            label: '邮箱',
            prop: 'email',
            width: 200
          }
          , {
            label: '创建时间',
            prop: 'gmtCreate',
            display: false,
            width: 200
          }
          , {
            label: '修改时间',
            prop: 'gmtModified',
            display: false,
            width: 200
          }
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
      getPage(this.search).then(res => {
        this.records = res.data.records
        this.page.total = res.data.total
      })
    },
    // 保存
    rowSave (form, done, loading) {
      // saveOrUpdate()
      saveOrUpdate(form).then(res => {
        this.msgNoticeSuccess('新增成功')
        this.getList()
      })
      done()
    },
    // 修改
    rowUpdate (form, index, done, loading) {
      saveOrUpdate(form).then(res => {
        this.msgNoticeSuccess('修改成功')
        this.getList()
      })
      done()
    },
    // 删除
    rowDel (form, index) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById(form.id).then(res => {
          this.msgNoticeSuccess('删除成功')
          this.getList()
        })

      }).catch(() => {
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
