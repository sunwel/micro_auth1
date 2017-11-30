-- ----------------------------
-- Records of tl_client
-- ----------------------------
INSERT INTO `tl_client` VALUES ('admin', 'admin_key1', null, '99', '超级管理员，拥有所有权限且无需配置', '1');
INSERT INTO `tl_client` VALUES ('didu', 'didu_key1', null, '1', '嘀嘟买车，拥有test_server.priv1和priv2权限', '1');
INSERT INTO `tl_client` VALUES ('invalid', 'invalid_key1', null, '1', '无效用户', '0');
INSERT INTO `tl_client` VALUES ('normal', 'normal_key1', null, '1', '普通用户，可以登录但没有任何特殊权限', '1');
INSERT INTO `tl_client` VALUES ('test_server', 'test_server_key1', null, '2', '测试微服务，拥有micro_auth.secret权限', '1');
INSERT INTO `tl_client` VALUES ('xunzhan', 'xunzhan_key1', null, '1', '巡展用户，拥有test_server.priv3权限', '1');

-- ----------------------------
-- Records of tl_microservice
-- ----------------------------
INSERT INTO `tl_microservice` VALUES ('1', 'micro_auth', '授权中心', null, '1');
INSERT INTO `tl_microservice` VALUES ('2', 'test_server', '测试用微服务', null, '1');

-- ----------------------------
-- Records of tl_microservice_func
-- ----------------------------
INSERT INTO `tl_microservice_func` VALUES ('1', '1', 'secret', '授权中心-密钥权限', null, '1');
INSERT INTO `tl_microservice_func` VALUES ('2', '1', 'weixin-oauth', '授权中心-获取微信授权', null, '1');
INSERT INTO `tl_microservice_func` VALUES ('3', '2', 'priv1', '测试微服务-priv1权限', null, '1');
INSERT INTO `tl_microservice_func` VALUES ('4', '2', 'priv2', '测试微服务-priv2权限', null, '1');
INSERT INTO `tl_microservice_func` VALUES ('5', '2', 'priv3', '测试微服务-priv3权限', null, '1');
INSERT INTO `tl_microservice_func` VALUES ('6', '2', 'priv4', '测试微服务-priv4权限', null, '0');

-- ----------------------------
-- Records of tl_client_func
-- ----------------------------
INSERT INTO `tl_client_func` VALUES ('1', 'didu', '3');
INSERT INTO `tl_client_func` VALUES ('2', 'didu', '4');
INSERT INTO `tl_client_func` VALUES ('3', 'didu', '2');
INSERT INTO `tl_client_func` VALUES ('4', 'xunzhan', '5');
INSERT INTO `tl_client_func` VALUES ('5', 'test_server', '1');

-- ----------------------------
-- Records of tl_secret
-- ----------------------------
INSERT INTO `tl_secret` VALUES ('1', 'vbID6jvbolj1kNMaW6uv3Vryazw2V^Qx', '1');
INSERT INTO `tl_secret` VALUES ('2', 'f9tty9pDkj&hH@zG', '1');
INSERT INTO `tl_secret` VALUES ('3', 'I0CTIBux!8dX4AMa', '0');
