package dev.waterchick.velocityaliases.configs;

import com.google.common.base.Enums;
import com.velocitypowered.api.proxy.ProxyServer;
import cz.waterchick.configwrapper.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.waterchick.velocityaliases.Alias;
import dev.waterchick.velocityaliases.enums.ConfigValue;
import dev.waterchick.velocityaliases.managers.AliasesManager;

import java.io.File;
import java.util.List;

public class MainConfig extends Config {
    private final AliasesManager aliasesManager;

    private final ProxyServer proxyServer;

    public MainConfig(File dataFolder, AliasesManager aliasesManager, ProxyServer proxyServer) {
        super(dataFolder, "config.yml");
        this.aliasesManager = aliasesManager;
        this.proxyServer = proxyServer;
    }

    public void onLoad() {
        Section aliasesSection = getConfig().getSection("aliases");
        List<Alias> aliasList = this.aliasesManager.getAliasList();
        if (!aliasList.isEmpty())
            aliasList.forEach(alias -> alias.unregister(this.proxyServer));
        this.aliasesManager.clearAliasList();
        for (Object serverKey : aliasesSection.getKeys()) {
            String serverName = serverKey.toString();
            Section serverSection = aliasesSection.getSection(serverName);
            if (serverSection == null)
                continue;
            for (Object aliasKey : serverSection.getKeys()) {
                String commandAlias = aliasKey.toString().replaceAll("/", "");
                String permission = serverSection.getString(aliasKey + ".permission");
                Alias alias = new Alias(serverName, commandAlias, permission);
                this.aliasesManager.createAlias(alias);
                alias.register(this.proxyServer);
            }
        }
        Section messagesSection = getConfig().getSection("messages");
        for (Object enumKey : messagesSection.getKeys()) {
            String enumPrefix = "MESSAGES_";
            String enumKeyName = enumKey.toString().toUpperCase();
            String enumName = enumPrefix + enumKeyName;
            String message = messagesSection.getString(enumKey.toString());
            if (message == null || !Enums.getIfPresent(ConfigValue.class, enumName).isPresent())
                continue;
            ConfigValue configValue = ConfigValue.valueOf(enumName);
            configValue.setValue(message);
        }
    }

    public void onSave() {}
}
