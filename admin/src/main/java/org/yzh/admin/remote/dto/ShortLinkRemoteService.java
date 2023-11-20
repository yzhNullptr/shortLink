package org.yzh.admin.remote.dto;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.yzh.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.yzh.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 短链接中台原创调用服务
 */
public interface ShortLinkRemoteService {
    /**
     * 创建短链接
     *
     * @param requestParam 创建短链接请求
     * @return 短链接创建响应
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO requestParam){
        String responseBody = HttpUtil.post("http://127.0.0.1:8001/api/shortLink/v1/create", JSON.toJSONString(requestParam));
        return JSON.parseObject(responseBody, new TypeReference<>() {
        });
    }


    /**
     * 分页查询短链接
     *
     * @param requestParma 分页查询短链接请求参数
     * @return 分页查询短链接响应
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParma){
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("gid",requestParma.getGid());
        requestMap.put("current",requestParma.getCurrent());
        requestMap.put("size",requestParma.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/shortLink/v1/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

}
