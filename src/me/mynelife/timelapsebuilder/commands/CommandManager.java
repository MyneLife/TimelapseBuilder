package me.mynelife.timelapsebuilder.commands;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    private ArrayList<GameCommand> cmds;
	
    public CommandManager() {
        cmds = new ArrayList<>();
        cmds.add(new CommandRecord());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        // Sender = Player?
        if (!(sender instanceof Player)) {
            sender.sendMessage("[Timelapse Builder] Please log in to use commands!");
            return true;
        }		
        Player p = (Player) sender;		
        if (cmd.getName().equalsIgnoreCase("tlb")) {
            // Show all commands
            if (args.length == 0) {
                for (GameCommand gcmd : cmds) {
                    CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
                    p.sendMessage(ChatColor.GOLD + "/tlb <" + StringUtils.join(info.aliases(), ", ").trim() + "> " + info.usage() + " - " + info.description());
                }			
                return true;
            }
            
            // Search class for command
            GameCommand wanted = null;			
            for (GameCommand gcmd : cmds) {
                CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
                for (String alias : info.aliases()) {
                    if (alias.equals(args[0])) {
                        wanted = gcmd;
                        break;
                    }
                }
            }
            
            // Command not found
            if (wanted == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] &4Could not find command!"));
                return true;
            }
            
            // Remove first argument
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(args));
            list.remove(0);
            String[] newArgs = new String[list.size()];
            list.toArray(newArgs);		
            wanted.onCommand(p, newArgs);
        }		
        return true;
    }
}
