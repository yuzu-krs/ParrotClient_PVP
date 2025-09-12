package me.gamrboy4life.paradox.mods;

import me.gamrboy4life.paradox.gui.hud.HUDManager;
import me.gamrboy4life.paradox.mods.impl.ModArmorStatus;
import me.gamrboy4life.paradox.mods.impl.ModPerspective;
import me.gamrboy4life.paradox.mods.impl.ModPotionStatus;

public class ModInstances {

    
    
    private static ModPerspective modPerspective;
  
    private static ModArmorStatus modArmorStatus;

  
    private static ModPotionStatus modPotionStatus;

    


    public static void register(HUDManager api) {
        
    	
        modArmorStatus=new ModArmorStatus();
        api.register(modArmorStatus);
        
        modPerspective=new ModPerspective();
        api.register(modPerspective);
        
        modPotionStatus=new ModPotionStatus();
        api.register(modPotionStatus);
        
   }
    
    public static ModPerspective getModPerspective() {
    	return modPerspective;
    }
    
}