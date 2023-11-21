package org.yzh.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yzh.admin.common.biz.user.UserContext;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.dao.entity.GroupDO;
import org.yzh.admin.dao.mapper.GroupMapper;
import org.yzh.admin.dto.request.ShortLinkGroupSortReqDTO;
import org.yzh.admin.dto.request.ShortLinkGroupUpdateReqDTO;
import org.yzh.admin.dto.response.ShortLinkGroupRespDTO;
import org.yzh.admin.remote.dto.ShortLinkRemoteService;
import org.yzh.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.yzh.admin.service.GroupService;
import org.yzh.admin.toolkit.RandomGenerator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 短链接接口分组实现层
 */
@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    /**
     * /后续重构为SpringCloud Feign调用
     */
    private final ShortLinkRemoteService shortLinkRemoteService=new ShortLinkRemoteService() {
    };

    @Override
    public void saveGroup(String groupName) {
        saveGroup(UserContext.getUsername(),groupName);
    }

    @Override
    public void saveGroup(String username, String groupName) {
        String gid ;
        do {
            gid = RandomGenerator.generateRandom();
        } while (!hasGid(username,gid));
        GroupDO groupDO = GroupDO
                .builder()
                .gid(gid)
                .username(username)
                .name(groupName)
                .sortOrder(0)
                .build();
        baseMapper.insert(groupDO);
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> groupDOLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder)
                .orderByDesc(GroupDO::getUpdateTime);
        List<GroupDO> groupDOS = baseMapper.selectList(groupDOLambdaQueryWrapper);
        Result<List<ShortLinkGroupCountQueryRespDTO>> listResult = shortLinkRemoteService
                .listGroupShortLinkCount(groupDOS.stream().map(GroupDO::getGid).toList());
        List<ShortLinkGroupRespDTO> shortLinkGroupRespDTOS = BeanUtil.copyToList(groupDOS, ShortLinkGroupRespDTO.class);
        shortLinkGroupRespDTOS.forEach(echo->{
            Optional<ShortLinkGroupCountQueryRespDTO> first = listResult.getData().stream()
                    .filter(item -> Objects.equals(item.getGid(), echo.getGid()))
                    .findFirst();
            first.ifPresent(item->echo.setShortLinkCount(first.get().getShortLinkCount()));
        });
         return shortLinkGroupRespDTOS;
    }

    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getGid, requestParam.getGid())
                .eq(GroupDO::getUsername, UserContext.getUsername());
        GroupDO groupDO = new GroupDO();
        groupDO.setName(requestParam.getName());
        baseMapper.update(groupDO,updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, UserContext.getUsername());
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO,updateWrapper);
    }

    @Override
    public void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam) {
            requestParam.forEach(param->{
                GroupDO groupDO = GroupDO.builder()
                        .sortOrder(param.getSortOrder())
                        .build();
                LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                        .eq(GroupDO::getUsername, UserContext.getUsername())
                        .eq(GroupDO::getGid, param.getGid())
                        .eq(GroupDO::getDelFlag, 0);
                baseMapper.update(groupDO,updateWrapper);
            });
    }

    private boolean hasGid(String username,String gid){
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
        GroupDO hasGroupFlag = baseMapper.selectOne(queryWrapper);
        return hasGroupFlag==null;
    }
}
