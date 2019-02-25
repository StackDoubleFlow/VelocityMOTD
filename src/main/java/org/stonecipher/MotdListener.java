package org.stonecipher;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class MotdListener implements Listener {

    private Plugin plugin;

    public MotdListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        e.getPlayer().sendMessage(Motd.loadMotd(this.plugin, e.getPlayer()));
    }
}
