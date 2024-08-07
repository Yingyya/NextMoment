package cn.yingyya.next.moment.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NextMomentUtils {

	public static void printToCommandSender(@NotNull CommandSender sender, @NotNull Plugin plugin) {
		PluginDescriptionFile description = plugin.getDescription();
		sender.sendMessage("Name: " + description.getName());
		sender.sendMessage("Description: " + description.getDescription());
		sender.sendMessage("Author: " + description.getAuthors());
		sender.sendMessage("Version: " + description.getVersion());
		sender.sendMessage("Website: " + description.getWebsite());
		sender.sendMessage("Libraries: " + description.getLibraries());
		sender.sendMessage("APIVersion: " + description.getAPIVersion());
	}

	@Nullable
	public static UUID getPlayerRealUUID(Player player) {
		String url = "https://api.mojang.com/users/profiles/minecraft/" + player.getName();
		String response = HttpUtils.sendGET(url);
		if (response == null) return null;
		JsonObject json = new Gson().fromJson(response, JsonObject.class);
		if (!json.has("id") || !json.has("name")) {
			return null;
		}
		if (!player.getName().equals(json.get("name").getAsString())) {
			return null;
		}
		return UUID.fromString(formatUUID(json.get("id").getAsString()));
	}

	public static String formatUUID(@NotNull String uuidString) {
		return uuidString.substring(0, 8) + "-" +
				uuidString.substring(8, 12) + "-" +
				uuidString.substring(12, 16) + "-" +
				uuidString.substring(16, 20) + "-" +
				uuidString.substring(20);
	}
}
