package org.yzh.project.common.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置
 */
@Configuration
public class RBloomFilterConfiguration {
    /**
     * 防止短链接创建查询数据库的布隆过滤器
     */
    @Bean
    public RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter(RedissonClient redissonClient){
        RBloomFilter<String> cachePenetrationBloomFilter=  redissonClient.getBloomFilter("shortUriCreateCachePenetrationBloomFilter");
        //tryInit两个核心参数 expectedInsertions预估布隆过滤器存储元素的长度  falseProbability 运行的误判率
        cachePenetrationBloomFilter.tryInit(100000000L,0.001);
        return cachePenetrationBloomFilter;
    }
}
