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
    private final Integer id;
    @Getter
    private final String texture = "textures", signature, value;
    @Getter
    private final Long lastUse;

    @Setter
    private Boolean active;

    @Getter
    private final String owner;

    public Boolean isActive() {
        return this.active;
    }

    public Skin clone(String owner) {
        return new Skin(
                this.id,
                this.signature,
                this.value,
                this.lastUse,
                this.active,
                owner
        );
    }
}
