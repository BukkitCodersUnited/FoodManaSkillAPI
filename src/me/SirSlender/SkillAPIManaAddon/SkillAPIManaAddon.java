//Commented code is for debugging.
package me.SirSlender.SkillAPIManaAddon;

//import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.PlayerSkills;
import com.sucy.skill.api.event.PlayerManaGainEvent;
import com.sucy.skill.api.event.PlayerManaUseEvent;

public class SkillAPIManaAddon extends JavaPlugin implements Listener
{
	private SkillAPI skillAPI;
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("FoodbarMana Addon Enabled for SkillAPI!");
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("FoodbarMana Addon Disabled!");
	}
	
	@EventHandler
	public void onManaUse(PlayerManaUseEvent e)
	{
		PlayerSkills playerData = e.getPlayerData();
		Player player = playerData.getPlayer();
		int ratio = playerData.getMaxMana() / 20;
		int manaUsed = e.getMana();
		int newFood;
		if (ratio > 1)
		{
			manaUsed = manaUsed / ratio;
			newFood = player.getFoodLevel() - manaUsed;
			if (newFood > 20) newFood = 20;
			else if (newFood < 1) newFood = 0;
			player.setFoodLevel(newFood);
		}
		else
		{
			newFood = player.getFoodLevel() - manaUsed;
			if (newFood > 20) newFood = 20;
			else if (newFood < 1) newFood = 0;
			player.setFoodLevel(newFood);		
		}
	}
	
	@EventHandler
	public void onManaGain(PlayerManaGainEvent e)
	{
		PlayerSkills playerData = e.getPlayerData();
		Player player = playerData.getPlayer();
		int ratio = playerData.getMaxMana() / 20;
		int manaAdd = e.getMana();
		int newFood;
		//player.sendMessage("Mana Gain Triggered");
		if (ratio > 1)
		{
			manaAdd = manaAdd / ratio;
			if (manaAdd < 1) manaAdd = 1;
			//player.sendMessage("Mana Added " + manaAdd);
			newFood = player.getFoodLevel() + manaAdd;
			//player.sendMessage("newFood " + newFood);
			if (newFood > 20) newFood = 20;
			else if (newFood < 1) newFood = 0;
			//player.sendMessage("newFood new " + newFood);
			player.setFoodLevel(newFood);
		}
		else
		{
			newFood = player.getFoodLevel() + manaAdd;
			//player.sendMessage("newFood " + newFood);
			if (newFood > 20) newFood = 20;
			else if (newFood < 1) newFood = 0;
			//player.sendMessage("newFood new " + newFood);
			player.setFoodLevel(newFood);		
		}	
	}
	
	public int getPlayerMana(Player player)
	{
		String pName = player.getName();
		PlayerSkills pData = skillAPI.getPlayer(pName);
		int playerMana = pData.getMana();
		return playerMana;
	}
	
	public int getPlayerMaxMana(Player player)
	{
		String pName = player.getName();
		PlayerSkills pData = skillAPI.getPlayer(pName);
		int playerMaxMana = pData.getMaxMana();
		return playerMaxMana;
	}

}
