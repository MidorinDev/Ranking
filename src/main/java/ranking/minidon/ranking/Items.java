package ranking.minidon.ranking;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Items {
    public static ItemStack pos1 = new ItemStack( Material.GOLD_BLOCK);
    public static ItemMeta im0 = pos1.getItemMeta();
    public static void onPos1()
    {
        im0.setDisplayName( ChatColor.GREEN + "ゴールBLOCK");
        im0.setLore( Collections.singletonList(ChatColor.GRAY + "ゴールとなる場所に設置するブロック"));
        pos1.setItemMeta(im0);
    }
}
