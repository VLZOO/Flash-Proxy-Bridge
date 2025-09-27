package me.vahidreza.flash;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Optional;

@Plugin(
        id = "flash",
        name = "Flash Velocity",
        version = "1.0",
        authors = {"VLZO"}
)
public class FlashVelocity {

    private final ProxyServer server;
    private final Logger logger;

    private static final ChannelIdentifier CHANNEL =
            MinecraftChannelIdentifier.create("flash", "proxy");

    @Inject
    public FlashVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        server.getChannelRegistrar().register(CHANNEL);
        logger.info("✅ FlashVelocity initialized. Listening on {}.", CHANNEL.getId());
    }

    @Subscribe
    @SuppressWarnings("UnstableApiUsage")
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(CHANNEL)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String type = in.readUTF();
        if (!"flashcmd".equals(type)) {
            return;
        }

        String playerName = in.readUTF();
        String command    = in.readUTF();

        Optional<Player> playerOpt = server.getPlayer(playerName);
        CommandSource executor = playerOpt
                .map(p -> (CommandSource) p)
                .orElseGet(server::getConsoleCommandSource);

        server.getCommandManager().executeAsync(executor, command);
    }
}