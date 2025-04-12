package ru.dementerqq.farmingspawn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.dementerqq.farmingspawn.FarmingSpawn;

public class FarmCMD implements CommandExecutor {
    private final FarmingSpawn plugin;
    public FarmCMD(FarmingSpawn plugin) { this.plugin = plugin; }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("farmingspawn.admin")) {
            sender.sendMessage(plugin.format("&8[&aFarmingSpawn&8] &7У вас нету права использовать данную команду!"));
            return true;
        }
        if (args.length != 0) {
            switch (args[0]) {
                case "reload":
                    plugin.loadConfigs();
                    sender.sendMessage(plugin.format("&8[&aFarmingSpawn&8] &7перезагружен!"));
                    return true;
                default:
                    sender.sendMessage(plugin.format("&8[&aFarmingSpawn&8] &7Используй: &f/farmingspawn <reload>"));
                    return true;
            }
        }
        sender.sendMessage(plugin.format("&8[&aFarmingSpawn&8] &7Используй: &f/farmingspawn <reload>"));
        return true;
    }
}
