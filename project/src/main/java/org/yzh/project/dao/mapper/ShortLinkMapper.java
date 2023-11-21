package org.yzh.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.yzh.project.dao.entity.ShortLinkDO;
import org.yzh.project.dto.resp.ShortLinkGroupCountQueryRespDTO;

import java.util.List;

/**
 * 短链接持久层
 */
public interface ShortLinkMapper extends BaseMapper<ShortLinkDO> {
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids);
}
