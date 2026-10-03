package com.github.lyokofirelyte.VariableTriggers.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;

import fr.mrmicky.fastboard.FastBoard;

public final class VTScoreboardManager implements Listener {

	private static final int MAX_LINES = 15;
	private final Map<UUID, BoardState> boards = new HashMap<>();

	public void setTitle(Player player, String title) {
		BoardState state = getOrCreate(player);
		state.board.updateTitle(VTUtils.AS(title));
	}

	public boolean setLine(Player player, int line, String text) {
		if (line < 1 || line > MAX_LINES) {
			return false;
		}

		BoardState state = getOrCreate(player);
		state.lines.put(line, VTUtils.AS(text));
		updateLines(state);
		return true;
	}

	public void remove(Player player) {
		BoardState state = boards.remove(player.getUniqueId());
		if (state != null) {
			state.board.delete();
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		remove(event.getPlayer());
	}

	public void shutdown() {
		for (BoardState state : boards.values()) {
			state.board.delete();
		}
		boards.clear();
	}

	private BoardState getOrCreate(Player player) {
		return boards.computeIfAbsent(player.getUniqueId(), ignored -> {
			BoardState state = new BoardState(new FastBoard(player));
			state.board.updateTitle(VTUtils.AS("&eVTV3"));
			return state;
		});
	}

	private void updateLines(BoardState state) {
		List<String> lines = new ArrayList<>();
		for (int i = 1; i <= state.lines.lastKey(); i++) {
			lines.add(state.lines.getOrDefault(i, ""));
		}
		state.board.updateLines(lines.toArray(new String[0]));
	}

	private static final class BoardState {
		private final FastBoard board;
		private final TreeMap<Integer, String> lines = new TreeMap<>();

		private BoardState(FastBoard board) {
			this.board = board;
		}
	}
}
