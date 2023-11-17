package org.yzh.project.service.impl;

import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.yzh.project.common.convention.exception.ServiceException;
import org.yzh.project.dao.entity.ShortLinkDO;
import org.yzh.project.dao.mapper.ShortLinkMapper;
import org.yzh.project.dto.req.ShortLinkCreateReqDTO;
import org.yzh.project.dto.resp.ShortLinkCreateRespDTO;
import org.yzh.project.service.ShortLinkService;
import org.yzh.project.toolkit.HashUtil;

/**
 * 短链接接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {
    private final RBloomFilter<String> rBloomFilter;
    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam) {
        String shortLinkSuffix = generateSuffix(requestParam);
        String fullShortUrl = StrBuilder.create(requestParam.getDomain())
                .append("/")
                .append(shortLinkSuffix)
                .toString();
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(requestParam.getDomain())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .createType(requestParam.getCreateType())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .shortUri(shortLinkSuffix)
                .enableStatus(0)
                .fullShortUrl(fullShortUrl)
                .build();

        try{
            baseMapper.insert(shortLinkDO);
        }catch (DuplicateKeyException ex){
            LambdaQueryWrapper<ShortLinkDO> lambdaQueryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl);
            ShortLinkDO hasShortLinkDO = baseMapper.selectOne(lambdaQueryWrapper);
            if (hasShortLinkDO!=null){
                log.info("短链接：{}重复入库",fullShortUrl);
                throw new ServiceException("短链接生成重复");
            }

        }
        rBloomFilter.add(shortLinkSuffix);
        return ShortLinkCreateRespDTO.builder()
                .fullShortLink(shortLinkDO.getFullShortUrl())
                .gid(requestParam.getGid())
                .originUrl(requestParam.getOriginUrl())
                .build();
    }

    private String generateSuffix(ShortLinkCreateReqDTO requestParam){
        int customGenerateCount=0;
        String originUrl = requestParam.getOriginUrl();
        String shortUri;
        do {
            if (customGenerateCount>10)throw new ServiceException("短链接生成频繁");
            originUrl+=System.currentTimeMillis();
            shortUri=HashUtil.hashToBase62(originUrl);
            if (!rBloomFilter.contains(requestParam.getDomain()+"/"+shortUri))break;
            else customGenerateCount++;
        }while (true);
        return shortUri;
    }
}
