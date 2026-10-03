package com.github.lyokofirelyte.VariableTriggers.Utils;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class VTPaths {

	private static final String LEGACY_PLUGIN_PATH = "./plugins/VariableTriggers";

	private VTPaths() {
	}

	public static File dataFile(String relativePath) {
		return new File(getPlugin().getDataFolder(), relativePath);
	}

	public static String resolveLegacyPath(String path) {
		if (!path.equals(LEGACY_PLUGIN_PATH) && !path.startsWith(LEGACY_PLUGIN_PATH + "/")) {
			return path;
		}
		return getPlugin().getDataFolder().getPath() + path.substring(LEGACY_PLUGIN_PATH.length());
	}

	private static Plugin getPlugin() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("VariableTriggers3");
		if (plugin == null) {
			throw new IllegalStateException("VariableTriggers3 is not registered with Bukkit.");
		}
		return plugin;
	}
}
