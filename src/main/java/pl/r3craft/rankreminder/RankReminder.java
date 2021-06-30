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

    public MessagesManager messages;

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

        //Obtaining an instance of the LuckPerms API
        api = LuckPermsProvider.get();
        //Obtaining default config file
        this.saveDefaultConfig();

        this.messages = new MessagesManager(this);
        this.messages.saveDefaultConfig();
        this.messages.reloadMessages();

        //Informing about plugin start-up
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getMessages().getString("messages.console-start").replaceAll("%prefix%", messages.getMessages().getString("messages.prefix"))));
    }

    @Override
    public void onDisable()
    {
        //Informing about plugin stop
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getMessages().getString("messages.console-stop").replaceAll("%prefix%", messages.getMessages().getString("messages.prefix"))));
    }

    //rankreminder commands
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(label.equalsIgnoreCase("rankreminder"))
        {
            if(!sender.hasPermission("rankreminder.reload"))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getMessages().getString("messages.no-permission").replaceAll("%prefix%", messages.getMessages().getString("messages.prefix"))));
                return true;
            }
            if(args.length == 0)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getMessages().getString("messages.wrong-command").replaceAll("%prefix%", messages.getMessages().getString("messages.prefix"))));
                return true;
            }
            if(args.length > 0)
            {
                if(args[0].equalsIgnoreCase("reload"))//rankreminder reload command
                {
                    //Reloading the config
                    this.reloadConfig();
                    this.messages.reloadMessages();;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getMessages().getString("messages..reload").replaceAll("%prefix%", messages.getMessages().getString("messages.prefix"))));
                    return true;
                }
                else if(args[0].equalsIgnoreCase("help")) //rankreminder help command
                {
                    for(String help : messages.getMessages().getStringList("messages.help"))
                    {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help.replaceAll("%prefix%", messages.getMessages().getString("messages.prefix"))));
                    }
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



