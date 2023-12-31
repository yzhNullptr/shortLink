package org.yzh.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.yzh.project.dao.entity.ShortLinkDO;
import org.yzh.project.dto.req.ShortLinkCreateReqDTO;
import org.yzh.project.dto.req.ShortLinkPageReqDTO;
import org.yzh.project.dto.req.ShortLinkUpdateReqDTO;
import org.yzh.project.dto.resp.ShortLinkCreateRespDTO;
import org.yzh.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.yzh.project.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

/**
 * 短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短链接
     *
     * @param requestParam 短链接请求参数
     * @return 短链接信息
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);

    /**
     * 修改短链接
     *
     * @param requestParam 修改短链接请求参数
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);


    /**
     * 分页查询短链接
     * @param requestParma 短链接分页查询请求参数
     * @return 短链接分页查询返回参数
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParma);

    /**
     * 查询短链接分组内数量
     *
     * @param gids 查询短链接分组内数量请求参数
     * @return 查询短链接分组内数量响应参数
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids);

    /**
     * 重定向到原始链接
     *
     * @param shortLink 短链接后缀
     * @param request HTTP请求
     * @param response HTTP响应
     */
    void restoreUrl(String shortLink, HttpServletRequest request, HttpServletResponse response);
}
