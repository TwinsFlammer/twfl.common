package br.com.twinsflammer.common.shared.permissions.user.group.channel;

import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import br.com.twinsflammer.common.shared.util.Constants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Created by @SrGutyerrez
 */
public class UserGroupChannel extends Channel {
    @Override
    public String getName() {
        return Constants.USER_GROUP_CHANNEL;
    }

    @Override
    public void sendMessage(String message) {
        try (Jedis jedis = this.getJedisPool().getResource()) {
            jedis.publish(
                    this.getName(),
                    message
            );
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }
}
