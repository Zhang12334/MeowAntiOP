package com.meowantiop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Listener implements org.bukkit.event.Listener {
    private final MeowAntiOP plugin;

    public Listener(MeowAntiOP plugin) {
        this.plugin = plugin;
    }

    // 进入游戏时检查
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.checkPlayerOP(event.getPlayer());
            }
        }.runTaskAsynchronously(plugin); // 在异步线程中执行检查
    }


    // 聊天判断
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        plugin.checkPlayerOP(player); // 在玩家聊天时检查
    }

    // 防御玩家破坏方块
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!plugin.isAllowedOP(player) && player.isOp()) {
            event.setCancelled(true); // 取消破坏事件
            plugin.removePlayerOP(player); // 移除op
        }
    }

    // 防御玩家创造拿东西
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
