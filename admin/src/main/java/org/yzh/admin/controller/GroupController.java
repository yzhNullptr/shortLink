package org.yzh.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.common.convention.result.Results;
import org.yzh.admin.dto.request.ShortLinkGroupSaveReqDTO;
import org.yzh.admin.service.GroupService;

/**
 * 短链接分组控制层
 */

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/api/shortLink/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO shortLinkGroupSaveReqDTO){
        groupService.saveGroup(shortLinkGroupSaveReqDTO.getName());
        return Results.success();
    }
}
