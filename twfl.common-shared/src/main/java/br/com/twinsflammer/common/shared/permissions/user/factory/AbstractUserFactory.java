package br.com.twinsflammer.common.shared.permissions.user.factory;

import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public abstract class AbstractUserFactory<U> {
    public abstract U getUser(Integer id);

    public abstract U getUser(String username);

    public abstract U getUser(UUID uniqueId);
}
