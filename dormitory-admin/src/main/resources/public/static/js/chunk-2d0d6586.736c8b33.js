(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0d6586"],{"71c9":function(e,a,r){"use strict";r.r(a);var t=function(){var e=this,a=e.$createElement,r=e._self._c||a;return r("div",{staticClass:"app-container"},[r("avue-crud",{attrs:{data:e.records,option:e.option,page:e.page,search:e.search},on:{"update:page":function(a){e.page=a},"update:search":function(a){e.search=a},"size-change":e.sizeChange,"current-change":e.currentChange,"search-change":e.searchChange,"search-reset":e.resetChange},scopedSlots:e._u([{key:"gender",fn:function(a){var t=a.row;return[0===t.gender?r("el-tag",{attrs:{type:"success"}},[e._v("女")]):r("el-tag",[e._v("男")])]}},{key:"grade",fn:function(a){var t=a.row;return[1===t.grade?r("el-tag",{attrs:{type:"success"}},[e._v("大一")]):e._e(),2===t.grade?r("el-tag",{attrs:{type:"success"}},[e._v("大二")]):e._e(),3===t.grade?r("el-tag",{attrs:{type:"success"}},[e._v("大三")]):e._e(),4===t.grade?r("el-tag",[e._v("大四")]):e._e()]}},{key:"clock",fn:function(a){var t=a.row;return[t.clock?r("el-tag",{attrs:{type:"success"}},[e._v("已打卡")]):r("el-tag",{attrs:{type:"primary"}},[e._v("未打卡")])]}}])})],1)},s=[],n=(r("ac1f"),r("841c"),r("b775")),l=r("751a"),c="/api/attendance";function u(e){return Object(n["a"])({url:"".concat(c,"/get/page"),method:l["a"].GET,params:e})}var i={data:function(){return{page:{total:0,layout:"total, sizes, prev, pager, next, jumper"},records:[],search:{clock:void 0,sno:void 0,name:void 0},option:{menu:!1,addBtn:!1,align:"center",searchMenuSpan:6,index:!0,indexLabel:"序号",column:[{type:"select",label:"是否打卡",search:!0,prop:"clock",dicData:[{label:"未打卡",value:!1},{label:"已打卡",value:!0}]},{label:"学号",search:!0,prop:"sno",rules:[{required:!0,message:"请输入学号",trigger:"blur"},{min:9,max:9,message:"请输入9个字符"}]},{label:"学院",prop:"academy",rules:[{required:!0,message:"请输入学院",trigger:"blur"}]},{label:"专业",prop:"major",rules:[{required:!0,message:"请输入专业",trigger:"blur"}]},{type:"select",label:"年级",prop:"grade",rules:[{required:!0,message:"请选择年级",trigger:"blur"}],dicData:[{label:"大一",value:1},{label:"大二",value:2},{label:"大三",value:3},{label:"大四",value:4}]},{label:"所在班级",prop:"inClass",rules:[{required:!0,message:"请输入所在班级",trigger:"blur"}]},{label:"姓名",prop:"name",search:!0,rules:[{required:!0,message:"请输入姓名",trigger:"blur"}]},{type:"select",label:"性别",prop:"gender",rules:[{required:!0,message:"请选择性别",trigger:"blur"}],dicData:[{label:"男",value:1},{label:"女",value:0},{label:"无",value:2}]},{label:"年龄",prop:"age",rules:[{required:!0,message:"请输入年龄",trigger:"blur"}]}]}}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.search.current=this.page.currentPage,this.search.size=this.page.pageSize,u(this.search).then((function(a){e.records=a.data.records,e.page.total=a.data.total}))},resetChange:function(e){this.search.username="",this.getList()},searchChange:function(e,a){this.search.username=e.username,this.getList(),a()},sizeChange:function(e){this.page.currentPage=1,this.page.pageSize=e,this.getList()},currentChange:function(e){this.page.currentPage=e,this.getList()}}},g=i,o=r("2877"),p=Object(o["a"])(g,t,s,!1,null,"0e25a2ee",null);a["default"]=p.exports}}]);