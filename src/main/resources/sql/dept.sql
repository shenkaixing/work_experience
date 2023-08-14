/*
 Navicat Premium Data Transfer

 Source Server         : 本地5.7
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 11/08/2023 17:38:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` bigint(255) NOT NULL COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dept
-- ----------------------------
BEGIN;
INSERT INTO `dept` VALUES (1, '控股集团', NULL);
INSERT INTO `dept` VALUES (2, '淘天集团', 1);
INSERT INTO `dept` VALUES (3, '菜鸟', 1);
INSERT INTO `dept` VALUES (4, '云智能', 1);
INSERT INTO `dept` VALUES (5, '大文娱', 1);
INSERT INTO `dept` VALUES (6, '本地生活', 1);
INSERT INTO `dept` VALUES (7, '钉钉', 4);
INSERT INTO `dept` VALUES (8, '瓴羊', 4);
INSERT INTO `dept` VALUES (9, '淘宝', 2);
INSERT INTO `dept` VALUES (10, '天猫', 2);
INSERT INTO `dept` VALUES (11, '乌鸫科技', 7);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
