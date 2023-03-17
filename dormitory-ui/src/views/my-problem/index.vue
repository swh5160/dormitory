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
               ref="crud"
    >
      <template slot="contentForm" slot-scope="scope">
        <editor v-model="form.content" :min-height="200"/>
      </template>
      <template slot="type" slot-scope="{row}">
        <el-tag type="success" v-if="row.type === 0">宿舍问题</el-tag>
        <el-tag v-else>学生问题</el-tag>
      </template>
      <template slot="pass" slot-scope="{row}">
        <el-tag type="success" v-if="row.pass">通过</el-tag>
        <el-tag v-else>未通过</el-tag>
      </template>

      <!--  编辑按钮    -->
      <template slot="menu" slot-scope="{row,index}">
        <el-button type="text"
                   icon="el-icon-info" @click="lookup(row, index)">查看
        </el-button>
        <el-button type="text" icon="el-icon-edit" @click="handleUpdate(row, index)" v-if="!row.pass">编辑
        </el-button>
        <el-button type="text" icon="el-icon-delete" @click="handleDel(row,index)">删除
        </el-button>
      </template>
    </avue-crud>

    <el-dialog title="查看内容" :visible.sync="problemVisible" width="80%">
      <el-card v-html="form.content" :header="form.title">

      </el-card>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="problemVisible = false">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
import { audit, deleteById, getMyPage, saveOrUpdate } from '@/api/problem'
import Editor from '@c/Editor'
import { mapGetters } from 'vuex'

export default {
  name: 'index',
  components: {
    Editor
  },
  data () {
    return {
      problemVisible: false,
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
        editBtn: false,
        delBtn: false,
        align: 'center',
        searchMenuSpan: 4,
        index: true,
        indexLabel: '序号',
        column: [
          {
            label: '楼栋号',
            prop: 'bno',
            display: false,
            hide: true
          },
          {
            label: '房间号',
            prop: 'rno',
            display: false,
            hide: true
          },
          {
            label: '学生姓名',
            prop: 'studentName',
            display: false,
            hide: true
          },
          {
            label: '类型',
            prop: 'type',
            search: true,
            type: 'select',
            rules: [
              { required: true, message: '请选择类型', trigger: 'blur' },
            ],
            dicData: [
              {
                label: '宿舍问题',
                value: 0
              }, {
                label: '学生问题',
                value: 1
              }]
          },
          {
            label: '标题',
            prop: 'title',
            search: true,
            width: 200,
            rules: [
              { required: true, message: '请输入标题', trigger: 'blur' },
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
            label: '是否通过',
            prop: 'pass',
            search: true,
            display: false,
            type: 'select',
            dicData: [
              {
                label: '通过',
                value: 1
              }, {
                label: '未通过',
                value: 0
              }],
            width: 100
          },
          {
            label: '回复内容',
            prop: 'reply',
            display: false,
            width: 200
          },
          {
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
      auditOption: {
        submitText: '审核',
        span: 24,
        column: [
          {
            label: '是否通过',
            prop: 'pass',
            type: 'radio',
            dicData: [
              {
                label: '通过',
                value: true
              }, {
                label: '未通过',
                value: false
              }],
            rules: [{
              required: true,
              trigger: 'blur'
            }],
          },
          {
            label: '答复内容',
            prop: 'reply',
            type: 'textarea',
            rules: [{
              required: true,
              message: '请输入答复内容',
              trigger: 'blur'
            }],
          }
        ]
      },
      content: undefined
    }
  },
  computed: {
    ...mapGetters([
      'roles',
      'user'
    ])
  },
  created () {
    console.log(this.roles)
    this.getList()
  },
  methods: {
    // 查看
    lookup (row, index) {
      this.problemVisible = true
      this.form = row
    },
    // 获取列表
    getList () {
      // 搜索条件
      this.search.current = this.page.currentPage
      this.search.size = this.page.pageSize
      getMyPage(this.search).then(res => {
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
    handleUpdate (row, index) {
      this.$refs.crud.rowEdit(row, index)
    },
    // 修改
    rowUpdate (form, index, done, loading) {
      saveOrUpdate(form).then(res => {
        this.msgNoticeSuccess('修改成功')
        this.getList()
      })
      done()
    },
    handleDel (row, index) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById(row.id).then(res => {
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
