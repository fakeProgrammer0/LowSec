/*
 Navicat Premium Data Transfer

 Source Server         : infosec-dev
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 139.196.162.127:3306
 Source Schema         : infosec

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 02/07/2019 20:52:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `showname` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码-SHA1哈希值',
  `question` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提问',
  `answer` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回答',
  `rolecode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户类型{ROLE_ACAMGR|ROLE_TCH:|ROLE_STD}',
  `description` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `lastlogintime` timestamp(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用，“0-否”，“1-是，启用” 默认：1',
  `accountNonExpired` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否过期，“0-否”，“1-是，未过期” 默认：1',
  `credentialsNonExpired` tinyint(1) NOT NULL DEFAULT 1 COMMENT '密码是否失效，“0-否”，“1-是，未失效” 默认：1',
  `accountNonLocked` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否被锁定-“0-否”，“1-是，未被锁定” 默认：1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5042 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (5038, 'mic', '木兰创新中心', '$2a$10$6FHDe67eoLfRrhZS/IaRjeVO2by2eeaCq0hwVkkH5YQC3SB7tm/aS', '?', '远正', 'ROLE_ADMIN', NULL, NULL, 1, 1, 1, 1);
INSERT INTO `user` VALUES (5039, 'mulan', '陈春华', '$2a$10$q848jxPC7xzae0OblYitse6fesiZwWQwyT/1TZiUD8xhM2GWJOSOO', '?', '华工软件', 'ROLE_USER', NULL, NULL, 1, 1, 1, 1);
INSERT INTO `user` VALUES (5040, 'test', 'test', '$2a$10$j86lhqUL0ouWS704unqKGOkVSgM8zTEO.dCcOdEArP5NbvPS0bbEG', '?', '看到我了', 'ROLE_USER', NULL, NULL, 1, 1, 1, 1);
INSERT INTO `user` VALUES (5041, 'LowSec', NULL, '$2a$10$/DsOfx7YfdhoSe5YWej7X.VMT2tVxLw.zyaUeuoX/RYCzqmeIxQiW', '?', 'LowSec', 'ROLE_ADMIN', NULL, NULL, 1, 1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
