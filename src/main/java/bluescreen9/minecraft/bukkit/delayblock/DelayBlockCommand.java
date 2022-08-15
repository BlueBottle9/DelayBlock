package bluescreen9.minecraft.bukkit.delayblock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bluescreen9.minecraft.bukkit.lang.MessageProcessor;

public class DelayBlockCommand implements TabExecutor{

	private static final ArrayList<String> empty = new ArrayList<String>();
	private static final MessageProcessor color = new MessageProcessor() {
		public String process(String original) {
			return ChatColor.translateAlternateColorCodes('&', original);
		}
	};
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		return empty;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if (!sender.isOp()) {
					Main.Language.sendMessage(sender, "error.nopermission", color);
					return true;
				}
				
				args = placehold(args);
				String x = args[0];
				String y = args[1];
				String z = args[2];
				Location loc;
				try {
					World world = null;
					if (sender instanceof Player) {
						world = ((Player)sender).getWorld();
					}
					if (sender instanceof CommandBlock) {
						world = ((CommandBlock)sender).getWorld();
					}
					if (sender instanceof ConsoleCommandSender) {
						world = Bukkit.getWorld("world");
					}
					loc = new Location(world, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
				} catch (Exception e) {
					Main.Language.sendMessage(sender, "usage", color);
					return true;
				}
				
				String block = args[3].replace("minecraft:", "").toUpperCase();
				Material blocktype;
				try {
					blocktype = Material.valueOf(block);
				} catch (Exception e) {
					Main.Language.sendMessage(sender, "error.wrongblocktype", color);
					return true;
				}
				
				String delay_s = args[4];
				String persistent_s = args[5];
				
				int delay;
				int persistent;
				
				try {
					delay = Integer.parseInt(delay_s);
					persistent = Integer.parseInt(persistent_s);
				} catch (Exception e) {
					Main.Language.sendMessage(sender, "error.wrongdelayorpersistent", color);
					return true;
				}
				
				place(loc, blocktype, delay, persistent);
		return true;
	}
			
		private static String[] placehold(String[] original) {
					String[] array = new String[7];
					for (int i = 0;i < array.length; i++) {
						array[i] = "";
					}
					for (int i = 0;i < original.length; i++) {
						array[i] = original[i];
					}
				return array;
		}
		
		public static void place(final Location loc, final Material blocktype,final int delay,final int persistent) {
					new BukkitRunnable() {
						public void run() {
								loc.getBlock().setType(blocktype);
								new BukkitRunnable() {
									public void run() {
										loc.getBlock().setType(Material.AIR);
									}
								}.runTaskLater(Main.DelayBlock, persistent);
						}
					}.runTaskLater(Main.DelayBlock, delay);
		}
}
