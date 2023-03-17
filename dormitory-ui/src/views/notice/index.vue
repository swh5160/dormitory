<template>
  <div class="app-container">
    <avue-crud :data="records"
               :option="option"
               :page.sync="page"
               :search.sync="search"
               v-model="form"
               @size-change="sizeChange"
               @current-change="currentChange"
               @search-change="searchChange"
               @search-reset="resetChange"
               @row-save="rowSave"
               @row-update="rowUpdate"
               @row-del="rowDel"
    >
      <template slot="contentForm" slot-scope="scope">
        <editor v-model="form.content" :min-height="200"/>
      </template>
    </avue-crud>


  </div>
</template>

<script>
import { deleteById, getPage, saveOrUpdate } from '@/api/notice'
import Editor from '@c/Editor'

export default {
  name: 'index',
  components: {
    Editor
  },
  data () {
    return {
      form: {},
      page: {
        total: 0,
        layout: 'total, sizes, prev, pager, next, jumper',
      },
      records: [],
      search: {
        title: '',
      },
      option: {
        align: 'center',
        searchMenuSpan: 8,
        index: true,
        indexLabel: '序号',
        column: [
          {
            label: '标题',
            prop: 'title',
            search: true,
            rules: [
              { required: true, message: '请输入标题', trigger: 'blur' },
            ]
          }
          , {
            label: '作者',
            prop: 'author',
            rules: [
              { required: true, message: '请输入作者', trigger: 'blur' },
            ]
          }
          , {
            label: '内容',
            formslot: true,
            prop: 'content',
            hide: true,
            rules: [
              { required: true, message: '请输入内容', trigger: 'blur' },
            ]
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
      content: undefined
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
