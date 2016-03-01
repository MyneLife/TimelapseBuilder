package me.mynelife.timelapsebuilder;

import java.io.File;
import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

public class BlockManager {
    
    private TimelapseBuilder plugin;
    private static File folder = new File("plugins//TimelapseBuilder");
    private static File blocks = new File("plugins//TimelpaseBuilder//blocks.yml");
    private static YamlConfiguration cfg;
    private static int blockcounter;
    
    public BlockManager(TimelapseBuilder plugin) {
        this.plugin = plugin;
        if(!folder.exists()) {
            folder.mkdir();
	}
        if(!blocks.exists()) {
           try {
		blocks.createNewFile();
            } catch(IOException e) {
		System.out.println("[BowPVP] The file 'plugins/TimelapseBuilder/blocks.yml' could not be created!");
            } 
        }
        cfg = YamlConfiguration.loadConfiguration(blocks);
        blockcounter = 1;
    }
    
    public static void saveBlock(Location loc, Block block) {
        cfg.set(String.valueOf(blockcounter), block);
        try {
            cfg.save(blocks);
            blockcounter++;
	} catch (IOException e) {
            e.printStackTrace();
	}
    }
}
