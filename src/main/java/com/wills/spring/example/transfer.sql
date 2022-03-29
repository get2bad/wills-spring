/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 29/03/2022 17:08:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for transfer
-- ----------------------------
DROP TABLE IF EXISTS `transfer`;
CREATE TABLE `transfer` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `total` int DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of transfer
-- ----------------------------
BEGIN;
INSERT INTO `transfer` (`id`, `name`, `total`, `date`) VALUES (1, '老王', 20, '2022-03-29 00:00:00');
INSERT INTO `transfer` (`id`, `name`, `total`, `date`) VALUES (2, '老阮', 180, '2022-03-29 00:00:00');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
