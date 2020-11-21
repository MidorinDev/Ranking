package ranking.minidon.ranking;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Start implements CommandExecutor {
    Location goal; //ゴールロケーション
    Map<UUID,Long> locs = new HashMap<>();
    Map<UUID,String> NickSave = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {
        if(sender.hasPermission( "pk.start" )){
            int x= Ranking.config.getInt( "Loc.x");
            int y = Ranking.config.getInt( "Loc.y");
            int z = Ranking.config.getInt( "Loc.z");
            String ws = Ranking.config.getString("Loc.world");
            World w = Bukkit.getWorld(ws);
            //World w = Bukkit.getWorld(worldname);
            Location xyz = new Location(w, x, y, z);
            goal = xyz;
            Timer timer = new Timer();

            if(args.length == 0){
                sender.sendMessage(ChatColor.RED+"/ranking <on(順位付けを有効化)/off(順位付けを無効化)/setup(ゴールブロックの付与)/reset(リセット)>");
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase( "on" )){
                    if(Ranking.GameStart){
                        sender.sendMessage( ChatColor.RED+"順位付けは既に有効です" );
                    }else {
                        Ranking.GameStart = true;
                        sender.sendMessage( ChatColor.RED+"順位付けを有効化しました!");

                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        if(Ranking.GameStart){
                                        }else {
                                            timer.cancel();
                                        }
                                        int y = Ranking.config.getInt( "Loc.y" );
                                        int z = Ranking.config.getInt( "Loc.z" );
                                        Location xyz = new Location(w, x, y, z);
                                        for(Player p: Bukkit.getOnlinePlayers()){
                                            long d = (long) goal.distance(p.getLocation());
                                            locs.put( p.getUniqueId( ),d );

                                        }
                                        Map<UUID,Long> l = locs.entrySet().stream().
                                                parallel()
                                                .sorted(Map.Entry.<UUID,Long>comparingByValue())
                                                .collect( Collectors.toMap( Map.Entry::getKey,Map.Entry::getValue,(e1, e2)  -> e1,LinkedHashMap::new ) );

                                        ArrayList<UUID> sorted = new ArrayList<>( l.keySet( ) );
//
                                        int i = 0;
                                        for (UUID u: sorted)
                                        {
                                            i++;
                                            Player p = Bukkit.getPlayer(u);
                                            String prefixCnf = Ranking.config.getString("Prefix");
                                            String Prefix = prefixCnf.replace("%%ranking%%",i+"");

                                            if(NickSave.get( p.getUniqueId() ) !=null){
                                                p.setDisplayName(Prefix+p.getDisplayName().replaceFirst( NickSave.get( p.getUniqueId() ),"" ));
                                                p.setPlayerListName(Prefix+p.getDisplayName().replaceFirst( NickSave.get( p.getUniqueId()),"" ));
                                                NickSave.put(p.getUniqueId(),Prefix);
                                            }else {
                                                p.setDisplayName(Prefix+p.getDisplayName());
                                                p.setPlayerListName(Prefix+p.getDisplayName());
                                                NickSave.put(p.getUniqueId(),Prefix);
                                            }

                                        }
                                    }}, 0, 1000);

                    }
                }else if(args[0].equalsIgnoreCase( "off" )){
                    if(Ranking.GameStart){
                        sender.sendMessage(ChatColor.RED+"順位付けを無効化しました!");
                        Ranking.GameStart = false;
                    }else {
                        sender.sendMessage( ChatColor.RED+"順位付けは有効ではないので無効化できません!" );
                    }
                } else if(args[0].equalsIgnoreCase("reset" )){
                    sender.sendMessage(ChatColor.RED+"リセットしました");
                    for(Player p:Bukkit.getOnlinePlayers()){
                        p.setDisplayName(p.getDisplayName().replace( NickSave.get( p.getUniqueId() ),"" ));
                        p.setPlayerListName(p.getDisplayName().replace( NickSave.get( p.getUniqueId()),"" ));
                    }
                }else if(args[0].equalsIgnoreCase( "setup" )){
                    sender.sendMessage(ChatColor.GREEN+"ゴールBLOCKを付与しました(ゴールに設置してください)");
                    ((Player)sender).getInventory().addItem( new ItemStack( Material.GOLD_BLOCK ));
                }
            }

        }else{
            sender.sendMessage(ChatColor.RED+"You can't use this!");
        }
        return true;
    }
}
