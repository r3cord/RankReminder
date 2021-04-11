package pl.r3craft.rankreminder;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class RankReminder extends JavaPlugin
{
    //Variable for an instance of the LuckPerms API
    public static LuckPerms api;

    public static RankReminder mainPlugin;

    @Override
    public void onEnable()
    {
        //Calling the constructor of PlayerJoinListener
        new PlayerJoinListener(this);
        //Calling the constructor of RanTime
        new RankTime(this);
        //Obtaining this (RankReminder) class
        mainPlugin = this;

        //Informing about plugin start-up
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("[R3Craft.pl] PLugin Informacyjny uruchomiony!");

        //Obtaining an instance of the LuckPerms API
        api = LuckPermsProvider.get();
        //Obtaining default config file
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable()
    {
        //Informing about plugin stop
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("[R3Craft.pl] PLugin Informacyjny wyłączony!");
    }

    //rankreminder commands
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(label.equalsIgnoreCase("rankreminder"))
        {
            if(!sender.hasPermission("rankreminder.reload"))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to run this command!"));
                return true;
            }
            if(args.length == 0)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cWrong command! Type &7/rankreminder help &cfor help"));
                return true;
            }
            if(args.length > 0)
            {
                if(args[0].equalsIgnoreCase("reload"))//rankreminder reload command
                {
                    //Reloading the config
                    this.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfiguration files reloaded!"));
                    return true;
                }
                else if(args[0].equalsIgnoreCase("help")) //rankreminder help command
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6RankReminder - help"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/rankreminder reload &7- Reloads the config"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/ranga &7- shows info about your rank "));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/rankexpiration &7- shows info about your rank "));
                    return true;
                }
            }
        }
        return false;
    }

    //Static method used for checking if the player is in the specified group
    public static boolean isPlayerInGroup(Player player, String group)
    {
        return player.hasPermission("group." + group);
    }

}



