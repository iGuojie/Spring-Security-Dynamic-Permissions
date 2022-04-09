SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(0) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单描述',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '可以访问的url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `menu` VALUES (0, '公共资源', '所有人可以访问的资源', '/getVerificationCode.do');
INSERT INTO `menu` VALUES (1, '用户菜单', '用户可以进行操作的菜单', '/hello');
INSERT INTO `menu` VALUES (2, '系统菜单', '管理员可以进行的操作', '/*');

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `name_zh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色中文名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `role` VALUES (0, 'Role_visitor', '访客');
INSERT INTO `role` VALUES (1, 'Role_admin', '系统管理员');
INSERT INTO `role` VALUES (2, 'Role_user', '用户');

DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `mid` int(0) NOT NULL COMMENT 'menu的主键',
  `rid` int(0) NOT NULL COMMENT 'role的主键'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `role_menu` VALUES (0, 0);
INSERT INTO `role_menu` VALUES (2, 2);
INSERT INTO `role_menu` VALUES (2, 1);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否可用',
  `accountNonExpired` tinyint(1) NULL DEFAULT NULL COMMENT '账户是否过期',
  `accountNonLocked` tinyint(1) NULL DEFAULT NULL COMMENT '账户是否锁定',
  `credentialsNonExpired` tinyint(1) NULL DEFAULT NULL COMMENT '密码是否过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (1, 'root', '{noop}123', 1, 1, 1, 1);
INSERT INTO `user` VALUES (2, 'iguojie', '{noop}123', 1, 1, 1, 1);

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `uid` int(0) NULL DEFAULT NULL COMMENT 'user主键',
  `rid` int(0) NULL DEFAULT NULL COMMENT 'role主键',
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);

DROP VIEW IF EXISTS `权限一览`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `权限一览` AS select `user`.`username` AS `用户名`,`role`.`name_zh` AS `权限名称中文`,`menu`.`name` AS `菜单名称`,`role`.`name` AS `权限名称`,`menu`.`url` AS `url` from ((((`user` join `user_role` on((`user`.`id` = `user_role`.`uid`))) join `role` on((`user_role`.`rid` = `role`.`id`))) join `role_menu` on((`role`.`id` = `role_menu`.`mid`))) join `menu` on((`role_menu`.`mid` = `menu`.`id`)));

SET FOREIGN_KEY_CHECKS = 1;
