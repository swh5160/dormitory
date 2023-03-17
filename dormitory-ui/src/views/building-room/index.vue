<template>
  <div class="app-container">
    <!-- 搜索栏   -->
    <el-form :model="search" ref="searchForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="房间号" prop="rno">
        <el-input v-model="search.rno" placeholder="请输入房间号" clearable size="mini" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="楼号" prop="bno">
        <el-input v-model="search.bno" placeholder="请输入楼号" clearable size="mini" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-delete" size="mini" @click="resetQuery">清空</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="rowSave">新增
        </el-button>
      </el-col>
    </el-row>

    <!-- 表单列表 -->
    <el-table v-loading="loading" :data="records" :height="tableHeight">
      <el-table-column width="55" align="center"/>
      <el-table-column type="expand">
        <template slot-scope="{row}">
          <el-form style="margin-left: 120px;" v-for="bed in row.roomBeds">
            <el-form-item :label="bed.name">
              <el-tag v-if="bed.studentId != null">已被占位</el-tag>
              <el-tag type="success" v-else>暂无占位</el-tag>
              <!--     学生占位       -->
              <el-button icon="el-icon-circle-plus-outline" v-if="bed.studentId ==null" circle
                         style="margin-left: 5px;" @click="placeholder(bed.id)"></el-button>
              <!--     查看占位信息         -->
              <el-button icon="el-icon-info" v-if="bed.studentId !=null" circle
                         style="margin-left: 5px;" @click="lookupPlaceHolder(bed.studentId)"></el-button>
              <!--    清空床位          -->
              <el-button icon="el-icon-delete-solid" v-if="bed.studentId !=null" circle
                         style="margin-left: 5px;" @click="removePlaceHolder(bed.id)"></el-button>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="楼号" align="center" prop="bno" :show-overflow-tooltip="true"/>
      <el-table-column label="宿舍号" align="center" prop="rno" :show-overflow-tooltip="true"/>
      <el-table-column label="楼层" align="center" prop="floor" width="50"/>
      <el-table-column label="最大入住人数" align="center" prop="maxOccupancy" width="150"/>
      <el-table-column label="当前入住人数" align="center" prop="currentOccupancy" width="150"/>
      <el-table-column label="创建时间" align="center" prop="gmtCreate" width="200"/>
      <el-table-column label="修改时间" align="center" prop="gmtModified" width="200"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--          <el-button size="mini" type="text" icon="el-icon-edit" @click="rowUpdate(scope.row)">编辑-->
          <!--          </el-button>-->
          <el-button size="mini" type="text" icon="el-icon-delete" @click="rowDel(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  分页插件  -->
    <pagination v-show="total>0" :total="total" :page.sync="search.current" :limit.sync="search.size"
                @pagination="getList"/>
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-form-item label="楼栋号" prop="buildingId">
            <el-select v-model="form.buildingId" placeholder="请选择">
              <el-option v-for="dict in buildingDic" :key="dict.value" :label="dict.label"
                         :value="+dict.value"/>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="楼层" prop="floor">
            <el-input-number v-model="form.floor" :min="1" :max="30" label="楼层"></el-input-number>
          </el-form-item>
        </el-row>

        <el-row>
          <el-form-item label="房间号" prop="rno">
            <el-input v-model="form.rno" placeholder="请输入宿舍号"/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="最大入住人数" prop="maxOccupancy">
            <el-input-number v-model="form.maxOccupancy" :min="1" :max="8" label="最大入住人数"></el-input-number>
          </el-form-item>
        </el-row>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { getPage, deleteById, save, placeholderBed, clearPlaceholderBed } from '@/api/building_room'
import { getAll } from '@/api/building'
import { getStudentById } from '@/api/student'

export default {
  data () {
    return {
      option: {
        submitText: '占位',
        span: 24,
        column: [
          {
            label: '学号',
            prop: 'sno',
            rules: [{
              required: true,
              message: '请输入学号',
              trigger: 'blur'
            }],
          }
        ]
      },
      tableHeight: window.innerHeight - 340,
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 列表数据
      records: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      search: {
        current: 1,
        size: 10,
        rno: undefined,
        bno: undefined
      },
      buildingDic: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        buildingId: [
          { required: true, message: '楼栋不能为空', trigger: 'blur' }
        ],
        rno: [
          { required: true, message: '房间号不能为空', trigger: 'blur' }
        ],
        floor: [
          { required: true, message: '楼层不能为空', trigger: 'blur' }
        ],
        maxOccupancy: [
          { required: true, message: '最大入住人数不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getList()
    this.getBuildings()
  },
  methods: {
    // 查看占位信息
    lookupPlaceHolder (studentId) {
      getStudentById(studentId).then(res => {
        let data = res.data
        let html = `<p>学号: ${data.sno}</p><p>学院: ${data.academy}</p><p>专业: ${data.major}</p><p>年级: ${data.grade}</p>`
        this.$alert(html, '学生信息', {
          dangerouslyUseHTMLString: true
        })
      })
    },
    // 移除占位
    removePlaceHolder (roomBedId) {
      this.$confirm('此操作将该学生移除该床位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await clearPlaceholderBed(roomBedId)
        this.msgNoticeSuccess('移除成功')
        this.getList()
      })
    },
    // 占位
    placeholder (roomBedId) {
      this.$DialogForm.show({
        title: '床位占位',
        width: '50%',
        option: this.option,
        callback: async (res) => {
          await placeholderBed({
            roomBedId: roomBedId,
            sno: res.data.sno
          })
          this.msgNoticeSuccess('占位成功')
          this.getList()
          res.done()
          res.close()
        }
      })
    },
    // 获取楼宇信息
    getBuildings () {
      getAll().then(res => {
        res.data.forEach(e => {
          this.buildingDic.push({
            label: `${e.name}-->${e.bno}`,
            value: e.id
          })
        })
      })
    },
    // 查询列表
    getList () {
      this.loading = true
      getPage(this.search).then(res => {
        this.records = res.data.records
        this.total = res.data.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel () {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset () {
      this.form = {
        id: undefined,
        buildingId: undefined,
        rno: undefined,
        floor: undefined,
        maxOccupancy: undefined,
      }
      this.resetForm('form')
    },
    // 搜索按钮
    handleQuery () {
      this.search.current = 1
      this.getList()
    },
    // 重置
    resetQuery () {
      this.resetForm('searchForm')
      this.handleQuery()
    },
    // 保存
    rowSave () {
      this.reset()
      this.open = true
      this.title = '添加房间信息'
    },
    // 修改
    // rowUpdate (row) {
    //   this.reset()
    //   const id = row.id || this.ids
    //   getByById(id).then(res => {
    //     this.form = res.data
    //     this.open = true
    //     this.title = '修改房间信息'
    //   })
    // },
    // 提交按钮
    submitForm: function () {
      this.$refs['form'].validate(async valid => {
        if (valid) {
          await save(this.form)
          if (this.form.id !== undefined) {
            this.msgNoticeSuccess('修改成功')
          } else {
            this.msgNoticeSuccess('新增成功')
          }
          this.open = false
          this.getList()
        }
      })
    },
    // 删除按钮
    rowDel (row) {
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
    }
  }
}
</script>

<style scoped>

</style>
