package org.stonecipher;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.io.*;
import java.util.regex.Pattern;

public class Motd {

    public static String defaultMotd = "&7hey, &4&l%PLAYERNAME%&7, you nerd.";
    private static final Pattern URL_REGEX = Pattern.compile("https://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    public static String motdReplacements(BungeeMOTD plugin, String motd, Player p) {
        String tmpMotd = motd;

        tmpMotd = tmpMotd.replaceAll("%PLAYERNAME%", p.getUsername());
        tmpMotd = tmpMotd.replaceAll("%ONLINE_COUNT%", "" + plugin.server.getAllPlayers().size());
        return tmpMotd;
    }

    public static Component renderTextComponent(String raw) {
        TextReplacementConfig replacementConfig = TextReplacementConfig.builder().match(URL_REGEX).replacement(url -> {
            String clickUrl = url.content();
            return url.clickEvent(ClickEvent.openUrl(clickUrl)).hoverEvent(HoverEvent.showText(Component.text("Visit " + clickUrl)));
        }).build();
        return LegacyComponentSerializer.builder().character('&').build().deserialize(raw).replaceText(replacementConfig);
    }

    public static Component loadMotd(BungeeMOTD plugin, Player player) {
        File dataDir = plugin.dataDirectory.toFile();
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        String filename = plugin.dataDirectory + "/motd.txt";
        File motdFile = new File(filename);
        if (!motdFile.exists()) {
            try {
                motdFile.createNewFile();
                PrintStream out = new PrintStream(new FileOutputStream(filename));
                out.print(defaultMotd);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                BufferedReader motd = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
                String line = motd.readLine();

                StringBuilder sb = new StringBuilder();

                while (line != null) {
                    sb.append(line).append("\n");
                    line = motd.readLine();
                }

                String resultTemplate = sb.toString().trim();
                String resultReplaced = motdReplacements(plugin, resultTemplate, player);

                Component rendered = renderTextComponent(resultReplaced);

                return rendered;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Component.text(defaultMotd);
    }
}
