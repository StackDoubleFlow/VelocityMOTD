package org.stonecipher;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class MotdCommand extends Command {

    private Plugin plugin;

    public MotdCommand(Plugin plugin) {
        super("motd");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            sender.sendMessage(Motd.loadMotd(this.plugin, pp));
        }
    }
}
