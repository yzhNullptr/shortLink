package org.yzh.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.common.convention.result.Results;
import org.yzh.admin.remote.dto.ShortLinkRemoteService;
import org.yzh.admin.remote.dto.req.RecycleBinSaveReqDTO;

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
    @PostMapping("/api/shortLink/v1/recycleBin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam){
        shortLinkRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }
}
