package tk.nofishleft.hoverchat;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import mkremins.fanciful.FancyMessage;

public class HoverChat extends JavaPlugin implements Listener {
	String permSee = "hoverchat.see";
	String permHave = "hoverchat.have";
	String layout = "User: %player_displayname%";
	String clayout = "<%player_displayname%>";
	String clayouts = "<";
	String clayoute = ">";
	String tooltipterm = "%player_displayname%";
	boolean b = true;
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Bukkit.getServer().getPluginManager().registerEvents(this, this);
			generateLayouts();
		} else {
			throw new RuntimeException("Could not find PlaceholderAPI! HoverChat currently does not work without it.");
		}
	}
	
	public void generateLayouts() {
		if (this.getConfig().get("tooltip") instanceof List<?>) {
			List<String> list = this.getConfig().getStringList("tooltip");
			String ilayout = "";
			for (String part: list) {
				if(ilayout == "") {
					ilayout = part;
				} else {
					ilayout = ilayout + "\n" + part;
				}
			}
			layout = ChatColor.translateAlternateColorCodes('&', ilayout);
		} else {
			throw new IllegalArgumentException("Invalid config.yml: tooltip is not a valid list");
		}
		
		tooltipterm = this.getConfig().getString("tooltipterm");
		int l = tooltipterm.length();
		clayout = this.getConfig().getString("chatlayout");
		int i = clayout.indexOf(tooltipterm);
			this.getServer().broadcastMessage(l + "," + i);
		if (i >=0) {
			clayouts = ChatColor.translateAlternateColorCodes('&', clayout.substring(0,i));
			clayoute = ChatColor.translateAlternateColorCodes('&', clayout.substring(i + l));
		} else {
			this.getServer().broadcastMessage("You just got yeeted");
		}
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player sender = e.getPlayer();
		String name = sender.getDisplayName();
		String tooltip = PlaceholderAPI.setPlaceholders(sender, layout);
		String message = e.getMessage();
		String cs = PlaceholderAPI.setPlaceholders(sender, clayouts);
		String ce = PlaceholderAPI.setPlaceholders(sender, clayoute);
		FancyMessage fm = new FancyMessage(cs).then(name).tooltip(tooltip).then(ce + " " + message);
		for (Player p:e.getRecipients()) {
			if(p.hasPermission(permSee)) fm.send(p);
		}
		e.setCancelled(true);
	}
	
}
