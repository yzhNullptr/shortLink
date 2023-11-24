package org.yzh.project.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.yzh.project.common.database.BaseDO;

import java.util.Date;

/**
 * 监控地区实体
 */
@TableName(value = "t_link_locate_stats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkLocateStatsDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量
     */
    private Integer cnt;

    /**
     * 省份名称
     */
    private String province;

    /**
     * 市名称
     */
    private String city;

    /**
     * 城市编号
     */
    private String adcode;

    /**
     * 国家
     */
    private String country;
}
