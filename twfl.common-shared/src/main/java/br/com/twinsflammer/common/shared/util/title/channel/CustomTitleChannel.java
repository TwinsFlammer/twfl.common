package br.com.twinsflammer.common.shared.util.title.channel;

import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import br.com.twinsflammer.common.shared.databases.redis.data.Redis;
import br.com.twinsflammer.common.shared.databases.redis.manager.RedisManager;
import br.com.twinsflammer.common.shared.util.Constants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Created by @SrGutyerrez
 */
public class CustomTitleChannel extends Channel {
    @Override
    public String getName() {
        return Constants.CUSTOM_TITLE_CHANNEL;
    }

    @Override
    public void sendMessage(String message) {
        Redis redis = RedisManager.getDefaultRedis();

        try (Jedis jedis = redis.getJedisPool().getResource()) {
            jedis.publish(
                    this.getName(),
                    message
            );
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }
}
