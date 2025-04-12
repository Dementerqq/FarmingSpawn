package ru.dementerqq.farmingspawn;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dementerqq.farmingspawn.commands.FarmCMD;
import ru.dementerqq.farmingspawn.commands.FarmTabComplete;
import ru.dementerqq.farmingspawn.events.BukkitListener;

import java.io.File;

public final class FarmingSpawn extends JavaPlugin {
    public FileConfiguration messages;
    public FileConfiguration config;
    private File configFile;
    private File messagesFile;

    @Override
    public void onEnable() {
        Economy economy = this.setupEconomy();
        if (economy == null) {
            this.getSLF4JLogger().error("Failed to setup economy");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.loadConfigs();
        getCommand("farmingspawn").setExecutor(new FarmCMD(this));
        getCommand("farmingspawn").setTabCompleter(new FarmTabComplete());
        this.getServer().getPluginManager().registerEvents(new BukkitListener(economy, this), this);
    }

    private Economy setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) return null;

        RegisteredServiceProvider<Economy> serviceProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (serviceProvider == null) return null;

        return serviceProvider.getProvider();
    }

    public void loadConfigs() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getPluginConfig() {
        return config;
    }

    public Plugin getPlugin() {
        return this;
    }

    public String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}