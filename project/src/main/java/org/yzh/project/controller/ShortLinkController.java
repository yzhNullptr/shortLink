package org.yzh.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yzh.project.common.convention.result.Result;
import org.yzh.project.common.convention.result.Results;
import org.yzh.project.dto.req.ShortLinkCreateReqDTO;
import org.yzh.project.dto.req.ShortLinkPageReqDTO;
import org.yzh.project.dto.req.ShortLinkUpdateReqDTO;
import org.yzh.project.dto.resp.ShortLinkCreateRespDTO;
import org.yzh.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.yzh.project.dto.resp.ShortLinkPageRespDTO;
import org.yzh.project.service.ShortLinkService;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;
    /**
     * 创建短链接
     */
    @PostMapping("/api/shortLink/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam){
        return Results.success(shortLinkService.createShortLink(requestParam));
    }
    @PutMapping("/api/shortLink/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam){
        shortLinkService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/shortLink/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParma){

        return Results.success(shortLinkService.pageShortLink(requestParma));
    }

    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/api/shortLink/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam){
        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));
    }
}
