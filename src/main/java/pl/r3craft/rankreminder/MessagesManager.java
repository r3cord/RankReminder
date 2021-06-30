package pl.r3craft.rankreminder;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class MessagesManager {
    private RankReminder plugin;
    private FileConfiguration messages = null;
    private File messagesFile = null;

    public MessagesManager(RankReminder plugin) {
        this.plugin = plugin;
    }

    public void reloadMessages() {
        if(this.messagesFile == null)
            messagesFile = new File(this.plugin.getDataFolder(), "messages.yml");

        messages = YamlConfiguration.loadConfiguration(messagesFile);

        try {
            Reader defMessagesStream = new InputStreamReader(this.plugin.getResource("messages.yml"), "UTF-8");
            if(defMessagesStream != null){
                YamlConfiguration defMessages = YamlConfiguration.loadConfiguration(defMessagesStream);
                messages.setDefaults(defMessages);
            }
        } catch (UnsupportedEncodingException e) {
        }

    }

    public FileConfiguration getMessages() {
        if(messages == null)
            reloadMessages();
        return messages;
    }

    public void saveDefaultConfig() {
        if (messagesFile == null) {
            messagesFile = new File(this.plugin.getDataFolder(), "messages.yml");
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

}
