CREATE TABLE `sys_sensitive_word` (
  `id` varchar(64) NOT NULL,
  `word_name` varchar(20) default NULL COMMENT '敏感词',
  `update_time` datetime default NULL COMMENT '更新时间',
  `istatus` char(1) default NULL COMMENT '0 信用；1 启用',
  `del_flag` char(1) default '0' COMMENT '0 未删除；1 已删除',
  PRIMARY KEY  USING BTREE (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='敏感词表';