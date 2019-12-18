package com.redecommunity.common.spigot;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.updater.data.Updater;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
public class CommonSpigot extends JavaPlugin {
    private static CommonSpigot instance;

    public CommonSpigot() {
        CommonSpigot.instance = this;
    }

    public static CommonSpigot getInstance() {
        return CommonSpigot.instance;
    }

    @Override
    public void onLoad() {
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
