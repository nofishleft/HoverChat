package nz.rishaan.hoverchat;

import nz.rishaan.hoverchat.Version;
import nz.rishaan.hoverchat.HoverChat;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUpdateManager {
  public static HoverChat hoverchat;
  public static Version plugin;
  public static Version config;
  //Represent updates as the version that the update was introduced,
  public static ArrayList <Version> updateVersions;
  public static Update[] updates;
  public static FileConfiguration fc;

  public static void init () {
    updateVersions = new ArrayList<Version>(2);
    updateVersions.add(new Version(0,0,1));
    updateVersions.add(new Version(0,0,2));
    plugin = hc.version;
    config = hc.getConfig().getString("configv");
    if (config == null) config = new Version(0,0,1);
    hc.getConfig().set("configv","0.0.1");
    hc.saveConfig();
  }

  public static void check ()  {
    System.out.println("HoverChat: Version \t" + plugin);
    System.out.println("HoverChat: Config version \t" + config);
    int status = Version.greater(plugin, config);
    if (status == 0) return;
    if (status < 0); //Config is ahead of plugin
    if (status > 1) {
      System.out.println("HoverChat: Config being updated...");
      fc = hoverchat.getConfig();
      int i = updateVersions.indexOf(config);
      int j = updateVersions.indexOf(plugin);
      for (int k = i; k<j; k++) {
        System.out.println("HoverChat: Config ==> \t" + config);
        update(k);
        config = updateVersions[k];
        fc.set("configv", config);
        fc.saveConfig();
      }
      System.out.println("HoverChat: Config updated to \t" + config);
    }
  }

  public void update(int k) {
    switch (k) {
      case 1:
        fc.set("chatlayout", fc.getString("chatlayout") + " ");
        break;
      /*case 0: First version no updates needed
        break;*/
      default:
        break;
    }
  }

}
