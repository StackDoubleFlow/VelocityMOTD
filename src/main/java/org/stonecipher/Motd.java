package org.stonecipher;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Motd {

    public static String defaultMotd = "&7hey, &4&l%PLAYERNAME%&7, you nerd.";

    public static String motdReplacements(String motd, ProxiedPlayer p) {
        String tmpMotd = motd;
        tmpMotd = tmpMotd.replaceAll("%PLAYERNAME%", p.getDisplayName());
        tmpMotd = tmpMotd.replaceAll("%ONLINE_COUNT%", "" + ProxyServer.getInstance().getOnlineCount());
        tmpMotd = tmpMotd.replaceAll("&", "§");
        return tmpMotd;
    }

    public static TextComponent renderTextComponent(String raw) {
        String urlRegex = "https://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(raw);

        TextComponent textBuilder = new TextComponent();
        int lastIndex = 0;

        while (urlMatcher.find()) {
            int startingIndex = urlMatcher.start();
            int endingIndex = urlMatcher.end();
            TextComponent prefix = new TextComponent(raw.substring(lastIndex, startingIndex));
            textBuilder.addExtra(prefix);
            TextComponent url = new TextComponent(raw.substring(startingIndex, endingIndex));
            url.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Visit " + raw.substring(startingIndex, endingIndex)).create()));
            url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, raw.substring(startingIndex, endingIndex)));
            textBuilder.addExtra(url);
            lastIndex = endingIndex;
        }
        textBuilder.addExtra(new TextComponent(raw.substring(lastIndex)));
        return textBuilder;
    }

    public static TextComponent loadMotd(Plugin plugin, ProxiedPlayer player) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File motdFile = new File(plugin.getDataFolder(), "motd.txt");
        if (!motdFile.exists()) {
            try {
                motdFile.createNewFile();
                PrintStream out = new PrintStream(new FileOutputStream(plugin.getDataFolder().toString() + "/motd.txt"));
                out.print(defaultMotd);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                BufferedReader motd = new BufferedReader(new InputStreamReader(new FileInputStream(plugin.getDataFolder().toString() + "/motd.txt")));
                String line = motd.readLine();

                StringBuilder sb = new StringBuilder();

                while(line != null){
                    sb.append(line).append("\n");
                    line = motd.readLine();
                }

                String resultTemplate = sb.toString().trim();
                String resultReplaced = motdReplacements(resultTemplate, player);

                TextComponent rendered = renderTextComponent(resultReplaced);

                return rendered;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new TextComponent(defaultMotd);
    }
}
