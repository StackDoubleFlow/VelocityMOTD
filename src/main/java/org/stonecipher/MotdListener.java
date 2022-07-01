package org.stonecipher;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;

public class MotdListener {

    private BungeeMOTD plugin;

    public MotdListener(BungeeMOTD plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onConnected(ServerConnectedEvent e) {
        e.getPlayer().sendMessage(Motd.loadMotd(this.plugin, e.getPlayer()));
    }
}
