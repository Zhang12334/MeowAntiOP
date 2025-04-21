package com.meow;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LanguageManager {
    private Map<String, String> messages = new HashMap<>();
    private FileConfiguration config;

    public LanguageManager(FileConfiguration config) {
        this.config = config;
        loadLanguage();
    }

    public void loadLanguage() {
        // 有效的语言列表
        Set<String> validLanguages = new HashSet<>(Arrays.asList("zh_hans", "zh_hant", "en_us", "ja_jp"));

        // 读取配置中的语言设置，默认为zh_cn
        String language = config.getString("language", "zh_hans");

        // 如果读取的语言不在有效列表中，则设为默认值
        if (!validLanguages.contains(language.toLowerCase())) {
            language = "zh_cn";
        }
        messages.clear();

        if ("zh_hans".equalsIgnoreCase(language)) {
            // 中文消息
            messages.put("TranslationContributors", "当前语言: 简体中文 (贡献者: Zhang1233)");
            messages.put("CanNotFoundMeowLibs", "未找到 MeowLibs, 请安装前置依赖 MeowLibs!");            
            messages.put("startup", "MeowAntiOP 已加载!");
            messages.put("shutdown", "MeowAntiOP 已卸载!");
            messages.put("nowusingversion", "当前使用版本:");
            messages.put("checkingupdate", "正在检查更新...");
            messages.put("checkfailed", "检查更新失败，请检查你的网络状况!");
            messages.put("updateavailable", "发现新版本:");
            messages.put("updatemessage", "更新内容如下:");
            messages.put("updateurl", "新版本下载地址:");
            messages.put("oldversionmaycauseproblem", "旧版本可能会导致问题，请尽快更新!");
            messages.put("nowusinglatestversion", "您正在使用最新版本!");
            messages.put("reloaded", "配置文件已重载!");
            messages.put("nopermission", "你没有权限执行此命令!");
            messages.put("periodic-check-start", "已触发定期检查, 正在检查是否存在非法OP");
            messages.put("periodic-check-complete", "定期检查结束, 用时: %dms");
            messages.put("periodic-check-enabled", "已启用定期检查, 间隔: %.1f秒");
            messages.put("periodic-check-disabled", "已禁用定期检查");
            messages.put("illegal-op-detected", "检测到非法OP玩家: %s, 已取消其OP权限");
        } else if ("zh_hant".equalsIgnoreCase(language)) {
            // 繁體中文消息
            messages.put("TranslationContributors", "當前語言: 繁體中文 (貢獻者: Zhang1233 & TongYi-Lingma LLM)");
            messages.put("CanNotFoundMeowLibs", "未找到 MeowLibs, 请安装前置依赖 MeowLibs!");
            messages.put("startup", "MeowAntiOP 已載入!");
            messages.put("shutdown", "MeowAntiOP 已卸載!");
            messages.put("nowusingversion", "當前使用版本:");
            messages.put("checkingupdate", "正在檢查更新...");
            messages.put("checkfailed", "檢查更新失敗，請檢查你的網絡狀態!");
            messages.put("updateavailable", "發現新版本:");
            messages.put("updatemessage", "更新內容如下:");
            messages.put("updateurl", "新版本下載地址:");
            messages.put("oldversionmaycauseproblem", "舊版本可能會導致問題，請尽快更新!");
            messages.put("nowusinglatestversion", "您正在使用最新版本!");
            messages.put("reloaded", "配置文件已重載!");
            messages.put("nopermission", "你沒有權限執行此命令!");
            messages.put("periodic-check-start", "已觸發定期检查, 正在檢查是否存在非法OP");
            messages.put("periodic-check-complete", "定期检查結束, 用時: %dms");
            messages.put("periodic-check-enabled", "已啟用定期检查, 間隔: %.1f秒");
            messages.put("periodic-check-disabled", "已禁用定期检查");
            messages.put("illegal-op-detected", "檢測到非法OP玩家: %s, 已取消其OP權限");
        } else if ("en_us".equalsIgnoreCase(language)) {
            // English messages
            messages.put("TranslationContributors", "Current Language: English (Contributors: Zhang1233)");
            messages.put("CanNotFoundMeowLibs", "MeowLibs not found, please install the dependency MeowLibs!");
            messages.put("startup", "MeowAntiOP has been loaded!");
            messages.put("shutdown", "MeowAntiOP has been disabled!");
            messages.put("nowusingversion", "Currently using version:");
            messages.put("checkingupdate", "Checking for updates...");
            messages.put("checkfailed", "Update check failed, please check your network!");
            messages.put("updateavailable", "A new version is available:");
            messages.put("updatemessage", "Update content:");
            messages.put("updateurl", "Download update at:");
            messages.put("oldversionmaycauseproblem", "Old versions may cause problems!");
            messages.put("nowusinglatestversion", "You are using the latest version!");
            messages.put("reloaded", "Configuration file has been reloaded!");
            messages.put("nopermission", "You do not have permission to execute this command!");
            messages.put("periodic-check-start", "Periodic check triggered, checking for illegal OPs");
            messages.put("periodic-check-complete", "Periodic check complete, took %dms");
            messages.put("periodic-check-enabled", "Periodic check enabled, interval: %.1f seconds");
            messages.put("periodic-check-disabled", "Periodic check disabled");
            messages.put("illegal-op-detected", "Illegal OP player detected: %s, OP permission has been revoked");
        } else if ("ja_jp".equalsIgnoreCase(language)) {
            // 日本语消息
            messages.put("TranslationContributors", "現在の言語: 日本語 (寄稿者: Zhang1233 & TongYi-Lingma LLM)");
            messages.put("CanNotFoundMeowLibs", "MeowLibsが見つかりません。プレフィックス依存をインストールしてください!");
            messages.put("startup", "MeowAntiOPがロードされました!");
            messages.put("shutdown", "MeowAntiOPが無効化されました!");
            messages.put("nowusingversion", "現在使用中のバージョン:");
            messages.put("checkingupdate", "更新を確認中...");
            messages.put("checkfailed", "更新チェックに失敗しました。ネットワークを確認してください!");
            messages.put("updateavailable", "新しいバージョンが利用できます:");
            messages.put("updatemessage", "アップデート内容:");
            messages.put("updateurl", "更新をダウンロードするURL:");
            messages.put("oldversionmaycauseproblem", "古いバージョンは問題を引き起こす可能性があります!");
            messages.put("nowusinglatestversion", "現在最新バージョンを使用しています!");
            messages.put("reloaded", "設定ファイルがリロードされました!");
            messages.put("nopermission", "このコマンドの実行に権限がありません!");
            messages.put("periodic-check-start", "定期チェックがトリガーされ、 Illegal OPのチェックが行われています");
            messages.put("periodic-check-complete", "定期チェックが完了しました, かかり時間: %dms");
            messages.put("periodic-check-enabled", "定期チェックが有効化されました, 間隔: %.1f秒");
            messages.put("periodic-check-disabled", "定期チェックが無効化されました");
            messages.put("illegal-op-detected", " Illegal OPプレイヤーが検出されました: %s, OP権限が取り消されました");
        }
    }

    /**
     * 获取语言消息
     * @param key 消息键名
     * @return 对应的语言消息，如果不存在则返回键名
     */
    public String getMessage(String key) {
        return messages.getOrDefault(key, key);
    }
}
