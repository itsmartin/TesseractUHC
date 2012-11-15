package com.martinbrook.tesseractuhc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class UhcMatch {

	private World startingWorld;
	private HashMap<Integer, UhcStartPoint> startPoints = new HashMap<Integer, UhcStartPoint>();
	private Location lastNotifierLocation;
	private Location lastDeathLocation;
	private Location lastEventLocation;
	private Location lastLogoutLocation;
	private int countdown = 0;
	private String countdownEvent;
	private String countdownEndMessage;

	private ArrayList<String> chatScript;
	private Boolean chatMuted = false;
	private CountdownType countdownType;
	private Boolean permaday = false;
	private int permadayTaskId;
	private Boolean deathban = false;
	
	private ArrayList<UhcStartPoint> availableStartPoints = new ArrayList<UhcStartPoint>();
	private Boolean launchingPlayers = false;
	private Boolean matchStarted = false;
	private HashMap<String, UhcPlayer> uhcPlayers = new HashMap<String, UhcPlayer>(32);
	public static String DEFAULT_MATCHDATA_FILE = "matchdata.yml";
	private int playersInMatch = 0;
	private int nextRadius;
	private Calendar matchStartTime;
	private int matchTimer = -1;
	private boolean matchEnded = false;
	private ArrayList<Location> calculatedStarts = null;
	private boolean pvp = false;
	private int spawnKeeperTask = -1;
	private YamlConfiguration md; // Match data
	private TesseractUHC plugin;
	private Server server;
	private Configuration defaults;
	
	public UhcMatch(TesseractUHC plugin, World startingWorld, Configuration defaults) {

		this.startingWorld = startingWorld;
		this.plugin = plugin;
		this.server = plugin.getServer();
		this.defaults = defaults;
		
		this.initialise();
		
	}
	
	private void initialise() {
		loadData();
		setPermaday(true);
		setPVP(false);
		setVanish();
		setDeathban(md.getBoolean("deathban"));
		enableSpawnKeeper();
	}
	
	
	
	/**
	 * Load match data from the default file. If it does not exist, load defaults.
	 */
	public void loadData() { 
		this.clearData();
		
		try {
			md = YamlConfiguration.loadConfiguration(UhcUtil.getDataFile(startingWorld.getWorldFolder(), DEFAULT_MATCHDATA_FILE, true));
			this.loadStartPoints();
		} catch (Exception e) {
			this.loadDefaultData();
		}
		
	}
	
	
	/**
	 * Set up a default matchdata object
	 */
	private void loadDefaultData() {
		md = new YamlConfiguration();
		md.addDefaults(defaults);
	}
	
	/**
	 * Create UhcStartPoint objects from the locations in matchdata field (md)
	 */
	private void loadStartPoints() {
		List<String> startData = md.getStringList("starts");
		for (String startDataEntry : startData) {
			String[] data = startDataEntry.split(",");
			if (data.length == 4) {
				try {
					int n = Integer.parseInt(data[0]);
					double x = Double.parseDouble(data[1]);
					double y = Double.parseDouble(data[2]);
					double z = Double.parseDouble(data[3]);
					UhcStartPoint sp = createStartPoint (n, startingWorld, x, y, z);
					if (sp == null) {
						adminBroadcast("Duplicate start point: " + n);

					}
				} catch (NumberFormatException e) {
					adminBroadcast("Bad start point definition in match data file: " + startDataEntry);
				}

			} else {
				adminBroadcast("Bad start point definition in match data file: " + startDataEntry);
			}
		}

	}
	
	/**
	 * Update matchdata (md) from the existing UhcStartPoints.
	 */
	private void saveStartPoints() {
		ArrayList<String> startData = new ArrayList<String>();
		for (UhcStartPoint sp : startPoints.values()) {
			startData.add(sp.getNumber() + "," + sp.getX() + "," + sp.getY() + "," + sp.getZ());
		}
		
		md.set("starts",startData);

	}

	/**
	 * Save start points to the default file
	 * 
	 * @return Whether the operation succeeded
	 */
	public Boolean saveData() {
		this.saveStartPoints();
		
		try {
			md.save(UhcUtil.getDataFile(UhcUtil.getWorldFolder(), DEFAULT_MATCHDATA_FILE, false));
		} catch (IOException e) {
			return false;
		}
		return true;
	}


	/**
	 * Clear all start points
	 */
	public void clearData() {
		startPoints.clear();
		availableStartPoints.clear();
	
	}

	private void adminBroadcast(String string) {
		broadcast(string,Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
	}
	
	private void broadcast(String string) {
		broadcast(string,Server.BROADCAST_CHANNEL_USERS);
	}

	private void broadcast(String string, String permission) {
		server.broadcast(string, permission);
	}
	
	/**
	 * Set time to midday, to keep permaday in effect.
	 */
	private void keepPermaday() {
		this.startingWorld.setTime(6000);
	}

	/**
	 * Enables / disables PVP on overworld
	 * 
	 * @param pvp Whether PVP is to be allowed
	 */
	public void setPVP(boolean pvp) {
		this.pvp = pvp;
		startingWorld.setPVP(pvp);

		adminBroadcast(TesseractUHC.OK_COLOR + "PVP has been " + (pvp ? "enabled" : "disabled") + "!");
	
	}
	
	/**
	 * @return Whether PVP is enabled
	 */
	public boolean getPVP() {
		return this.pvp;
	}

	/**
	 * Enables / disables permaday
	 * 
	 * @param p whether permaday is to be on or off
	 */
	public void setPermaday(boolean p) {
		if (p == permaday) return;
		
		this.permaday=p;
	
		adminBroadcast(TesseractUHC.OK_COLOR + "Permaday has been " + (permaday ? "enabled" : "disabled") + "!");
		
		
		if (permaday) {
			startingWorld.setTime(6000);
			permadayTaskId = server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					keepPermaday();
				}
			}, 1200L, 1200L);
			
		} else {
			server.getScheduler().cancelTask(permadayTaskId);
		}
	}
	

	/**
	 * @return Whether permaday is enabled
	 */
	public boolean getPermaday() {
		return this.permaday;
	}

	/**
	 * Check whether deathban is in effect
	 * 
	 * @return Whether deathban is enabled
	 */
	public boolean getDeathban() {
		return deathban;
	}

	/**
	 * Set deathban on/off
	 * 
	 * @param d Whether deathban is to be enabled
	 */
	public void setDeathban(boolean d) {
		this.deathban = d;
		adminBroadcast(TesseractUHC.OK_COLOR + "Deathban has been " + (deathban ? "enabled" : "disabled") + "!");
	}

	/**
	 * Try to find a start point from a user-provided search string.
	 * 
	 * @param searchParam The string to search for - a player name, or a start number may be sent
	 * @return The start point, or null if not found.
	 */
	public UhcStartPoint findStartPoint(String searchParam) {
		UhcPlayer up = this.getUhcPlayer(searchParam);
		if (up != null) {
			// Argument matches a player
			return up.getStartPoint();
			
		} else {
			try {
				int i = Integer.parseInt(searchParam);
				return startPoints.get(i);
			} catch (Exception e) {
				return null;
			}
		}
		
	}

	/**
	 * Set a death location for teleporters
	 * 
	 * @param l The location to be stored
	 */
	public void setLastDeathLocation(Location l) {
		lastDeathLocation = l;
		lastEventLocation = l;
	}

	/**
	 * Set a notification location for teleporters
	 * 
	 * @param l The location to be stored
	 */
	public void setLastNotifierLocation(Location l) {
		lastNotifierLocation = l;
		lastEventLocation = l;
	}

	/**
	 * Set a logout location for teleporters
	 * 
	 * @param l The location to be stored
	 */
	public void setLastLogoutLocation(Location l) {
		lastLogoutLocation = l;
	}
	

	
	/**
	 * Remove all hostile mobs in the overworld
	 */
	public void butcherHostile() {
		for (Entity entity : startingWorld.getEntitiesByClass(LivingEntity.class)) {
			if (entity instanceof Monster || entity instanceof MagmaCube || entity instanceof Slime || entity instanceof EnderDragon
					|| entity instanceof Ghast)
				entity.remove();
		}
	}
	
	/**
	 * Heal, feed, clear XP, inventory and potion effects of the given player
	 * 
	 * @param p The player to be renewed
	 */
	public void renew(Player p) {
		heal(p);
		feed(p);
		clearXP(p);
		clearPotionEffects(p);
		clearInventory(p);
	}


	/**
	 * Heal the given player
	 * 
	 * @param p The player to be healed
	 */
	public void heal(Player p) {
		p.setHealth(20);
	}

	/**
	 * Feed the given player
	 * 
	 * @param p The player to be fed
	 */
	public void feed(Player p) {
		p.setFoodLevel(20);
		p.setExhaustion(0.0F);
		p.setSaturation(5.0F);
	}

	/**
	 * Reset XP of the given player
	 * 
	 * @param p The player
	 */
	public void clearXP(Player p) {
		p.setTotalExperience(0);
		p.setExp(0);
		p.setLevel(0);
	}

	/**
	 * Clear potion effects of the given player
	 * 
	 * @param p The player
	 */
	public void clearPotionEffects(Player p) {
		for (PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
	}

	/**
	 * Clear inventory and ender chest of the given player
	 * 
	 * @param player
	 */
	public void clearInventory(Player player) {
		PlayerInventory i = player.getInventory();
		i.clear();
		i.setHelmet(null);
		i.setChestplate(null);
		i.setLeggings(null);
		i.setBoots(null);
		
		player.getEnderChest().clear();
		
	}
	
	/**
	 * Start the match
	 * 
	 * Butcher hostile mobs, turn off permaday, turn on PVP, put all players in survival and reset all players.
	 */
	public void startMatch() {
		matchStarted = true;
		startingWorld.setTime(0);
		butcherHostile();
		for (Player p : server.getOnlinePlayers()) {
			if (p.getGameMode() != GameMode.CREATIVE) {
				feed(p);
				clearXP(p);
				clearPotionEffects(p);
				heal(p);
				p.setGameMode(GameMode.SURVIVAL);
			}
		}
		setPermaday(false);
		setPVP(true);
		startMatchTimer();
		setVanish();
	}
	
	/**
	 * End the match
	 * 
	 * Announce the total match duration
	 */
	public void endMatch() {
		announceMatchTime(true);
		stopMatchTimer();
		matchEnded = true;
		// Put all players into creative
		for (Player p : server.getOnlinePlayers()) p.setGameMode(GameMode.CREATIVE);
		setVanish();

	}
	
	/**
	 * Initiates a countdown
	 * 
	 * @param countdownLength the number of seconds to count down
	 * @param eventName The name of the event to be announced
	 * @param endMessage The message to display at the end of the countdown
	 * @return Whether the countdown was started
	 */
	public boolean startCountdown(Integer countdownLength, String eventName, String endMessage, CountdownType type) {
		if (countdown>0) return false;
		countdown = countdownLength;
		countdownEvent = eventName;
		countdownEndMessage = endMessage;
		countdownType = type;
		countdown();
		return true;
	}
	
	/**
	 * Continues the countdown in progress
	 */
	private void countdown() {
		if (countdown < 0)
			return;
		
		if (countdown == 0) {
			if (countdownType == CountdownType.MATCH) {
				this.startMatch();
			} else if (countdownType == CountdownType.PVP) {
				this.setPVP(true);
			} else if (countdownType == CountdownType.WORLD_REDUCE) {
				if (UhcUtil.setWorldRadius(startingWorld,nextRadius)) {
					adminBroadcast(TesseractUHC.OK_COLOR + "Border reduced to " + nextRadius);
				} else {
					adminBroadcast(TesseractUHC.ERROR_COLOR + "Unable to reduce border. Is WorldBorder installed?");
				}
			}
			broadcast(TesseractUHC.MAIN_COLOR + countdownEndMessage);
			return;
		}
		
		if (countdown >= 60) {
			if (countdown % 60 == 0) {
				int minutes = countdown / 60;
				broadcast(ChatColor.RED + countdownEvent + " in " + minutes + " minute" + (minutes == 1? "":"s"));
			}
		} else if (countdown % 15 == 0) {
			broadcast(ChatColor.RED + countdownEvent + " in " + countdown + " seconds");
		} else if (countdown <= 5) { 
			broadcast(ChatColor.RED + "" + countdown + "...");
		}
		
		countdown--;
		server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				countdown();
			}
		}, 20L);
	}
	
	/**
	 * Cancels a running countdown
	 */
	public void cancelCountdown() {
		countdown = -1;
	}
	
	/**
	 * Starts the match timer
	 */
	private void startMatchTimer() {
		matchStartTime = Calendar.getInstance();
		matchTimer = server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				announceMatchTime(false);
			}
		}, 36000L, 36000L);
	}
	
	/**
	 * Stops the match timer
	 */
	private void stopMatchTimer() {
		if (matchTimer != -1) {
			server.getScheduler().cancelTask(matchTimer);
		}
	}
	
	/**
	 * Announce the current match time in chat
	 * 
	 * @param precise Whether to give a precise time (00:00:00) instead of (xx minutes)
	 */
	public void announceMatchTime(boolean precise) {
		broadcast(TesseractUHC.MAIN_COLOR + "Match time: " + TesseractUHC.SIDE_COLOR + UhcUtil.formatDuration(matchStartTime, Calendar.getInstance(), precise));
	}
	

	/**
	 * Plays a chat script
	 * 
	 * @param filename The file to read the chat script from
	 * @param muteChat Whether other chat should be muted
	 */
	public void playChatScript(String filename, boolean muteChat) {
		if (muteChat) this.setChatMuted(true);
		chatScript = UhcUtil.readFile(filename);
		if (chatScript != null)
			continueChatScript();
	}
	
	/**
	 * Output next line of current chat script, unmuting the chat if it's finished.
	 */
	private void continueChatScript() {
		broadcast(ChatColor.GREEN + chatScript.get(0));
		chatScript.remove(0);
		if (chatScript.size() > 0) {
			server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					continueChatScript();
				}
			}, 30L);
		} else {
			this.setChatMuted(false);
			chatScript = null;
		}
		
	}
	
	/**
	 * Get all players currently registered with the game
	 * 
	 * @return All registered players
	 */
	public Collection<UhcPlayer> getUhcPlayers() {
		return uhcPlayers.values();
	}
	
	
	/**
	 * Create a new player and add them to the game
	 * 
	 * @param name The player's name
	 * @param sp The player's start point
	 * @return The newly created player, or null if they already existed
	 */
	public UhcPlayer createUhcPlayer(String name, UhcStartPoint sp) {
		// Fail if player exists
		if (existsUhcPlayer(name)) return null;
		
		UhcPlayer up = new UhcPlayer(name, sp);
		uhcPlayers.put(name.toLowerCase(), up);
		return up;
	}
	
	/**
	 * Check if a player exists
	 * 
	 * @param name Player name to check (case insensitive)
	 * @return Whether the player exists
	 */
	public boolean existsUhcPlayer(String name) {
		return uhcPlayers.containsKey(name.toLowerCase());
	}
	
	/**
	 * Get a specific UhcPlayer by name
	 * 
	 * @param name The exact name of the player to be found  (case insensitive)
	 * @return The UhcPlayer, or null if not found
	 */
	public UhcPlayer getUhcPlayer(String name) {
		return uhcPlayers.get(name.toLowerCase());
	}

	
	/**
	 * Get a specific UhcPlayer matching the given Bukkit Player
	 * 
	 * @param playerToGet The Player to look for
	 * @return The UhcPlayer, or null if not found
	 */
	public UhcPlayer getUhcPlayer(Player playerToGet) {
		return getUhcPlayer(playerToGet.getName());
	}
	
	/**
	 * Add the supplied player and assign them a start point
	 * 
	 * @param p The player to add
	 * @return success or failure
	 */
	public boolean addPlayer(Player p) {
		// Check that we are not dealing with an op here
		if (p.isOp()) return false;
		
		// Check that there are available start points
		if (availableStartPoints.size() < 1) return false;
		
		// Check that the player doesn't exist 
		if (existsUhcPlayer(p.getName())) return false;
		
		// Get them a start point
		Random rand = new Random();
		UhcStartPoint start = availableStartPoints.remove(rand.nextInt(availableStartPoints.size()));
		
		// Create the player
		UhcPlayer up = createUhcPlayer(p.getName(), start);
		start.setUhcPlayer(up);

		playersInMatch++;
		
		start.makeSign();

		return true;
	}

	/**
	 * Launch the specified player only
	 * 
	 * @param p The UhcPlayer to be launched
	 * @return success or failure
	 */
	public boolean launch(UhcPlayer up) {

		// If player already launched, ignore
		if (up.isLaunched()) return false;
		
		// Get the player
		Player p = server.getPlayer(up.getName());
		
		// If player not online, return
		if (p == null) return false;
		
		// Teleport the player to the start point
		p.setGameMode(GameMode.ADVENTURE);
		plugin.doTeleport(p, up.getStartPoint().getLocation());
		renew(p);
		
		up.setLaunched(true);

		return true;


		
	}
	
	/**
	 * Re-teleport the specified player
	 * 
	 * @param p The player to be relaunched
	 */
	public boolean relaunch(Player p) {
		UhcPlayer up = getUhcPlayer(p);
		if (up == null) return false;
		
		return p.teleport(up.getStartPoint().getLocation());
	}
	
	/**
	 * Remove the given player, removing them from the match and freeing up a start point.
	 * 
	 * The player will be teleported back to spawn if they are still on the server
	 * 
	 * @param name The player to be removed
	 * @return The removed player, or null if failed
	 */
	public UhcPlayer removePlayer(String name) {
		UhcPlayer up = uhcPlayers.remove(name);
		Player p = server.getPlayer(name);
		
		if (up != null) {
			// Free up the start point
			UhcStartPoint sp = up.getStartPoint();
			if (sp != null) {
				sp.setUhcPlayer(null);
				playersInMatch--;
				sp.makeSign();
				availableStartPoints.add(sp);
				if (matchStarted) {
					broadcast(ChatColor.GOLD + up.getName() + " has been removed from the match");
					announcePlayersRemaining();
				}
			}
		}
		
		// Teleport the player if possible
		if (p != null) plugin.doTeleport(p,startingWorld.getSpawnLocation());
		
		return up;
	}


	/**
	 * Start the launching phase, and launch all players who have been added to the game
	 */
	public void launchAll() {
		launchingPlayers=true;
		disableSpawnKeeper();
		setVanish(); // Update vanish status
		for(UhcPlayer up : getUhcPlayers()) launch(up);
	}
	
	
	public void enableSpawnKeeper() {
		spawnKeeperTask = server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				runSpawnKeeper();
			}
		}, 20L, 20L);
	}

	public void disableSpawnKeeper() {
		server.getScheduler().cancelTask(spawnKeeperTask);
	}
	
	public void runSpawnKeeper() {
		for (Player p : server.getOnlinePlayers()) {
			if (!p.isOp() && p.getLocation().getY() < 128) {
				plugin.doTeleport(p, startingWorld.getSpawnLocation());
			}
		}
	}
	
	/**
	 * Create a new start point at a given location
	 * 
	 * @param number The start point's number
	 * @param l The location of the start point
	 * @return The created start point
	 */
	public UhcStartPoint createStartPoint(int number, Location l) {
		// Check there is not already a start point with this number		
		if (startPoints.containsKey(number))
			return null;
		
		UhcStartPoint sp = new UhcStartPoint(number, l);
		startPoints.put(number,  sp);
		availableStartPoints.add(sp);
		
		return sp;
	}
	
	/**
	 * Create a new start point at a given location
	 * 
	 * @param number The start point's number
	 * @param world The world to create the start point
	 * @param x x coordinate of the start point
	 * @param y y coordinate of the start point
	 * @param z z coordinate of the start point
	 * @return The created start point
	 */
	public UhcStartPoint createStartPoint(int number, World world, Double x, Double y, Double z) {
		return createStartPoint(number, new Location(world, x, y, z));
	}
	
	/**
	 * Create a new start point at a given location, giving it the next available number
	 * 
	 * @param l The location of the start point
	 * @return The created start point
	 */
	public UhcStartPoint createStartPoint(Location l) {
		return createStartPoint(getNextAvailableStartNumber(), l);
	}

	/**
	 * Create a new start point at a given location, giving it the next available number
	 * 
	 * @param x x coordinate of the start point
	 * @param y y coordinate of the start point
	 * @param z z coordinate of the start point
	 * @return The created start point
	 */
	public UhcStartPoint createStartPoint(Double x, Double y, Double z) {
		return createStartPoint(new Location(startingWorld, x, y, z));
	}
		
	/**
	 * Determine the lowest unused start number
	 * 
	 * @return The lowest available start point number
	 */
	public int getNextAvailableStartNumber() {
		int n = 1;
		while (startPoints.containsKey(n))
			n++;
		return n;
	}
	

	

	
	/**
	 * @return Whether chat is currently muted
	 */
	public boolean isChatMuted() {
		return chatMuted;
	}
	
	/**
	 * Mute or unmute chat
	 * 
	 * @param muted Status to be set
	 */
	public void setChatMuted(Boolean muted) {
		chatMuted = muted;
	}

	/**
	 * @return Whether player launching has started yet
	 */
	public Boolean getLaunchingPlayers() {
		return launchingPlayers;
	}

	/**
	 * Get the bonus items to be dropped by a PVP-killed player in addition to their inventory
	 * 
	 * @return The ItemStack to be dropped
	 */
	public ItemStack getKillerBonus() {
		if (!md.getBoolean("killerbonus.enabled")) return null;
		
		if (md.getInt("killerbonus.id") != 0 && md.getInt("killerbonus.quantity") != 0)
			return new ItemStack(md.getInt("killerbonus.id"), md.getInt("killerbonus.quantity"));
		else
			return null;
	}

	/**
	 * Apply the mining fatigue game mechanic
	 * 
	 * Players who mine stone below a certain depth increase their hunger or take damage
	 * 
	 * @param player The player to act upon
	 * @param blockY The Y coordinate of the mined block
	 */
	public void doMiningFatigue(Player player, int blockY) {
		if (!md.getBoolean("miningfatigue.enabled")) return;
		if (blockY > md.getInt("miningfatigue.maxy")) return;
		UhcPlayer up = getUhcPlayer(player);
		if (up == null) return;
		up.incMineCount();
		if (up.getMineCount() >= md.getInt("miningfatigue.blocks")) {
			up.resetMineCount();
			if (md.getInt("miningfatigue.exhaustion") > 0) {
				// Increase player's exhaustion by specified amount
				player.setExhaustion(player.getExhaustion() + md.getInt("miningfatigue.exhaustion"));
			}
			if (md.getInt("miningfatigue.damage") > 0) {
				// Apply specified damage to player
				player.damage(md.getInt("miningfatigue.damage"));
			}
		}

		
	}

	/**
	 * @return Whether the game is underway
	 */
	public Boolean isMatchStarted() {
		return matchStarted;
	}

	/**
	 * @return The number of players still in the match
	 */
	public int getPlayersInMatch() {
		return playersInMatch;
	}

	/**
	 * Process the death of a player
	 * 
	 * @param up The player who died
	 */
	public void handlePlayerDeath(UhcPlayer up) {
		if (up.isDead()) return;
		up.setDead(true);
		playersInMatch--;
		server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				announcePlayersRemaining();
			}
		});
	}

	/**
	 * Publicly announce how many players are still in the match 
	 */
	private void announcePlayersRemaining() {
		// Make no announcement if final player was killed
		if (playersInMatch < 1) return;
		
		String message;
		if (playersInMatch == 1) {
			message = getSurvivingPlayerList() + " is the winner!";
			endMatch();
		} else if (playersInMatch <= 4) {
			message = playersInMatch + " players remain: " + getSurvivingPlayerList();
		} else {
			message = playersInMatch + " players remain";
		}
		
		broadcast(TesseractUHC.OK_COLOR + message);
	}

	/**
	 * Get a list of surviving players
	 * 
	 * @return A comma-separated list of surviving players
	 */
	private String getSurvivingPlayerList() {
		String survivors = "";
		
		for (UhcPlayer up : getUhcPlayers())
			if (up.isLaunched() && !up.isDead()) survivors += up.getName() + ", ";;
		
		if (survivors.length() > 2)
			survivors = survivors.substring(0,survivors.length()-2);
		
		return survivors;
		
	}
	


	/**
	 * @return Whether the match is over
	 */
	public boolean isMatchEnded() {
		return matchEnded;
	}
	
	/**
	 * @return Whether we are in the launch or match period
	 */
	public boolean inLaunchOrMatch() {
		return (getLaunchingPlayers() && !isMatchEnded());
	}

	/**
	 * Show a spectator the contents of a player's inventory.
	 * 
	 * @param spectator The player who is asking to see the inventory
	 * @param player The player being observed
	 */
	public boolean showInventory(Player spectator, Player player) {

		Inventory i = getInventoryView(player);
		if (i == null) return false;
		
		spectator.openInventory(i);
		return true;
	}

	/**
	 * Gets a copy of a player's current inventory, including armor/health/hunger details.
	 *
	 * @author AuthorBlues
	 * @param player The player to be viewed
	 * @return inventory The player's inventory
	 *
	 */
	public Inventory getInventoryView(Player player)
	{

		PlayerInventory pInventory = player.getInventory();
		Inventory inventoryView = Bukkit.getServer().createInventory(null,
			pInventory.getSize() + 9, player.getDisplayName() + "'s Inventory");

		ItemStack[] oldContents = pInventory.getContents();
		ItemStack[] newContents = inventoryView.getContents();

		for (int i = 0; i < oldContents.length; ++i)
			if (oldContents[i] != null) newContents[i] = oldContents[i];

		newContents[oldContents.length + 0] = pInventory.getHelmet();
		newContents[oldContents.length + 1] = pInventory.getChestplate();
		newContents[oldContents.length + 2] = pInventory.getLeggings();
		newContents[oldContents.length + 3] = pInventory.getBoots();

		newContents[oldContents.length + 7] = new ItemStack(Material.APPLE, player.getHealth());
		newContents[oldContents.length + 8] = new ItemStack(Material.COOKED_BEEF, player.getFoodLevel());

		for (int i = 0; i < oldContents.length; ++i)
			if (newContents[i] != null) newContents[i] = newContents[i].clone();

		inventoryView.setContents(newContents);
		return inventoryView;
	}
	

	/**
	 * Set the correct vanish status for all players on the server
	 * 
	 * @param p1
	 */
	public void setVanish() {
		for(Player p : server.getOnlinePlayers()) {
			setVanish(p);
		}
	}

	/**
	 * Set the correct vanish status for the player in relation to all other players
	 * 
	 * @param p The player to update
	 */
	public void setVanish(Player p) {
		for (Player p2 : server.getOnlinePlayers()) {
			setVanish(p, p2);
			setVanish(p2, p);
		}
	}
	
	/**
	 * Set the correct vanish status between two players
	 * 
	 * @param viewer Player viewing
	 * @param viewed Player being viewed
	 */
	public void setVanish(Player viewer, Player viewed) {
		if (viewer == viewed) return;
		
		// An op should be invisible to a non-op if the match is launching and not ended
		if (!viewer.isOp() && viewed.isOp() && inLaunchOrMatch()) {
			viewer.hidePlayer(viewed);
		} else {
			viewer.showPlayer(viewed);
		}
	}

	public ArrayList<Location> getCalculatedStarts() {
		return calculatedStarts;
	}

	public void setCalculatedStarts(ArrayList<Location> calculatedStarts) {
		this.calculatedStarts = calculatedStarts;
	}

	public HashMap<Integer, UhcStartPoint> getStartPoints() {
		return startPoints;
	}

	public int countAvailableStartPoints() {
		return availableStartPoints.size();
	}

	public int getNextRadius() {
		return nextRadius;
	}

	public void setNextRadius(int nextRadius) {
		this.nextRadius = nextRadius;
	}

	public World getStartingWorld() {
		return startingWorld;
	}

	public int countPlayers() {
		return this.playersInMatch;
	}

	public Location getLastEventLocation() {
		return lastEventLocation;
	}

	public void setLastEventLocation(Location lastEventLocation) {
		this.lastEventLocation = lastEventLocation;
	}

	public Location getLastNotifierLocation() {
		return lastNotifierLocation;
	}

	public Location getLastDeathLocation() {
		return lastDeathLocation;
	}

	public Location getLastLogoutLocation() {
		return lastLogoutLocation;
	}

}
