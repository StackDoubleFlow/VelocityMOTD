package org.stonecipher;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "velocitymotd", name = "BungeeMOTD", version = "1.2",
        url = "https://github.com/StackDoubleFlow/VelocityMOTD",
        description = "This is a simple MOTD plugin written primarily for use by the Open Redstone Engineers.",
        authors = {"Nickster258", "StackDoubleFlow"})
public class VelocityMOTD {

    public final Logger logger;
    public final ProxyServer server;
    public final Path dataDirectory;

    @Inject
    public VelocityMOTD(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new MotdListener(this));
        server.getCommandManager().register("motd", new MotdCommand(this));
        logger.info("Loaded motd.txt");
    }
}
