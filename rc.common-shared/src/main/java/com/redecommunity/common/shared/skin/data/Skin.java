package com.redecommunity.common.shared.skin.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
