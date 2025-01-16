package dev.waterchick.velocityaliases.utils;

import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;


public class Utils {
    public static ConnectionRequestBuilder sendPlayerToServer(Player player, RegisteredServer registeredServer) {
        return player.createConnectionRequest(registeredServer);
    }

    public static Component color(String message) {
        return (Component) LegacyComponentSerializer.legacy('&').deserialize(message);
    }
}
