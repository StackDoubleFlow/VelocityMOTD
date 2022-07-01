package org.stonecipher;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class MotdCommand implements SimpleCommand {

    private BungeeMOTD plugin;

    public MotdCommand(BungeeMOTD plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();

        if (source instanceof Player) {
            Player pp = (Player) source;
            source.sendMessage(Motd.loadMotd(this.plugin, pp));
        }
    }
}
