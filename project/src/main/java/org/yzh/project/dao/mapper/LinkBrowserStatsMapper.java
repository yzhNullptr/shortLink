package org.yzh.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.yzh.project.dao.entity.LinkBrowserStatsDO;

public interface LinkBrowserStatsMapper extends BaseMapper<LinkBrowserStatsDO> {
    void shortLinkBrowserStats(@Param("linkBrowserStats") LinkBrowserStatsDO linkBrowserStatsDO);
}
