package pl.r3craft.rankreminder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankTime implements CommandExecutor {

    private final RankReminder plugin;

    public RankTime(RankReminder plugin)
    {
        this.plugin = plugin;
        plugin.getCommand("ranga").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(label.equalsIgnoreCase("ranga") || label.equalsIgnoreCase("rankexpiration"))
        {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable()
            {
                @Override
                public void run()
                {

                    if(sender instanceof Player)
                    {
                        Player player = (Player) sender;
                        if(RankReminder.isPlayerInGroup(player, "mvip"))
                        {

                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aR3Craft.pl&8] &7Informacje o randze:"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8* &7Posiadana ranga: &5MVIP"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8* &7Ranga kończy się za: &a" + Time.getTime(player, "mvip")));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPrzedłuż ją w sklepie! &6/sklep"));
                        }
                        else if (RankReminder.isPlayerInGroup(player, "vip"))
                        {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aR3Craft.pl&8] &7Informacje o randze:"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8* &7Posiadana ranga: &bVIP"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8* &7Ranga kończy się za: &a" + Time.getTime(player, "vip")));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPrzedłuż ją w sklepie! &6/sklep"));
                        }
                        else
                        {
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
