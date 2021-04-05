package pl.r3craft.rankreminder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{

    private final RankReminder plugin;

    public PlayerJoinListener(RankReminder plugin)
    {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                if(RankReminder.isPlayerInGroup(player, "mvip"))
                {
                    String result = Time.getTimeReminder(player, "mvip");
                    if(!result.equals("false"))
                    {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aR3Craft.pl&8] &7Twoja ranga &5MVIP &7kończy się za: &c" + result + "&7! &aPrzedłuż ją w sklepie! &6/sklep"));
                    }
                }
                else if(RankReminder.isPlayerInGroup(player, "vip"))
                {
                    String result = Time.getTimeReminder(player, "vip");
                    if(!result.equals("false"))
                    {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aR3Craft.pl&8] &7Twoja ranga &bVIP &7kończy się za: &c" + result + "&7! &aPrzedłuż ją w sklepie! &6/sklep"));
                    }
                }
            }
        }, 20);
    }
}
