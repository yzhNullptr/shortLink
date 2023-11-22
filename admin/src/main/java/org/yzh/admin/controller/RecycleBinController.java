package org.yzh.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.common.convention.result.Results;
import org.yzh.admin.remote.dto.ShortLinkRemoteService;
import org.yzh.admin.remote.dto.req.RecycleBinSaveReqDTO;
import org.yzh.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 * 回收站控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    /**
     * /后续重构为SpringCloud Feign调用
     */
    private final ShortLinkRemoteService shortLinkRemoteService= new ShortLinkRemoteService(){

    };

    /**
     * 保存回收站
     */
    @PostMapping("/api/shortLink/admin/v1/recycleBin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam){
        shortLinkRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }
    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/api/shortLink/admin/v1/recycleBin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParma){

        return shortLinkRemoteService.pageRecycleBinShortLink(requestParma);
    }
}
