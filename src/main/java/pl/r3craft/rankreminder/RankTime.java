package pl.r3craft.rankreminder;

import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankTime implements CommandExecutor {

    private final RankReminder plugin;

    //Constructor
    public RankTime(RankReminder plugin)
    {
        //Registering the command
        this.plugin = plugin;
        plugin.getCommand("ranga").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(label.equalsIgnoreCase("ranga") || label.equalsIgnoreCase("rankexpiration"))
        {
            //Running task asynchronously
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable()
            {
                @Override
                public void run()
                {

                    if(sender instanceof Player)
                    {
                        Player player = (Player) sender;
                        boolean haveRank = false;
                        for(String rank : plugin.getConfig().getStringList("ranks"))
                        {
                            if(RankReminder.isPlayerInGroup(player, rank))
                            {
                                haveRank = true;
                                //Obtaining meta data of the current group
                                CachedMetaData metaData = RankReminder.api.getGroupManager().getGroup(rank).getCachedData().getMetaData();
                                String prefix = metaData.getPrefix();

                                //Send messages
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aR3Craft.pl&8] &7Informacje o randze:"));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8* &7Posiadana ranga: " + prefix));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8* &7Ranga kończy się za: &a" + Time.getTime(player, rank)));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPrzedłuż ją w sklepie! &6/sklep"));
                            }
                        }
                        if(!haveRank) //If the player is not in any group from the config file
                        {
                            //Send messages
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aR3Craft.pl&8] &7Informacje o randze:"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8* &cNie posiadasz żadnej zakupionej rangi!"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aZmień to kupując ją w sklepie! &6/sklep"));
                        }
                    }
                    else
                    {
                        sender.sendMessage("Tej komendy nie można używać z poziomu konsoli");
                    }
                }
            });
            return true;
        }
        else
            return false;
    }
}
