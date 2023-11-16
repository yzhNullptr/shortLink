package org.yzh.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yzh.admin.dao.entity.GroupDO;
import org.yzh.admin.dto.response.ShortLinkGroupRespDTO;

import java.util.List;

public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短链接分组
     *
     * @param groupName 短链接分组名
     */
    void saveGroup(String groupName);

    /**
     * 查询用户短链接分组集合
     *
     * @return 短链接分组集合
     */
    List<ShortLinkGroupRespDTO> listGroup();
}
