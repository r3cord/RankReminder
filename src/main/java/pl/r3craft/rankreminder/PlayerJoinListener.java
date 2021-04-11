package pl.r3craft.rankreminder;

import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    //Obtaining RankReminder class
    private final RankReminder plugin;

    //Constructor
    public PlayerJoinListener(RankReminder plugin)
    {
        this.plugin = plugin;
        //Register join listener
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        //Running task asynchronously
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                //Getting all ranks from config file
                for(String rank : plugin.getConfig().getStringList("ranks"))
                {
                    //Checking if player is in groups from config file
                    if(RankReminder.isPlayerInGroup(player, rank))
                    {
                        //Calling getTimeReminder method which checks if we should remind the player about his expiration time of the current group
                        String result = Time.getTimeReminder(player, rank);
                        if(!result.equals("false"))
                        {
                            //Obtaining meta data of the current group
                            CachedMetaData metaData = RankReminder.api.getGroupManager().getGroup(rank).getCachedData().getMetaData();
                            //Obtaining prefix of the current group
                            String prefix = metaData.getPrefix();
                            //Sending the message to the player
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aR3Craft.pl&8] &7Twoja ranga &5"+prefix+" &7kończy się za: &c" + result + "&7! &aPrzedłuż ją w sklepie! &6/sklep"));
                        }
                    }
                }
            }
        }, 40);
    }
}
