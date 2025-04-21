package com.meow;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Listener implements org.bukkit.event.Listener {
    private final MeowAntiOP plugin;

    public Listener(MeowAntiOP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.checkPlayerOP(player); // 在玩家加入时检查
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        plugin.checkPlayerOP(player); // 在玩家移动时检查
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        plugin.checkPlayerOP(player); // 在玩家聊天时检查
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!plugin.isAllowedOP(player) && player.isOp()) {
            event.setCancelled(true); // 取消破坏事件
            plugin.removePlayerOP(player); // 移除op
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!plugin.isAllowedOP(player) && player.isOp()) {
            event.setCancelled(true); // 取消交互事件
            plugin.removePlayerOP(player); // 移除op
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (!plugin.isAllowedOP(player) && player.isOp()) {
                event.setCancelled(true); // 取消物品栏点击事件
                plugin.removePlayerOP(player); // 移除op
            }
        }
    }
}
