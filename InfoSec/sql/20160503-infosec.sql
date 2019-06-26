/*
 Navicat Premium Data Transfer

 Source Server         : mulan
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : localhost
 Source Database       : db1

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 01/10/2016 14:22:32 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(30) NOT NULL COMMENT '登录名',
  `showname` varchar(90) DEFAULT NULL COMMENT '显示名',
  `password` varchar(200) NOT NULL COMMENT '密码-SHA1哈希值',
  `question` varchar(45) DEFAULT NULL COMMENT '提问',
  `answer` varchar(45) DEFAULT NULL COMMENT '回答',
  `rolecode` varchar(100) NOT NULL COMMENT '用户类型{ROLE_ACAMGR|ROLE_TCH:|ROLE_STD}',
  `description` varchar(150) DEFAULT NULL COMMENT '备注',
  `lastlogintime` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用，“0-否”，“1-是，启用” 默认：1',
  `accountNonExpired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否过期，“0-否”，“1-是，未过期” 默认：1',
  `credentialsNonExpired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '密码是否失效，“0-否”，“1-是，未失效” 默认：1',
  `accountNonLocked` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否被锁定-“0-否”，“1-是，未被锁定” 默认：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5041 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('5038', 'mic', '木兰创新中心', '123456', '?', '远正', 'ROLE_ADMIN', null, null, '1', '1', '1', '1'), ('5039', 'mulan', '陈春华', '123456', '?', '华工软件', 'ROLE_USER', null, null, '1', '1', '1', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
