/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724 (5.7.24-log)
 Source Host           : localhost:3306
 Source Schema         : message

 Target Server Type    : MySQL
 Target Server Version : 50724 (5.7.24-log)
 File Encoding         : 65001

 Date: 23/11/2023 20:22:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint(20) NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `channel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `template_id` int(11) NULL DEFAULT NULL,
  `sender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `send_time` datetime NULL DEFAULT NULL,
  `receive_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (382857611693404160, NULL, 'mail', '两件9折,三件8折', NULL, '3406138837@qq.com', NULL, '2023-11-23 19:39:28', NULL);
INSERT INTO `message` VALUES (382858121796268032, 'mail', 'mailBox', '两件9折,三件8折,四件7折', NULL, '3406138837@qq.com', NULL, '2023-11-23 19:41:29', NULL);
INSERT INTO `message` VALUES (382868010203492352, 'mail', 'mailBox', '您好余浩，您卡号为23456777 ，地址为临沂山庄456 ，请注意查收！', NULL, '3406138837@qq.com', '2226001563@qq.com', '2023-11-23 20:20:47', NULL);

-- ----------------------------
-- Table structure for sys_outbox
-- ----------------------------
DROP TABLE IF EXISTS `sys_outbox`;
CREATE TABLE `sys_outbox`  (
  `id` bigint(20) NOT NULL,
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_outbox
-- ----------------------------
INSERT INTO `sys_outbox` VALUES (1, '百亿补贴', '2023-11-20 20:01:42');
INSERT INTO `sys_outbox` VALUES (2, '双十一', '2023-11-21 20:26:56');
INSERT INTO `sys_outbox` VALUES (3, '618', '2023-11-21 16:49:29');
INSERT INTO `sys_outbox` VALUES (4, '补贴', '2023-11-21 12:50:01');
INSERT INTO `sys_outbox` VALUES (5, '618618', '2023-11-21 22:53:57');

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template`  (
  `id` bigint(20) NOT NULL,
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `number` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of template
-- ----------------------------

-- ----------------------------
-- Table structure for user_read_sys_outbox
-- ----------------------------
DROP TABLE IF EXISTS `user_read_sys_outbox`;
CREATE TABLE `user_read_sys_outbox`  (
  `id` bigint(20) NOT NULL,
  `sys_outbox_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_read_sys_outbox
-- ----------------------------
INSERT INTO `user_read_sys_outbox` VALUES (1, 5, 12345);

SET FOREIGN_KEY_CHECKS = 1;
