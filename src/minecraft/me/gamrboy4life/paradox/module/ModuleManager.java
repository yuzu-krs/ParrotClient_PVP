package me.gamrboy4life.paradox.module;

import java.util.ArrayList;
import java.util.List;

import me.gamrboy4life.paradox.Paradox;
import me.gamrboy4life.paradox.module.pvp.AutoSprint;
import me.gamrboy4life.paradox.module.pvp.FastPlace;
import me.gamrboy4life.paradox.module.pvp.MineClicker;
import me.gamrboy4life.paradox.module.render.EspChest;
import me.gamrboy4life.paradox.module.render.EspItems;
import me.gamrboy4life.paradox.module.render.EspPlayer;
import me.gamrboy4life.paradox.module.render.FullBright;
import me.gamrboy4life.paradox.module.render.Xray;
import me.gamrboy4life.paradox.module.status.Armor;
import me.gamrboy4life.paradox.module.status.Potion;
import me.gamrboy4life.paradox.module.status.TabGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ModuleManager {
	
	private static ArrayList<Module> mods;
	
	public ModuleManager() {
		mods=new ArrayList<Module>();
		
		newMod(new TabGui());
		
		
		newMod(new AutoSprint());
		
			
		newMod(new EspChest());
		newMod(new EspItems());
		newMod(new EspPlayer());
		
		newMod(new FastPlace());
		newMod(new FullBright());
		
		
		newMod(new MineClicker());

		newMod(new Xray());
		
		newMod(new Armor());
		
		
		newMod(new Potion());
		
	}
	
	
	public static List<Module> getModulesbyCategory(Category c){
		List<Module> modules=new ArrayList<Module>();
		
		for(Module m:Paradox.instance.moduleManager.getModules()) {
			if(m.category==c) {
				modules.add(m);
			}
		}
		return modules;
		
	}
	
	
	
	public static void newMod(Module m) {
		mods.add(m);
	}
	
	public static ArrayList<Module> getModules(){
		return mods;
	}
	
	public static void onUpdate() {
		for(Module m:mods) {
			m.onUpdate();
		}
	}
	
	public static void onRender() {
		for(Module m:mods) {
			m.onRender();
		}
	}
	
	public static void onKey(int k) {
		for(Module m:mods) {
			if(m.getKey()==k) {
				m.toggle();
			}
		}
	}


	public static void addChatMessage(String message) {
		message="\u00A7e"+Paradox.name+"\2477: "+message;
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
	}
	
	public static Module getModuleByName(String moduleName) {
		for(Module m:Paradox.instance.moduleManager.getModules()) {
			if(!m.getName().trim().equalsIgnoreCase(moduleName)&&!m.toString().equalsIgnoreCase(moduleName.trim())) continue;
			return m;
		}
		return null;
	}
	
	
	
	
	
	
	
	
}
