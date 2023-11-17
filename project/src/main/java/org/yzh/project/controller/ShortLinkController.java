package org.yzh.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.project.common.convention.result.Result;
import org.yzh.project.common.convention.result.Results;
import org.yzh.project.dto.req.ShortLinkCreateReqDTO;
import org.yzh.project.dto.resp.ShortLinkCreateRespDTO;
import org.yzh.project.service.ShortLinkService;

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
}
