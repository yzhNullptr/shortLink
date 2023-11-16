package org.yzh.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yzh.admin.dao.entity.GroupDO;

public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短链接分组
     *
     * @param groupName 短链接分组名
     */
    void saveGroup(String groupName);
}
