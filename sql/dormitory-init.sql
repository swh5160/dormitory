/*
 Navicat Premium Data Transfer

 Source Server         : linc.fun
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : linc.fun:53306
 Source Schema         : dormitory

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 17/04/2022 21:23:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别(0-女 1-男 2未知)',
  `age` int(11) NOT NULL COMMENT '年龄',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='管理员';

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bno` varchar(30) NOT NULL COMMENT '宿舍楼号',
  `name` varchar(30) NOT NULL COMMENT '宿舍楼名',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bno` (`bno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='宿舍楼';

-- ----------------------------
-- Table structure for building_room
-- ----------------------------
DROP TABLE IF EXISTS `building_room`;
CREATE TABLE `building_room` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `building_id` bigint(20) NOT NULL COMMENT '宿舍楼ID',
  `rno` varchar(30) NOT NULL COMMENT '宿舍房间号',
  `floor` int(11) NOT NULL COMMENT '楼层',
  `max_occupancy` int(11) NOT NULL DEFAULT '4' COMMENT '房间最大入住人数',
  `current_occupancy` int(11) NOT NULL DEFAULT '0' COMMENT '当前房间入住人数',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `bno` varchar(30) NOT NULL COMMENT '楼号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_building_id_rno` (`building_id`,`rno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='宿舍';

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `author` varchar(30) NOT NULL COMMENT '发布者',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='公告';

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `building_id` bigint(20) DEFAULT NULL COMMENT '宿舍楼ID',
  `bno` varchar(30) DEFAULT NULL COMMENT '楼栋号',
  `building_room_id` bigint(20) DEFAULT NULL COMMENT '宿舍ID',
  `rno` varchar(30) DEFAULT NULL COMMENT '宿舍号',
  `student_id` bigint(20) DEFAULT NULL COMMENT '学生ID',
  `student_name` varchar(30) DEFAULT NULL COMMENT '学生姓名',
  `type` tinyint(2) NOT NULL COMMENT '问题类型(0: 宿舍问题 1: 学生问题)',
  `title` varchar(30) NOT NULL COMMENT '问题标题',
  `content` longblob NOT NULL COMMENT '内容',
  `pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否通过(0: 未通过 1:通过)',
  `reply` varchar(1024) DEFAULT NULL COMMENT '回复',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='问题';

-- ----------------------------
-- Table structure for room_bed
-- ----------------------------
DROP TABLE IF EXISTS `room_bed`;
CREATE TABLE `room_bed` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `building_room_id` bigint(20) NOT NULL COMMENT '宿舍ID',
  `student_id` bigint(20) DEFAULT NULL COMMENT '学生ID',
  `sno` varchar(9) DEFAULT NULL COMMENT '学号',
  `name` varchar(30) NOT NULL COMMENT '床位名称(如一号床)',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_id` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='床位信息';

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sno` varchar(9) NOT NULL COMMENT '学号',
  `academy` varchar(30) NOT NULL COMMENT '学院',
  `major` varchar(30) NOT NULL COMMENT '专业',
  `grade` tinyint(1) NOT NULL COMMENT '年级(1:大一 2:大二 3:大三 4:大四)',
  `in_class` varchar(255) NOT NULL COMMENT '所在班级',
  `username` varchar(30) NOT NULL COMMENT '用户名(学号登录)',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别(0-女 1-男 2未知)',
  `age` int(11) NOT NULL COMMENT '年龄',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sno` (`sno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='学生';

SET FOREIGN_KEY_CHECKS = 1;
