package org.yzh.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.project.common.convention.result.Result;
import org.yzh.project.common.convention.result.Results;
import org.yzh.project.dto.req.RecycleBinDeleteReqDTO;
import org.yzh.project.dto.req.RecycleBinRecoverReqDTO;
import org.yzh.project.dto.req.RecycleBinSaveReqDTO;
import org.yzh.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.yzh.project.dto.resp.ShortLinkPageRespDTO;
import org.yzh.project.service.RecycleBinService;

/**
 * 回收站控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * 保存回收站
     */
    @PostMapping("/api/shortLink/v1/recycleBin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/api/shortLink/v1/recycleBin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkRecycleBinPageReqDTO requestParma) {

        return Results.success(recycleBinService.pageShortLink(requestParma));
    }

    /**
     * 回复短链接
     */
    @PostMapping("api/shortLink/v1/recycleBin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        recycleBinService.recoverRecycleBin(requestParam);
        return Results.success();
    }
    /**
     * 删除短链接
     */
    @PostMapping("api/shortLink/v1/recycleBin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinDeleteReqDTO requestParam){
        recycleBinService.removeRecycleBin(requestParam);
        return Results.success();
    }
}
