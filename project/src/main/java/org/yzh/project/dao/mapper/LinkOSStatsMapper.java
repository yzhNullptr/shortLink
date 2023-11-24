package org.yzh.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.yzh.project.dao.entity.LinkOSStatsDO;

public interface LinkOSStatsMapper extends BaseMapper<LinkOSStatsDO> {
    void shortLinkOSStatus(@Param("linkOSStats") LinkOSStatsDO linkOSStatsDO);
}
