package me.gamrboy4life.paradox.mods;

import me.gamrboy4life.paradox.gui.hud.HUDManager;
import me.gamrboy4life.paradox.mods.impl.ModArmorStatus;
import me.gamrboy4life.paradox.mods.impl.ModGlintColor;
import me.gamrboy4life.paradox.mods.impl.ModPerspective;
import me.gamrboy4life.paradox.mods.impl.ModPotionStatus;

public class ModInstances {

    
    
    private static ModPerspective modPerspective;
  
    private static ModArmorStatus modArmorStatus;

  
    private static ModPotionStatus modPotionStatus;

    
    private static ModGlintColor glintColor;

    public static void register(HUDManager api) {
        
    	
        modArmorStatus=new ModArmorStatus();
        api.register(modArmorStatus);
        
        modPerspective=new ModPerspective();
        api.register(modPerspective);
        
        modPotionStatus=new ModPotionStatus();
        api.register(modPotionStatus);
        
        glintColor = new ModGlintColor();
        
   }
    
    public static ModPerspective getModPerspective() {
    	return modPerspective;
    }
    
    public static ModGlintColor getModGlintColor() {
    	return glintColor;
    }
}