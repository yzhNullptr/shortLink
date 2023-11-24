package org.yzh.project.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yzh.project.common.database.BaseDO;

import java.util.Date;

/**
 * 监控浏览器实体
 */
@TableName(value = "t_link_browser_stats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkBrowserStatsDO extends BaseDO {
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
     * 浏览器
     */
    private String browser;
}
