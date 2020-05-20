package br.com.twinsflammer.common.shared.databases.redis.data;

import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Redis {
    private final String host, password;

    private JedisPool jedisPool;

    public void start() throws JedisConnectionException {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(70);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(50);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setNumTestsPerEvictionRun(50);

        this.jedisPool = new JedisPool(
                jedisPoolConfig,
                this.host,
                6379,
                0,
                this.password
        );
    }

    public void sendMessage(String channel, String message) {
        try (Jedis jedis = this.getJedisPool().getResource()) {
            jedis.publish(channel, message);
        } catch (JedisConnectionException | JedisDataException exception) {
            exception.printStackTrace();
        }
    }

    public void refresh() throws JedisConnectionException {
        if (this.jedisPool == null || this.jedisPool.isClosed()) this.start();
    }

    public void register(JedisPubSub handler, String... channels) {
        try (Jedis jedis = this.getJedisPool().getResource()) {
            jedis.subscribe(handler, channels);
        } catch (JedisConnectionException | JedisDataException exception) {
            exception.printStackTrace();
        }
    }

    public JedisPool getJedisPool() throws JedisConnectionException {
        this.refresh();

        return this.jedisPool;
    }
}
