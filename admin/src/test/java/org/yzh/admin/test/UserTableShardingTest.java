package org.yzh.admin.test;

public class UserTableShardingTest {
    public static final String SQL="create table t_user_%d\n" +
            "(\n" +
            "    id            bigint auto_increment comment 'ID'\n" +
            "        primary key,\n" +
            "    username      varchar(256)             null comment '用户名',\n" +
            "    password      varchar(512)             null comment '密码',\n" +
            "    real_name     varchar(256)             null comment '真实姓名',\n" +
            "    phone         varchar(128)             null comment '手机号',\n" +
            "    mail          varchar(512)             null comment '邮箱',\n" +
            "    deletion_time bigint   default (now()) null comment '注销时间戳',\n" +
            "    create_time   datetime default (now()) null comment '创建时间',\n" +
            "    update_time   datetime default (now()) null comment '修改时间',\n" +
            "    del_flag      tinyint  default 0       null comment '逻辑删除 0:未删除   1：已删除'\n" +
            ")\n" +
            "    comment '用户';";
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n",i);
        }
    }
}
