<template>
  <div class="dashboard-editor-container">
    <el-row>
      <el-card>
        <avue-data-tabs :option="option"></avue-data-tabs>
      </el-card>
    </el-row>


    <el-row style="display: flex;" :gutter="2">

      <!--  公告模块    -->
      <el-col>
        <el-card style="height: 760px">
          <span style="font-size: 22px;display: block;margin-bottom: 30px;margin-left: 10px;">公告信息</span>
          <el-timeline>
            <el-timeline-item v-for="(activity, index) in activities" :key="index" :timestamp="activity.gmtCreate">
              <el-popover
                placement="right"
                :title="activity.title"
                width="400"
                trigger="hover">
                <div v-html="activity.content"></div>
                <span slot="reference" style="font-size: 15px">{{ activity.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>

      <!--  打卡模块  -->
      <el-col v-if="roles.includes('STUDENT')">
        <el-card style="height: 760px">
          <div style="display: flex;justify-content: space-between">
            <span style="font-size: 22px;display: block;margin-bottom: 30px;margin-left: 10px;">打卡</span>
            <span v-if="!isClocked"><el-button type="primary" class="el-icon-s-promotion" style="border: none"
                                               @click="clockIn()"><span
              style="color: white;font-weight: bolder">未打卡</span></el-button></span>
            <span v-else><el-button type="success" class="el-icon-s-promotion" style="border: none"><span
              style="color: white;font-weight: bolder">已打卡</span></el-button></span>
          </div>
          <el-calendar :first-day-of-week="7">
            <template slot="dateCell" slot-scope="{ data }">
              <p>{{ data.day.split('-').slice(2).join('-') }}<br/></p>
              <div v-for="(item, index) in calendarData" :key="index">
                <div v-if="data.day === item.day">
                  <span v-if="item.clock">
                 <i class="el-icon-check" style="color: green;font-weight: bolder;">已打卡</i>
                  </span>
                  <!--                  <span v-else>-->
                  <!--                   <i class="el-icon-close" style="color: red;font-weight: bolder">未打卡</i>-->
                  <!--                  </span>-->
                </div>
              </div>
            </template>
          </el-calendar>
        </el-card>
      </el-col>
    </el-row>

    <!--  问题时间轴   -->
    <el-row>
      <el-card>
        <span style="font-size: 22px;display: block;margin-bottom: 30px;margin-left: 10px;">问题汇总</span>
        <el-timeline>
          <el-timeline-item :timestamp="problem.gmtCreate" placement="top" v-for="problem in recentProblems">
            <el-card>
              <h4>
                {{ problem.title }}
                <el-divider direction="vertical"/>
                {{ problem.bno }}
                <el-divider direction="vertical"/>
                {{ problem.rno }}
                <el-divider direction="vertical"/>
                {{ problem.studentName }}
                <el-divider direction="vertical"/>
              </h4>
              <p v-html=" problem.content "></p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import { getRecentNotice } from '@/api/notice'
import { checkClockIn, clockIn, getClockInfo } from '@/api/student'
import { mapGetters } from 'vuex'
import { getStatistical } from '@/api/statistical'
import { getRecentProblems } from '@/api/problem'

export default {
  name: 'DashboardAdmin',
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  data () {
    return {
      activities: [],
      calendarData: [],
      // 是否打卡
      isClocked: false,
      option: {
        data: []
      },
      recentProblems: []
    }
  },
  created () {
    this.getRecent()
    this.getClockInfo()
    this.checkClockIn()
    this.getStatistical()
    this.getRecentProblems()
  },
  methods: {
    getRecentProblems () {
      getRecentProblems(10).then(res => {
        this.recentProblems = res.data
      })
    },
    // 获取统计信息
    getStatistical () {
      getStatistical().then(res => {
        const { studentClockInfo, studentAccommodationInfo, buildingRoomInfo } = res.data
        let array = [
          {
            title: '打卡统计',
            subtitle: '实时',
            count: studentClockInfo.clockCount,
            allcount: studentClockInfo.allCount,
            text: '当前分类总记录数',
            color: 'rgb(27, 201, 142)',
            key: '类'
          },
          {
            title: '学生住宿统计',
            subtitle: '实时',
            count: studentAccommodationInfo.accommodationCount,
            allcount: studentAccommodationInfo.allCount,
            text: '当前分类总记录数',
            color: 'rgb(230, 71, 88)',
            key: '类'
          },
          {
            title: '空宿舍统计',
            subtitle: '实时',
            count: buildingRoomInfo.emptyCount,
            allcount: buildingRoomInfo.allCount,
            text: '当前分类总记录数',
            color: 'rgb(178, 159, 255)',
            key: '类'
          },
        ]
        this.option.data = []
        this.option.data.push(...array)
      })
    },
    // 检查是否已经打了卡
    checkClockIn () {
      checkClockIn().then(res => {
        this.isClocked = res.data
      })
    },
    // 打卡
    clockIn () {
      clockIn().then(res => {
        this.getClockInfo()
        this.checkClockIn()
        this.getStatistical()
        this.msgNoticeSuccess('打卡成功')
      })
    },
    // 获取今年打卡信息
    getClockInfo () {
      getClockInfo().then(res => {
        this.calendarData = res.data.clockInfo
      })
    },
    getRecent () {
      getRecentNotice(10).then(res => {
        this.activities = res.data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}

// 除去日历表格的padding
::v-deep .el-calendar-table .el-calendar-day {
  padding: 0;
}

// 修改当天日期样式
::v-deep .el-calendar-table td.is-today {
  background-color: #fff;
}

// 修改选中日期样式
::v-deep .el-calendar-table td.is-selected {
  background-color: #fff;
}

// 修改上个月样式
::v-deep .el-calendar-table .el-calendar-day {
  height: 60px;
  font-size: 12px;
  text-align: center;
}

// 修改下个月样式
::v-deep .el-calendar-table:not(.is-range) td.prev {
  .calendarFont {
    color: #C0C4CC;
  }

  pointer-events: none;
}

// 修改日历中星期样式
::v-deep .el-calendar-table thead th {
  font-size: 12px;
  padding-bottom: 6px;
}

.cal ::v-deep.el-calendar-day .calendar_circle1 {
  margin: 0 auto;
  padding: 2px;
  text-align: center;
}

.cal ::v-deep.el-calendar-day .calendar_circle2 {
  border: 1px solid #DE4747;
  border-radius: 50%;
  margin: 0 auto;
  padding: 2px;
  text-align: center;
}

</style>
