package dev.waterchick.velocityaliases;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.waterchick.velocityaliases.commands.ReloadCommand;
import dev.waterchick.velocityaliases.configs.MainConfig;
import dev.waterchick.velocityaliases.managers.AliasesManager;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

@Plugin(id = "velocityaliases", name = "velocityaliases", version = BuildConstants.VERSION, authors = {"Water_Chick"})
public class Velocityaliases {

    @Inject
    private final Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {}

    @Inject
    public Velocityaliases(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory) {
        this.logger = logger;
        File dataFolder = dataDirectory.toFile();
        AliasesManager aliasesManager = new AliasesManager();
        MainConfig mainConfig = new MainConfig(dataFolder, aliasesManager, proxyServer);
        mainConfig.loadConfig();
        ReloadCommand reloadCommand = new ReloadCommand(mainConfig, proxyServer);
        reloadCommand.register();
    }
}
