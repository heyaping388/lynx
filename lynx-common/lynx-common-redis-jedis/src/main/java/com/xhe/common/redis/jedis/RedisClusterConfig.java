package com.xhe.common.redis.jedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;

/**
 * @author xhe
 */
@Configuration
public class RedisClusterConfig {

    @Resource
    private RedisClusterProperties redisClusterProperties;

    @Bean
    public JedisCluster redisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        for (String node : redisClusterProperties.getNodes()) {
            String[] parts = StringUtils.split(node, ":");
            Assert.notNull(parts,"redis node should not be null.");
            Assert.state(parts.length == 2,
                       "redis node should be defined as 'host:port', not '" + Arrays.toString(parts) + "'");
            nodes.add(new HostAndPort(parts[0], Integer.parseInt(parts[1])));
        }
        JedisCluster cluster;
        if (StringUtils.isEmpty(redisClusterProperties.getPassword())) {
            cluster = new JedisCluster(nodes, redisClusterProperties.getConnectionTimeout(),
                                       redisClusterProperties.getSoTimeout(), redisClusterProperties.getMaxAttempts(),
                                       new JedisPoolConfig());
        } else {
            cluster = new JedisCluster(nodes, redisClusterProperties.getConnectionTimeout(),
                                       redisClusterProperties.getSoTimeout(), redisClusterProperties.getMaxAttempts(),
                                       redisClusterProperties.getPassword(),
                                       new JedisPoolConfig());
        }
        return cluster;
    }
}
