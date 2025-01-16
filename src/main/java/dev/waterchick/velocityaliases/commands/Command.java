package dev.waterchick.velocityaliases.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.waterchick.velocityaliases.Alias;
import dev.waterchick.velocityaliases.enums.ConfigValue;
import dev.waterchick.velocityaliases.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Optional;


public class Command implements SimpleCommand {
    private final Alias alias;

    private final ProxyServer proxyServer;

    public Command(Alias alias, ProxyServer proxyServer) {
        this.alias = alias;
        this.proxyServer = proxyServer;
    }

    public void execute(SimpleCommand.Invocation invocation) {
        CommandSource sender = invocation.source();
        Component prefix = Utils.color(ConfigValue.MESSAGES_PREFIX.getValue());
        Component alreadyConnected = Utils.color(ConfigValue.MESSAGES_ALREADYCONNECTED.getValue());
        Component noPermission = Utils.color(ConfigValue.MESSAGES_CONNECTINGNOPERMISSION.getValue());
        Component connecting = Utils.color(ConfigValue.MESSAGES_CONNECTING.getValue());
        Component onlyPlayer = Utils.color(ConfigValue.MESSAGES_ONLYPLAYER.getValue());
        MiniMessage miniMessage = MiniMessage.miniMessage();
        if (!(sender instanceof Player player)) {
            if (miniMessage.serialize(onlyPlayer).isBlank())
                return;
            sender.sendMessage(prefix.append(onlyPlayer));
            return;
        }
        if (this.alias.getPermission() != null && !this.alias.getPermission().isEmpty() && !player.hasPermission(this.alias.getPermission())) {
            if (miniMessage.serialize(noPermission).isBlank())
                return;
            String noPermissionString = miniMessage.serialize(noPermission).replaceAll("%server%", this.alias.getServerName());
            noPermission = miniMessage.deserialize(noPermissionString);
            player.sendMessage(prefix.append(noPermission));
            return;
        }
        Optional<RegisteredServer> optionalRegisteredServer = this.proxyServer.getServer(this.alias.getServerName());
        if (optionalRegisteredServer.isEmpty())
            return;
        RegisteredServer server = optionalRegisteredServer.get();
        Optional<ServerConnection> playerServer = player.getCurrentServer();
        if (playerServer.isPresent() && server.getPlayersConnected().contains(player)) {
            String alreadyConnectedString = miniMessage.serialize(alreadyConnected).replace("%server%", server.getServerInfo().getName());
            if (alreadyConnectedString.isBlank())
                return;
            alreadyConnected = miniMessage.deserialize(alreadyConnectedString);
            player.sendMessage(prefix.append(alreadyConnected));
            return;
        }
        String connectingString = miniMessage.serialize(connecting).replace("%server%", server.getServerInfo().getName());
        ConnectionRequestBuilder connectionRequestBuilder = Utils.sendPlayerToServer(player, server);
        if (!connectingString.isBlank()) {
            connecting = miniMessage.deserialize(connectingString);
            player.sendMessage(prefix.append(connecting));
        }
        connectionRequestBuilder.connect();
    }
}
