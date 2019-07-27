package org.stonecipher;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMOTD extends Plugin implements Listener {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new MotdListener(this));
        getProxy().getPluginManager().registerCommand(this, new MotdCommand(this));
        getLogger().info("Loaded motd.txt");
    }
}
