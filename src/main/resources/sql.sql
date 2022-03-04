-- auto-generated definition
create table t_user(
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '' primary key,
    `username` varchar(32) NOT NULL DEFAULT '' COMMENT '',
    `password` varchar(32) NOT NULL DEFAULT '' COMMENT '',
    `status` int not null DEFAULT -1 COMMENT ''
)collate = utf8_bin