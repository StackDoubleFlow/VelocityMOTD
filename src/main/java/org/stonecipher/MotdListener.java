package org.stonecipher;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;

public class MotdListener {

    private VelocityMOTD plugin;

    public MotdListener(VelocityMOTD plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onConnected(ServerConnectedEvent e) {
        e.getPlayer().sendMessage(Motd.loadMotd(this.plugin, e.getPlayer()));
    }
}
