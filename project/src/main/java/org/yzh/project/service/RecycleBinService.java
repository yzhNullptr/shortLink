package org.yzh.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yzh.project.dao.entity.ShortLinkDO;
import org.yzh.project.dto.req.RecycleBinDeleteReqDTO;
import org.yzh.project.dto.req.RecycleBinRecoverReqDTO;
import org.yzh.project.dto.req.RecycleBinSaveReqDTO;
import org.yzh.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.yzh.project.dto.resp.ShortLinkPageRespDTO;

/**
 * 回收站管理接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 保存回收站
     * @param requestParam 请求参数
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);
    /**
     * 分页查询短链接
     * @param requestParma 短链接分页查询请求参数
     * @return 短链接分页查询返回参数
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO requestParma);

    /**
     * 回收站回复功能
     * @param requestParam 恢复短链接请求参数
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO requestParam);

    /**
     * 删除短链接
     * @param requestParam 请删除短链接求参数
     */
    void removeRecycleBin(RecycleBinDeleteReqDTO requestParam);
}
