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
-- spring_websocket.staff definition

CREATE TABLE `staff` (
    `staff_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `hashedpassword` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO spring_websocket.staff
(staff_id, username, hashedpassword, last_update, salt, email)
VALUES(1, 'leak', '9d3f90ab3e82812c3482ab7ddc0f0a4acf2bd9567ded71ed50d22d4d241bdb24', '2024-03-14 02:50:24', 'ba4fa10e3e79c89ff791a2c9012f6487', '3309411542@qq.com');
INSERT INTO spring_websocket.staff
(staff_id, username, hashedpassword, last_update, salt, email)
VALUES(8, 'ljk', '83bb5f09660a59eb5beac397e6b728a860bb26824580d8983864d06e9e3857ca', '2024-03-14 03:52:12', '5c8b923f5ef9169ddf47b90ac4aca02c', '3309411542@qq.com');


SET FOREIGN_KEY_CHECKS = 1;
