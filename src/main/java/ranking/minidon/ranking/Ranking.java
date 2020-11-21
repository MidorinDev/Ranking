package ranking.minidon.ranking;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;
import java.util.TimerTask;

public final class Ranking extends JavaPlugin {
    public static FileConfiguration config;
    public static JavaPlugin plugin;
    public static boolean GameStart = false;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager( ).registerEvents( new Rank( ),this );
        getCommand("ranking").setExecutor(new Start());
        saveDefaultConfig( );
        config = getConfig( );
        Items.onPos1();
        plugin = this;
        super.onEnable();
    }
    @Override
    public void onDisable(){
        super.onDisable();
    }
}
