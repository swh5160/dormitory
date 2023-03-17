<template>
  <div>
    <el-card style="margin: 15px; min-height: calc(100vh - 111px)">
      <div style="display: flex">
        <div style="margin-top: 55px">
          <div style="margin-left: 50px;margin-top: 20px">
            <!--      房间信息-->
            <el-descriptions :column="1" border style="width: 500px" title="房间信息" size="medium">
              <el-descriptions-item label="楼宇号">
                <span>{{ buildingRoom.bno }}</span>
              </el-descriptions-item>
              <el-descriptions-item label="房间号">
                <span>{{ buildingRoom.rno }}</span>
              </el-descriptions-item>
              <el-descriptions-item label="楼层">
                <span>{{ buildingRoom.floor }}</span>
              </el-descriptions-item>
              <el-descriptions-item label="可住人数">
                <span>{{ buildingRoom.maxOccupancy }}</span>
              </el-descriptions-item>
              <el-descriptions-item label="已住人数">
                <span class="rightSpan">{{ buildingRoom.currentOccupancy }}</span>
              </el-descriptions-item>
            </el-descriptions>
          </div>
          <!--      床位信息-->
          <div style="margin-left: 50px;margin-top: 40px">
            <el-descriptions :column="1" border style="width: 500px" title="床位信息">
              <el-descriptions-item :label="bed.name" v-for="bed in roomBeds">
                <el-tag size="success" v-if="bed.studentId == null">暂无占位</el-tag>
                <el-tag size="small" v-else>{{ bed.sno }}</el-tag>
                <el-button icon="el-icon-circle-plus-outline" v-if="bed.studentId ==null" circle
                           style="float: right" @click="applyBed(bed.id)"></el-button>
                <el-button icon="el-icon-info" v-else circle
                           style="float: right" @click="lookupPlaceHolder(bed.studentId)"></el-button>
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
        <div style="margin-left: 100px;margin-top: 85px">
          <img alt="" src="../../../public/myRoom.png" style="width: 600px">
        </div>
      </div>
    </el-card>

  </div>
</template>

<script>
import { applyBed, getSelfInfo, getStudentById } from '@/api/student'

export default {
  name: 'index'
  , data () {
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
      buildingRoom: {},
      myRoomBed: {},
      roomBeds: []
    }
  },
  created () {
    this.getSelfInfo()
  },
  methods: {
    // 申请床位
    applyBed (roomBedId) {
      this.$DialogForm.show({
        title: '申请床位',
        width: '50%',
        option: this.option,
        callback: async (res) => {
          await applyBed({
            roomBedId: roomBedId,
            sno: res.data.sno
          })
          this.getSelfInfo()
          this.msgNoticeSuccess('申请成功')
          res.done()
          res.close()
        }
      })
    },
    // 查看床位信息
    lookupPlaceHolder (studentId) {
      getStudentById(studentId).then(res => {
        let data = res.data
        let html = `<p>学号: ${data.sno}</p><p>学院: ${data.academy}</p><p>专业: ${data.major}</p><p>年级: ${data.grade}</p>`
        this.$alert(html, '学生信息', {
          dangerouslyUseHTMLString: true
        })
      })
    },
    getSelfInfo () {
      getSelfInfo().then(res => {
        this.buildingRoom = res.data.buildingRoom
        this.myRoomBed = res.data.myRoomBed
        this.roomBeds = res.data.roomBeds
      })
    }

  }
}
</script>

<style scoped>

</style>
