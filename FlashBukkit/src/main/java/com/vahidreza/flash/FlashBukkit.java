package com.vahidreza.flash;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FlashBukkit extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "flash:proxy");

        if (this.getCommand("flc") != null) {
            this.getCommand("flc").setExecutor(this);
        }
        if (this.getCommand("flashcmd") != null) {
            this.getCommand("flashcmd").setExecutor(this);
        }

        getLogger().info("FlashBukkit enabled and ready to send commands to Velocity.");
    }

    @Override
    public void onDisable() {
        getLogger().info("FlashBukkit disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("Usage: /" + label + " <command>");
            return true;
        }

        if (sender instanceof Player && !sender.hasPermission("flash.use")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }

        String cmd = String.join(" ", args);
        String senderName;

        if (sender instanceof Player) {
            senderName = sender.getName();
        } else {
            senderName = "Console";
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("flashcmd");
        out.writeUTF(senderName);
        out.writeUTF(cmd);

        try {
            if (sender instanceof Player) {
                ((Player) sender).sendPluginMessage(this, "flash:proxy", out.toByteArray());
            } else {
                Player onlinePlayer = Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);
                if (onlinePlayer != null) {
                    onlinePlayer.sendPluginMessage(this, "flash:proxy", out.toByteArray());
                } else {
                    getLogger().info("No online players to send flashcmd from console.");
                }
            }

            if (Bukkit.getMessenger().getIncomingChannels().isEmpty()) {
                getLogger().severe("Communication with the velocity server could not be established");
            }
        } catch (Exception e) {
            getLogger().severe("Communication with the velocity server could not be established");
            e.printStackTrace();
        }

        return true;
    }
}
