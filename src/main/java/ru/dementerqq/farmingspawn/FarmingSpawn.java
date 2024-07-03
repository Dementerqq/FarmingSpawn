package ru.dementerqq.farmingspawn;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dementerqq.farmingspawn.events.BukkitListener;

public final class FarmingSpawn extends JavaPlugin {
    @Override
    public void onEnable() {
        Economy economy = this.setupEconomy();
        if (economy == null) {
            this.getSLF4JLogger().error("Failed to setup economy");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getServer().getPluginManager().registerEvents(new BukkitListener(economy), this);
    }

    private Economy setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) return null;

        RegisteredServiceProvider<Economy> serviceProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (serviceProvider == null) return null;

        return serviceProvider.getProvider();
    }
}