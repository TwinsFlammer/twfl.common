package br.com.twinsflammer.common.shared.permissions.group;

/**
 * Created by @SrGutyerrez
 */
public class GroupNames {
    public static final String MASTER = "master",
            DIRECTOR = "director",
            ADMINISTRATOR = "administrator",
            MODERATOR = "moderator",
            HELPER = "helper",
            NOBLE = "noble",
            KNIGHT = "knight",
            FARMER = "farmer",
            DEFAULT = "default";

    @Deprecated
    public static final String MANAGER = GroupNames.DIRECTOR;

    @Deprecated
    public static final String COORDINATOR = GroupNames.ADMINISTRATOR;

    @Deprecated
    public static final String ELITE = GroupNames.FARMER;
}
