package org.yzh.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.common.convention.result.Results;
import org.yzh.admin.remote.dto.ShortLinkRemoteService;
import org.yzh.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.yzh.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.yzh.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

/**
 * 短链接后管控制层
 */
@RestController("/")
public class ShortLinkController {
    /**
     * /后续重构为SpringCloud Feign调用
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
     * 修改短链接
     */
    @PutMapping("/api/shortLink/admin/v1/link/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam){
        shortLinkRemoteService.updateShortLink(requestParam);
        return Results.success();
    }
    /**
     * 分页查询短链接
     */
    @GetMapping("/api/shortLink/admin/v1/link/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParma){

        return shortLinkRemoteService.pageShortLink(requestParma);
    }

    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/api/shortLink/admin/v1/link/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam){
        return shortLinkRemoteService.listGroupShortLinkCount(requestParam);
    }

}
