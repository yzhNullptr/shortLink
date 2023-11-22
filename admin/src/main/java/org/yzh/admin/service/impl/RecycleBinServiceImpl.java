package org.yzh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yzh.admin.common.biz.user.UserContext;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.dao.entity.GroupDO;
import org.yzh.admin.dao.mapper.GroupMapper;
import org.yzh.admin.remote.dto.ShortLinkRemoteService;
import org.yzh.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.yzh.admin.service.RecycleBinService;

import java.util.List;

/**
 * URL 回收站接口实现层
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {
    private final GroupMapper groupMapper;
    /**
     * /后续重构为SpringCloud Feign调用
     */
    private final ShortLinkRemoteService shortLinkRemoteService= new ShortLinkRemoteService(){

    };

    @Override
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParma) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<String> gidList = groupMapper.selectList(queryWrapper).stream().map(GroupDO::getGid).toList();
        requestParma.setGidList(gidList);
        return shortLinkRemoteService.pageRecycleBinShortLink(requestParma);
    }
}
