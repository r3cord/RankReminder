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
                                for(String rankInfo : plugin.messages.getMessages().getStringList("messages.rankInfo"))
                                {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', rankInfo.replaceAll("%prefix%", plugin.messages.getMessages().getString("messages.prefix")).replaceAll("%rankPrefix%", prefix).replaceAll("%time%", Time.getTime(player, rank))));
                                }
                            }
                        }
                        if(!haveRank) //If the player is not in any group from the config file
                        {
                            for(String noRankInfo : plugin.messages.getMessages().getStringList("messages.noRankInfo"))
                            {
                                //Send messages
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', noRankInfo.replaceAll("%prefix%", plugin.messages.getMessages().getString("messages.prefix"))));
                            }
                        }
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messages.getMessages().getString("messages.only-player").replaceAll("%prefix%", plugin.messages.getMessages().getString("messages.prefix"))));
                    }
                }
            });
            return true;
        }
        else
            return false;
    }
}
