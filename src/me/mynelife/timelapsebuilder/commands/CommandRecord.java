package me.mynelife.timelapsebuilder.commands;

import java.io.File;
import me.mynelife.timelapsebuilder.TimelapseBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Start/stop the recording of a Timelapse-Build", usage = "<start | stop> <name>", aliases = {"recording", "record"})
public class CommandRecord extends GameCommand {
    
    @Override
    public void onCommand(Player p, String[] args) {
        
        //Permission
        if(!(p.hasPermission("tlb.recording.start")) || !(p.hasPermission("tlb.recording.stop"))) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
            return;
        }
        
        // Arguments
        if(args.length <= 1) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] Please use the command as following: &2/tlb recording <start | stop> <name>"));
            return;
        }
        
        if(args[0].equalsIgnoreCase("start")) {
            String name = args[1];
            File file = new File("plugins//TimelapseBuilder//" + name + ".yml");
            if(file.exists()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] This building already exists! THe building will be appended with the new blocks"));
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You have started the recording of the building named &2" + name + "&6 successfully!"));
            }  
            TimelapseBuilder.setActiveBlockManager(name);                        
        } else if(args[0].equalsIgnoreCase("stop")) {
            
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] Please use the command as following: &2/tlb recording <start | stop>"));            
        }
    }
}
