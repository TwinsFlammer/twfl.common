package br.com.twinsflammer.common.shared.permissions.user.group.data;

import br.com.twinsflammer.common.shared.permissions.group.data.Group;
import br.com.twinsflammer.common.shared.permissions.group.manager.GroupManager;
import br.com.twinsflammer.common.shared.server.data.Server;
import br.com.twinsflammer.common.shared.server.manager.ServerManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class UserGroup {
    private final Group group;
    private final Server server;
    private final Long duration;

    public static UserGroup toUserGroup(ResultSet resultSet) throws SQLException {
        return new UserGroup(
                GroupManager.getGroup(resultSet.getInt("group_id")),
                ServerManager.getServer(resultSet.getInt("server_id")),
                resultSet.getLong("end_time")
        );
    }
}
