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

        getLogger().info("FlashBukkit enabled and ready to send commands to Velocity.");
    }

    @Override
    public void onDisable() {
        getLogger().info("FlashBukkit disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!sender.hasPermission("flash.use")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage: /" + label + " <command>");
            return true;
        }

        Player player = (Player) sender;
        String cmd = String.join(" ", args);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("flashcmd");
        out.writeUTF(player.getName());
        out.writeUTF(cmd);

        try {
            player.sendPluginMessage(this, "flash:proxy", out.toByteArray());

            if (Bukkit.getMessenger().getIncomingChannels().isEmpty()) {
                getLogger().severe("Communication with the velocity server could not be established");
            }
        } catch (Exception e) {
            getLogger().severe("Communication with the velocity server could not be established");
        }

        return true;
    }
}
