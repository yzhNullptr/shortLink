package org.yzh.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.remote.dto.ShortLinkRemoteService;
import org.yzh.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.yzh.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 * 短链接后管控制层
 */
@RestController("/")
public class ShortLinkController {
    /**
     * //TODO后续重构为SpringCloud Fegin调用
     */
    private final ShortLinkRemoteService shortLinkRemoteService= new ShortLinkRemoteService(){

    };
    /**
     * 创建短链接
     */
    @PostMapping("/api/shortLink/admin/v1/link/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam){
        return shortLinkRemoteService.createShortLink(requestParam);
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/shortLink/admin/v1/link/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParma){

        return shortLinkRemoteService.pageShortLink(requestParma);
    }


}
