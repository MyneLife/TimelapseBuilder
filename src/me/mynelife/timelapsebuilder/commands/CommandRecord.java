package me.mynelife.timelapsebuilder.commands;

import java.io.File;
import me.mynelife.timelapsebuilder.TimelapseBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Start/stop/delete a recording of a Timelapse-Build", usage = "<start | stop | delete> <name>", aliases = {"recording", "record"})
public class CommandRecord extends GameCommand {
    
    @Override
    public void onCommand(Player p, String[] args) {
        
        //stop recording
        if(args[0].equalsIgnoreCase("stop")) {
            if(!p.hasPermission("tlb.recording.stop")) { 
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }            
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You have stopped the recording named &2" + TimelapseBuilder.getActiveBlockManager().getName() + "&6 successfully!"));            
            TimelapseBuilder.resetActiveBlockManager();
            return;
        }
        
        //start recording
        if(args.length <= 1 && args[0].equalsIgnoreCase("start")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] Please use the command as following: &2/tlb recording start <name>"));
            return;
        } else if(args.length == 2 && args[0].equalsIgnoreCase("start")) {
            if(!p.hasPermission("tlb.recording.start")) { 
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }
            String name = args[1];
            File file = new File("plugins//TimelapseBuilder//" + name + ".yml");
            if(file.exists()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] This building already exists! The building will be appended with the new blocks"));                
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You have started the recording of the building named &2" + name + "&6 successfully!"));              
            }  
            TimelapseBuilder.setActiveBlockManager(name, p.getWorld().getName());
            return;
        }
        
        //delete recording
        if(args.length <= 1 && args[0].equalsIgnoreCase("delete")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] Please use the command as following: &2/tlb recording delete <name>"));
            return;
        } else if(args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            if(!p.hasPermission("tlb.recording.delete")) { 
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }
            String name = args[1];
            File file = new File("plugins//TimelapseBuilder//" + name + ".yml");
            if(file.exists()) {
                file.delete();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You have successfully deleted the building &2" + name + "&6!"));
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] There is no saved building named &4" + name + "&6!"));
            }
            return;
        }
                
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] Please use the command &2/tlb recording&6 for more informations!"));                    
    }
}
