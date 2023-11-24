package org.yzh.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.yzh.project.dao.entity.LinkLocateStatsDO;

/**
 * 地区统计访问持久层
 */
public interface LinkLocateStatsMapper extends BaseMapper<LinkLocateStatsDO> {
    void shortLinkLocateStatus(@Param("linkLocateStats") LinkLocateStatsDO linkLocateStatsDO);
}
