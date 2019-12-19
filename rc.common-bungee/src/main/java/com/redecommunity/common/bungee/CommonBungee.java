package com.redecommunity.common.bungee;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.updater.data.Updater;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
public class CommonBungee extends Plugin {
    private static CommonBungee instance;

    public CommonBungee() {
        CommonBungee.instance = this;
    }

    public static CommonBungee getInstance() {
        return CommonBungee.instance;
    }

    @Override
    public void onEnable() {
        new Common();
    }

    @Override
    public void onDisable() {
        Updater updater = new Updater(this.getFile(), Common.getBranch());

        try {
            updater.download();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
