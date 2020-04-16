package com.redecommunity.common.shared.permissions.group;

/**
 * Created by @SrGutyerrez
 */
public class GroupNames {
    public static final String MASTER = "master",
            DIRECTOR = "director",
            COORDINATOR = "coordinator",
            ADMINISTRATOR = "administrator",
            MODERATOR = "moderator",
            HELPER = "helper",
            ELITE = "elite",
            DEFAULT = "default";

    @Deprecated
    public static String MANAGER = GroupNames.DIRECTOR;
}
