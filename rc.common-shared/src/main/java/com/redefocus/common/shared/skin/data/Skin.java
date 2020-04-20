package com.redefocus.common.shared.skin.data;

import com.google.common.collect.Maps;
import com.redefocus.common.shared.skin.dao.SkinDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class Skin {
    @Getter
    private final Integer id, userId;
    @Getter
    private final String texture = "textures", signature, value;

    @Getter
    @Setter
    private Long lastUse;

    @Setter
    private Boolean active;

    private final String owner;

    public String getOwner() {
        return this.owner.toLowerCase();
    }

    public Boolean isActive() {
        return this.active;
    }

    public void active() {
        SkinDao skinDao = new SkinDao();

        HashMap<String, Object> keys = Maps.newHashMap();

        this.setActive(true);
        this.setLastUse(System.currentTimeMillis());

        keys.put("active", this.active);
        keys.put("last_use", this.lastUse);

        skinDao.update(
                keys,
                "id",
                this.id
        );
    }

    public void deactivate() {
        SkinDao skinDao = new SkinDao();

        HashMap<String, Boolean> keys = Maps.newHashMap();

        this.setActive(false);

        keys.put("active", this.active);

        skinDao.update(
                keys,
                "id",
                this.id
        );
    }

    public Skin clone(String owner) {
        return new Skin(
                this.id,
                this.userId,
                this.signature,
                this.value,
                System.currentTimeMillis(),
                true,
                owner
        );
    }
}
