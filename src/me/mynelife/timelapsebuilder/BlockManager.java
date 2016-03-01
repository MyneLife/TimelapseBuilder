package me.mynelife.timelapsebuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

public class BlockManager {
    
    private TimelapseBuilder plugin;
    private File folder = new File("plugins//TimelapseBuilder");
    private File blocksFile;
    private YamlConfiguration cfg;
    private int blockcounter;
    private Map<Integer, Block> blocks = new HashMap<Integer, Block>();
    
    public BlockManager(TimelapseBuilder plugin, String name) {
        this.plugin = plugin;
        if(!folder.exists()) {
            folder.mkdir();
	}        
        blocksFile = new File("plugins//TimelapseBuilder//" + name + ".yml");
        if(!blocksFile.exists()) {
           try {
		blocksFile.createNewFile();
            } catch(IOException e) {
		System.out.println("[BowPVP] The file 'plugins/TimelapseBuilder/blocks.yml' could not be created!");
            } 
        }
        cfg = YamlConfiguration.loadConfiguration(blocksFile);
        blockcounter = 1;
    }
    
    public void saveBlock(Block block) {
        String blockdata = "[" + block.getX() + "," + block.getY() + "," + block.getZ() + "," + block.getData() + "," + block.getType() + "]";
        cfg.set(String.valueOf(blockcounter), blockdata);
        try {
            cfg.save(blocksFile);
            blocks.put(blockcounter, block);
            blockcounter++;
	} catch (IOException e) {
            e.printStackTrace();
	}
    }
}
