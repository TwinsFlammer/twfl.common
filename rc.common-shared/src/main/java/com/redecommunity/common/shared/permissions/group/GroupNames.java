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
            NOBLE = "noble",
            KNIGHT = "knight",
            FARMER = "farmer",
            DEFAULT = "default";

    @Deprecated
    public static String MANAGER = GroupNames.DIRECTOR;

    @Deprecated
    public static String ELITE = GroupNames.FARMER;
}
