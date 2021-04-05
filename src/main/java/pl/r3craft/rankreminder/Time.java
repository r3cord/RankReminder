package pl.r3craft.rankreminder;

import java.time.Duration;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;

public class Time {

    public static String getTime(Player player, String rank)
    {
        User user = RankReminder.api.getPlayerAdapter(Player.class).getUser(player);
        Duration x = user.getDistinctNodes().stream().filter(n -> n.getKey().contains("group." + rank) && n.hasExpiry()).map(Node::getExpiryDuration).findAny().orElse(null);
        if(x!=null)
        {
            long ms = x.toMillis();
            return  DurationFormatUtils.formatDuration(ms, "d'd' H'h' m'm' s's'");
        }
        else
            return "nigdy";
    }

    public static String getTimeReminder(Player player, String rank)
    {
        Duration minDuration = Duration.ofDays(7);
        User user = RankReminder.api.getPlayerAdapter(Player.class).getUser(player);
        Duration x = user.getDistinctNodes().stream().filter(n -> n.getKey().contains("group." + rank) && n.hasExpiry()).map(Node::getExpiryDuration).findAny().orElse(null);
        if(x!=null && x.compareTo(minDuration) <= 0)
        {
            long ms = x.toMillis();
            return  DurationFormatUtils.formatDuration(ms, "d'd' H'h' m'm' s's'");
        }
        else
        {
            return "false";
        }

    }
}
