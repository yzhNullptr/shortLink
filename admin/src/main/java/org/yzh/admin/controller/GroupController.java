package org.yzh.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.common.convention.result.Results;
import org.yzh.admin.dto.request.ShortLinkGroupSaveReqDTO;
import org.yzh.admin.dto.request.ShortLinkGroupUpdateReqDTO;
import org.yzh.admin.dto.response.ShortLinkGroupRespDTO;
import org.yzh.admin.service.GroupService;

import java.util.List;

/**
 * 短链接分组控制层
 */

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/shortLink/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO requestParam){
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }
    /**
     * 根据名称查询分组
     */
    @GetMapping("/api/shortLink/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup(){

        return Results.success( groupService.listGroup());
    }

    /**
     * 修改短链接分组名称
     */
    @PutMapping("/api/shortLink/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam){
        groupService.updateGroup(requestParam);
        return Results.success();
    }
}
