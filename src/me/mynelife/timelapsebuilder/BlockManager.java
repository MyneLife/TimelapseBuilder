package me.mynelife.timelapsebuilder;

import java.io.File;
import java.io.IOException;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

public class BlockManager {
    
    private TimelapseBuilder plugin;
    
    private File blocksFile;
    private YamlConfiguration cfg;
    private int blockcounter;
    private String name;
    
    public BlockManager(String name) {
        this.name = name;             
        blocksFile = new File("plugins//TimelapseBuilder//" + name + ".yml");
        if(!blocksFile.exists()) {
           try {
		blocksFile.createNewFile();
                blockcounter = 1;
                cfg = YamlConfiguration.loadConfiguration(blocksFile);                
                cfg.set("0", String.valueOf(blockcounter));
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
        
    public void saveBlock(Block block) {
        String blockdata = "[" + block.getX() + "," + block.getY() + "," + block.getZ() + "," + block.getData() + "," + block.getType() + "]";
        cfg.set(String.valueOf(blockcounter), blockdata);
        cfg.set("0", String.valueOf(blockcounter));
        try {
            cfg.save(blocksFile);
            blockcounter++;
	} catch (IOException e) {
            e.printStackTrace();
	}
    }    
}
