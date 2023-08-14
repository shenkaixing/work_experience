/*
 Navicat Premium Data Transfer

 Source Server         : 本地5.7
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : test_master

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 11/08/2023 11:14:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_account
-- ----------------------------
DROP TABLE IF EXISTS `oauth_account`;
CREATE TABLE `oauth_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `client_id` varchar(50) NOT NULL COMMENT '客户端ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(13) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '账号可用',
  `account_non_expired` tinyint(1) DEFAULT '1' COMMENT '账号未过期',
  `credentials_non_expired` tinyint(1) DEFAULT '1' COMMENT '密码未过期',
  `account_non_locked` tinyint(1) DEFAULT '1' COMMENT '账号未锁定',
  `account_non_deleted` tinyint(1) DEFAULT '1' COMMENT '账号未删除',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_idx` (`client_id`,`username`,`password`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_account
-- ----------------------------
BEGIN;
INSERT INTO `oauth_account` VALUES (1, 'ABC', 'lanya', '$2a$10$ZGiCPmQUVlDWyf..h95umeIiZ5wF98p3sN0BqYdkR3uDszr.UwRTe', '1232378743', 'abc@123.com', 1, 1, 1, 1, 1, '2023-07-17 06:44:24', '2023-07-17 06:46:45');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
