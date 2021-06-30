package pl.r3craft.rankreminder;

import java.time.Duration;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;

public class Time {

    //Static method which obtains the remaining time of the specified group
    public static String getTime(Player player, String rank)
    {
        //Obtaining the player instance from LP
        User user = RankReminder.api.getPlayerAdapter(Player.class).getUser(player);
        //Getting the time
        Duration x = user.getDistinctNodes().stream().filter(n -> n.getKey().contains("group." + rank) && n.hasExpiry()).map(Node::getExpiryDuration).findAny().orElse(null);
        if(x!=null)
        {
            long ms = x.toMillis(); //Changing the time from Duration to long
            return  DurationFormatUtils.formatDuration(ms, "d'd' H'h' m'm' s's'"); //Returning the time in String
        }
        else
            return RankReminder.mainPlugin.messages.getMessages().getString("messages.never");
    }

    //Static method which checks if the plugin should remind that the rank is expiring
    public static String getTimeReminder(Player player, String rank)
    {
        //Obtaining minDuration from the config file
        Duration minDuration = Duration.ofHours(RankReminder.mainPlugin.getConfig().getInt("hours"));
        //Obtaining the player instance from LP
        User user = RankReminder.api.getPlayerAdapter(Player.class).getUser(player);
        //Getting the time
        Duration x = user.getDistinctNodes().stream().filter(n -> n.getKey().contains("group." + rank) && n.hasExpiry()).map(Node::getExpiryDuration).findAny().orElse(null);
        if(x!=null && x.compareTo(minDuration) <= 0)
        {
            long ms = x.toMillis(); //Changing the time from Duration to long
            return  DurationFormatUtils.formatDuration(ms, "d'd' H'h' m'm' s's'"); //Returning the time in String
        }
        else
        {
            return "false";
        }

    }
}
