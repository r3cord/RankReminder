package pl.r3craft.rankreminder;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class RankReminder extends JavaPlugin
{
    public static LuckPerms api;

    @Override
    public void onEnable()
    {
        new PlayerJoinListener(this);
        new RankTime(this);
        //this.getCommand("ranga").setExecutor(new RankTime());
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("[R3Craft.pl] PLugin Informacyjny uruchomiony!");
        api = LuckPermsProvider.get();
    }

    @Override
    public void onDisable()
    {
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("[R3Craft.pl] PLugin Informacyjny wyłączony!");
    }

    public static boolean isPlayerInGroup(Player player, String group)
    {
        return player.hasPermission("group." + group);
    }

}



