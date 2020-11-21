package ranking.minidon.ranking;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class Rank implements Listener {
    @EventHandler
    public void saveLoc(BlockPlaceEvent event) {
        if ( event.getBlock( ).getType( ).equals( Material.GOLD_BLOCK ) ) {
            if ( event.getPlayer( ).hasPermission( "setloc.allow" ) ) {
                int x = event.getBlock( ).getLocation( ).getBlockX( );
                int y = event.getBlock( ).getLocation( ).getBlockY( );
                int z = event.getBlock( ).getLocation( ).getBlockZ( );
                Ranking.config.set( "Loc.x",x );
                Ranking.config.set( "Loc.y",y );
                Ranking.config.set( "Loc.z",z );
                Ranking.config.set( "Loc.world",event.getPlayer( ).getWorld( ).getName( ) );
                Bukkit.getPluginManager( ).getPlugin( "Ranking" ).saveConfig( );
                Bukkit.getPluginManager( ).getPlugin( "Ranking" ).reloadConfig( );
                event.getPlayer( ).sendMessage( ChatColor.GREEN + "ゴールを設置しました!" );
            }
        }
    }
}
