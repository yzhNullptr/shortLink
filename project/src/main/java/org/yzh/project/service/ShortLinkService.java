package org.yzh.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yzh.project.dao.entity.ShortLinkDO;
import org.yzh.project.dto.req.ShortLinkCreateReqDTO;
import org.yzh.project.dto.resp.ShortLinkCreateRespDTO;

/**
 * 短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短链接
     * @param requestParam 短链接请求参数
     * @return 短链接信息
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);
}
