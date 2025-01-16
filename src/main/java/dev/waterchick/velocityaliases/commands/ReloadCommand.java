package dev.waterchick.velocityaliases.commands;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.waterchick.velocityaliases.configs.MainConfig;
import dev.waterchick.velocityaliases.enums.ConfigValue;
import dev.waterchick.velocityaliases.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ReloadCommand implements SimpleCommand {
    private final MainConfig mainConfig;

    private final ProxyServer proxyServer;

    public ReloadCommand(MainConfig mainConfig, ProxyServer proxyServer) {
        this.mainConfig = mainConfig;
        this.proxyServer = proxyServer;
    }

    public void execute(SimpleCommand.Invocation invocation) {
        Component prefix = Utils.color(ConfigValue.MESSAGES_PREFIX.getValue());
        Component configReloaded = Utils.color(ConfigValue.MESSAGES_CONFIGRELOADED.getValue());
        Component noPermission = Utils.color(ConfigValue.MESSAGES_CONNECTINGNOPERMISSION.getValue());
        MiniMessage miniMessage = MiniMessage.miniMessage();
        CommandSource source = invocation.source();
        if (!source.hasPermission("va.reload")) {
            String noPermissionString = miniMessage.serialize(noPermission);
            if (noPermissionString.isBlank())
                return;
            source.sendMessage(prefix.append(noPermission));
            return;
        }
        this.mainConfig.reloadConfig();
        source.sendMessage(prefix.append(configReloaded));
    }

    public void register() {
        CommandManager commandManager = this.proxyServer.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("vareload").build();
        commandManager.register(commandMeta, this);
    }
}
