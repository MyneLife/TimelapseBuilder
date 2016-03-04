package me.mynelife.timelapsebuilder;

import java.io.File;
import java.io.IOException;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

public class BlockManager {    
    
    private File blocksFile;
    private YamlConfiguration cfg;
    private int blockcounter;
    private String name;
    private String worldname;
    
    public BlockManager(String name, String worldname) {
        this.name = name;
        this.worldname = worldname;
        blocksFile = new File("plugins//TimelapseBuilder//" + name + ".yml");
        if(!blocksFile.exists()) {
           try {
		blocksFile.createNewFile();
                blockcounter = 1;
                cfg = YamlConfiguration.loadConfiguration(blocksFile);
                cfg.set("world", worldname);
                cfg.set("0", blockcounter);
                cfg.save(blocksFile);
            } catch(IOException e) {
		System.out.println("[Timelapse Builder] The file 'plugins/TimelapseBuilder/" + name + ".yml' could not be created!");
            } 
        } else {
            cfg = YamlConfiguration.loadConfiguration(blocksFile);
            blockcounter = cfg.getInt("0");             
        }                
    }
    
    public String getName() {
        return name;
    }
    
    public String getWorldName() {
        return worldname;
    }
        
    public void saveBlock(Block block) {
        String blockdata = "[set," + block.getX() + "," + block.getY() + "," + block.getZ() + "," + block.getData() + "," + block.getType() + "]";
        cfg.set(String.valueOf(blockcounter), blockdata);
        cfg.set("0", blockcounter);
        try {
            cfg.save(blocksFile);
            blockcounter++;
	} catch (IOException e) {
            e.printStackTrace();
	}
    }

    public void removeBlock(Block block) {
        String blockdata = "[remove," + block.getX() + "," + block.getY() + "," + block.getZ() + "," + block.getData() + "," + block.getType() + "]";
        cfg.set(String.valueOf(blockcounter), blockdata);
        cfg.set("0", blockcounter);
        try {
            cfg.save(blocksFile);
            blockcounter++;
	} catch (IOException e) {
            e.printStackTrace();
	}
    }
}