package dev.waterchick.velocityaliases;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.waterchick.velocityaliases.commands.Command;

public class Alias {
    private final String serverName;

    private final String commandAlias;

    private final String permission;

    public Alias(String serverName, String commandAlias, String permission) {
        this.serverName = serverName;
        this.commandAlias = commandAlias;
        this.permission = permission;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getCommandAlias() {
        return this.commandAlias;
    }

    public void unregister(ProxyServer proxyServer) {
        CommandManager commandManager = proxyServer.getCommandManager();
        commandManager.unregister(getCommandAlias());
    }

    public void register(ProxyServer proxyServer) {
        CommandManager commandManager = proxyServer.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder(getCommandAlias()).build();
        Command command = new Command(this, proxyServer);
        commandManager.register(commandMeta, command);
    }
}
