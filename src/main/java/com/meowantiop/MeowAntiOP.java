package com.meowantiop;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MeowAntiOP extends JavaPlugin {

    // 配置项
    private LanguageManager languageManager;
    private List<String> allowedOPs; // 允许的OP列表
    private boolean periodicCheck; // 是否启用定期检查
    private double checkInterval; // 检查间隔(秒)
    private boolean showlog; // 是否在控制台显示日志
    private int taskId = -1; // 定期检查任务ID
    @Override
    public void onEnable() {
        // ------------------------------------------初始化阶段------------------------------------------

        // bstats统计
        int pluginId = 25572;
        Metrics metrics = new Metrics(this, pluginId);
        
        // 加载配置文件
        saveDefaultConfig();
        loadConfig();

        // 初始化 LanguageManager
        languageManager = new LanguageManager(getConfig());

        // 检查前置库是否加载
        if (!Bukkit.getPluginManager().isPluginEnabled("MeowLibs")) {
            getLogger().warning(languageManager.getMessage("CanNotFoundMeowLibs"));
            // 禁用插件
            getServer().getPluginManager().disablePlugin(this); 
            return;           
        }

        // ------------------------------------------开始加载插件------------------------------------------

        // 翻译者信息
        getLogger().info(languageManager.getMessage("TranslationContributors"));

        // 注册事件监听器
        Bukkit.getPluginManager().registerEvents(new Listener(this), this);

        // 启动消息
        getLogger().info(languageManager.getMessage("startup"));
        String currentVersion = getDescription().getVersion();
        getLogger().info(languageManager.getMessage("nowusingversion") + " v" + currentVersion);
        getLogger().info(languageManager.getMessage("checkingupdate"));

        // 创建更新检查器
        CheckUpdate updateChecker = new CheckUpdate(
            getLogger(), // 日志记录器
            languageManager, // 语言管理器
            getDescription() // 插件版本信息
        );    

        // 异步执行更新检查
        new BukkitRunnable() {
            @Override
            public void run() {
                updateChecker.checkUpdate();
            }
        }.runTaskAsynchronously(this);

        // 启动定期检查任务
        startPeriodicCheck();
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!isAllowedOP(player) && player.isOp()) {
                removePlayerOP(player);
                return false; // 命令执行失败
            }
        }
        return true; // 命令执行成功
    }

    @Override
    public void onDisable() {
        // 取消定期检查任务
        stopPeriodicCheck();
        getLogger().info("MeowAntiOP 已禁用!");
    }

    // 加载配置
    private void loadConfig() {
        allowedOPs = getConfig().getStringList("allowed-ops");
        periodicCheck = getConfig().getBoolean("periodic-check.enabled", true);
        checkInterval = getConfig().getDouble("periodic-check.interval", 30.0);
        showlog = getConfig().getBoolean("periodic-check.showlog", true);
    }

    // 检查所有在线玩家
    private void checkAllPlayers() {
        if (showlog) {
            // 记录开始时间
            long startTime = System.currentTimeMillis();
            // log
            getLogger().info(languageManager.getMessage("periodic-check-start"));        
            // 遍历所有在线玩家并检查其OP状态
            for (Player player : Bukkit.getOnlinePlayers()) {
                checkPlayerOP(player);
            }
            // 记录结束时间
            long endTime = System.currentTimeMillis();
            // 计算花费的时间
            long spendtime = endTime - startTime;
            // log        
            getLogger().info(String.format(languageManager.getMessage("periodic-check-complete"), spendtime));
        } else {
            // 遍历所有在线玩家并检查其OP状态
            for (Player player : Bukkit.getOnlinePlayers()) {
                checkPlayerOP(player);
            }
        }
    }
    
    // 启动定期检查任务
    private void startPeriodicCheck() {
        if (periodicCheck) {
            // 将秒转换为tick (1秒 = 20 tick)
            long intervalTicks = (long) (checkInterval * 20);
            
            // 取消现有任务
            if (taskId != -1) {
                Bukkit.getScheduler().cancelTask(taskId);
            }
            
            // 启动新任务
            taskId = new BukkitRunnable() {
                @Override
                public void run() {
                    checkAllPlayers();
                }
            }.runTaskTimer(this, intervalTicks, intervalTicks).getTaskId();
            
            getLogger().info(String.format(
                languageManager.getMessage("periodic-check-enabled"),
                checkInterval
            ));
        } else {
            getLogger().info(languageManager.getMessage("periodic-check-disabled"));
        }
    }

    // 停止定期检查任务
    private void stopPeriodicCheck() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1; // 重置任务ID
            getLogger().info(languageManager.getMessage("periodic-check-stopped"));
        }
    }
    
    // 检查玩家OP状态
    public void checkPlayerOP(Player player) {
        if (player.isOp()) {
            // 检查玩家是否在白名单中
            if (!isAllowedOP(player)) {
                // 将去除OP的操作放入主线程
                Bukkit.getScheduler().runTask(this, () -> removePlayerOP(player));
            }
        }
    }

    // 去除玩家OP状态
    public void removePlayerOP(Player player) {
        // 取消OP
        player.setOp(false);
        
        // 记录log
        getLogger().warning(String.format(
            languageManager.getMessage("illegal-op-detected"),
            player.getName()
        ));
        
        // 获取配置文件中的命令列表
        List<String> commands = getConfig().getStringList("command");
        if (commands.isEmpty()) return;
        
        // 获取当前时间
        String time = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
        
        // 替换参数并执行命令
        for (String command : commands) {
            // 替换 %player% 和 %time%
            String formattedCommand = command
                .replace("%player%", player.getName())
                .replace("%time%", time);
            
            // 以控制台身份执行命令
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
        }
    }

    // 检查玩家是否在允许的OP列表中
    public boolean isAllowedOP(Player player) {
        return allowedOPs.contains(player.getName()) || 
               allowedOPs.contains(player.getUniqueId().toString());
    }
}
