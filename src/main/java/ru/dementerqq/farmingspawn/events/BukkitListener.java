package ru.dementerqq.farmingspawn.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.EnumSet;
import java.util.Objects;

public class BukkitListener implements Listener {
    private static final EnumSet<Material> CROPS = EnumSet.of(
            Material.WHEAT, Material.CARROTS, Material.POTATOES
    );
    private final Economy economy;

   public BukkitListener(Economy economy) {
       this.economy = economy;
   }
    @EventHandler
    public void onCropsClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) return;

        Block block = Objects.requireNonNull(event.getClickedBlock(), "event.clickedBlock is null");
        if (block.getWorld() != Bukkit.getWorld("spawn")) return;
        if (!CROPS.contains(block.getType())) return;

        BlockData data = block.getBlockData();
        if (!(data instanceof Ageable ageable) || ageable.getAge() != ageable.getMaximumAge()) return;

        ageable.setAge(0);
        block.setBlockData(data);

        Player player = event.getPlayer();
        player.swingMainHand();
        player.playSound(player.getLocation(), Sound.BLOCK_WET_GRASS_BREAK, 1.0F, 1.0F);
        this.economy.depositPlayer(player, 0.5);
        player.sendActionBar(Component.text("+ 0.5$", NamedTextColor.GREEN));
    }
}