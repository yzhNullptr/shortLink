package org.yzh.admin.test;

public class UserTableShardingTest {
    public static final String SQL= "create table t_link_%d\n" +
            "(\n" +
            "    id              bigint auto_increment comment 'ID'\n" +
            "        primary key,\n" +
            "    domain          varchar(128) charset utf8mb4  null comment '域名',\n" +
            "    short_uri       varchar(8) charset utf8mb4    null comment '短链接',\n" +
            "    full_short_url  varchar(128) charset utf8mb4  null comment '完整短链接',\n" +
            "    origin_url      varchar(1024) charset utf8mb4 null comment '原始链接',\n" +
            "    click_num       int                           null comment '点击量',\n" +
            "    gid             varchar(32) charset utf8mb4   null comment '分组标识',\n" +
            "    enable_status   tinyint                       null comment '启用标识 0：启用 1：未启用',\n" +
            "    create_type     tinyint                       null comment '创建类型 0：接口创建 1：控制台创建',\n" +
            "    valid_date_type tinyint                       null comment '有效期类型 0：永久有效 1：自定义有效期',\n" +
            "    valid_date      datetime                      null comment '有效期',\n" +
            "    `describe`      varchar(1024) charset utf8mb4 null comment '描述',\n" +
            "    create_time     datetime                      null comment '创建时间',\n" +
            "    update_time     datetime                      null comment '更新时间',\n" +
            "    del_flag        tinyint                       null comment '删除标识',\n" +
            "    constraint idx_unique_full_short_url\n" +
            "        unique (full_short_url)\n" +
            ")\n" +
            "    collate = utf8_bin;\n" +
            "\n";
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n",i);
        }
    }
}
