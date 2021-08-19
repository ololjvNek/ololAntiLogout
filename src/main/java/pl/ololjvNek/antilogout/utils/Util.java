package pl.ololjvNek.antilogout.utils;

import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import pl.ololjvNek.antilogout.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Util {

    static String[] find;
    static String[] replace;
    private static String serverName;
    private static int playerCount;

    static {
        Util.find = new String[] { ">>", "<<", "{o}", "{PREFIX}" };
        Util.replace = new String[] { "»", "«", "\u2022", "&8[&4<!>&8]" };
    }

    public static void sendTitle(final Player player, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)length);
    }

    public static void sendSubTitle(final Player player, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)length);
    }

    public static void sendTitle(final Collection<? extends Player> players, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        for (final Player player : players) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)title);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)length);
        }
    }

    public static void sendSubTitle(final Collection<? extends Player> players, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        for (final Player player : players) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)title);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)length);
        }
    }

    public static void sendTitleX(final Player player, final String text, final int show, final int duration, final int end) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(20 * show, 20 * duration, 20 * end);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)length);
    }

    public static void sendSubTitleX(final Player player, final String text, final int show, final int duration, final int end) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(20 * show, 20 * duration, 20 * end);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)length);
    }

    public static void sendActionBar(final Player player, final String text) {
        final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\"}");
        final PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)bar);
    }

    public static void sendActionBar(final Collection<? extends Player> players, final String text) {
        final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\"}");
        final PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        for (final Player player : players) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)bar);
        }
    }

    public static double round(double value, int decimals)
    {
        double p = Math.pow(10.0D, decimals);
        return Math.round(value * p) / p;
    }

    public static String setHEX(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String fixColors(final String text) {
        return ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(text, Util.find, Util.replace));
    }

    public static List<String> fixColors(final List<String> text) {
        final List<String> ret = new ArrayList<String>();
        for (final String s : text) {
            ret.add(ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(s, Util.find, Util.replace)));
        }
        return ret;
    }

    public static String changeTimeToProgressString(long time){
        if(DataUtil.secondsToString(time).isEmpty()){
            return "&aMozesz sie bezpiecznie wylogowac";
        }
        int antilogouttime = Main.getPlugin().getConfig().getInt("settings.antilogouttime");
        int amount = DataUtil.convertStrToInt(DataUtil.secondsToString(time));
        List<String> strings = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            strings.add("&c|");
        }
        for(int i = 0; i < (antilogouttime-amount); i++){
            strings.add("&8|");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : strings){
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static boolean sendMessage(final CommandSender player, final String text) {
        player.sendMessage(setHEX(text).replace(">>", "»").replace("<<", "«").replace("{o}", "\u2022").replace("{PREFIX}", setHEX("&8[&4<!>&8]")));
        return true;
    }

    public static boolean sendMessage(final CommandSender player, final List<String> text) {
        for (final String s : text) {
            player.sendMessage(setHEX(s).replace(">>", "»").replace("<<", "«").replace("{o}", "\u2022").replace("{NICK}", player.getName()));
        }
        return true;
    }

    public static boolean isPlayerHasItem(Player player, ItemStack is){
        for(ItemStack isIn : player.getInventory().getContents()){
            if(isIn != null){
                if(isIn.isSimilar(is)){
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Player> getListOfPlayersInArea(Location center, double radius){
        List<Player> playerList = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getWorld().getName().equals(center.getWorld().getName())){
                if(p.getLocation().distance(center) < radius){
                    playerList.add(p);
                }
            }
        }
        return playerList;
    }


    public static void setTeg(Entity entity, String tag2, int value)
    {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = null;
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt(tag2, value);
        nmsEntity.f(tag);
    }

    public static List<String> compareStringToList(String stringToList, String splitIndex){
        List<String> list = new ArrayList<>();
        String[] split = stringToList.split(splitIndex);
        Collections.addAll(list, split);
        return list;
    }

    public static String compareListToString(List<String> listToString, String splitIndex){
        StringBuilder strBuilder = new StringBuilder();
        for(String s : listToString){
            strBuilder.append(s).append(splitIndex);
        }
        return strBuilder.toString();
    }

    public static void dropToEquipment(List<ItemStack> items, Player player){
        int empty = 0;
        for(ItemStack is : player.getInventory()){
            if(is == null){
                empty++;
            }
        }
        if(empty >= items.size()){
            for(ItemStack is : items){
                player.getInventory().addItem(is);
            }
        }else{
            for(ItemStack is : items){
                player.getWorld().dropItemNaturally(player.getLocation(), is);
            }
        }
    }


    public static void spawnFireworks(Location location, int amount){
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();

        for(int i = 0;i<amount; i++){
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }

    public static List<Location> getChestsIn(World world, int max, int min) {
        List<Location> locations = new ArrayList<>();
        for (int x = min - (min * 2); x < max; x++) {
            for (int z = min - (min * 2); z < max; z++) {
                for (int y = 0; y < 256; y++) {
                    Block b = world.getBlockAt(x, y, z);
                    if (b.getType() == Material.CHEST) {
                        locations.add(b.getLocation());
                    }
                }
            }
        }
        return locations;
    }



    public static List<Location> sphere(Location loc, int radius, int height, boolean hollow, boolean sphere, int plusY) {
        List<Location> circleblocks = new ArrayList();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();

        for(int x = cx - radius; x <= cx + radius; ++x) {
            for(int z = cz - radius; z <= cz + radius; ++z) {
                for(int y = sphere ? cy - radius : cy; y < (sphere ? cy + radius : cy + height); ++y) {
                    double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
                    if (dist < (double)(radius * radius) && (!hollow || dist >= (double)((radius - 1) * (radius - 1)))) {
                        Location l = new Location(loc.getWorld(), (double)x, (double)(y + plusY), (double)z);
                        circleblocks.add(l);
                    }
                }
            }
        }

        return circleblocks;
    }

    public static boolean sendMessage(final Collection<? extends CommandSender> players, final String text) {
        for (final CommandSender cs : players) {
            cs.sendMessage(setHEX(text).replace(">>", "»").replace("<<", "«").replace("{o}", "\u2022"));
        }
        return true;
    }

    public static boolean sendMessageToOperators(final Collection<? extends CommandSender> players, final String text) {
        for (final CommandSender cs : players) {
            if(cs.isOp()){
                cs.sendMessage(setHEX(text).replace(">>", "»").replace("<<", "«").replace("{o}", "\u2022"));
            }
        }
        return true;
    }
}
