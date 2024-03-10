/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 8,19
 Source Host           : localhost:3306
 Source Schema         : spring_websocket

 Target Server Type    : MySQL
 Target Server Version : 8.19
 File Encoding         : 65001

 Date: 10/08/2020 15:35:07
*/
CREATE database spring_websocket;
use spring_websocket;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `staff_id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `last_update` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`staff_id`) USING BTREE,
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES (1, 'Mike', '81dc9bdb52d04dc20036dbd8313ed055', '2024-01-29 14:27:38');
INSERT INTO `staff` VALUES (2, 'Jon', '1234', '2024-01-29 14:27:38');
INSERT INTO `staff` VALUES (3, 'TOM', '1234', '2024-1-11 11:08:10');

SET FOREIGN_KEY_CHECKS = 1;
