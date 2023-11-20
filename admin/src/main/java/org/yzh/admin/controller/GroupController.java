package org.yzh.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.common.convention.result.Results;
import org.yzh.admin.dto.request.ShortLinkGroupSaveReqDTO;
import org.yzh.admin.dto.request.ShortLinkGroupSortReqDTO;
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
    @PostMapping("/api/shortLink/admin/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO requestParam){
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }
    /**
     * 查询分组
     */
    @GetMapping("/api/shortLink/admin/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup(){

        return Results.success( groupService.listGroup());
    }

    /**
     * 修改短链接分组名称
     */
    @PutMapping("/api/shortLink/admin/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam){
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    /**
     * 删除短链接分组
     */
    @DeleteMapping("/api/shortLink/admin/v1/group")
    public Result<Void> deleteGroup(@RequestParam("gid")String gid){
        groupService.deleteGroup(gid);
        return Results.success();
    }

    /**
     * 短链接排序
     */
    @PostMapping("/api/shortLink/admin/v1/group/sort")
    public Result<Void> sortGroup(@RequestBody List<ShortLinkGroupSortReqDTO> requestParam){
        groupService.sortGroup(requestParam);
        return Results.success();
    }
}
