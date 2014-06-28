package me.SirSlender.SkillAPIManaAddon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
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
		getLogger().info("FoodbarMana Addon Enabled for SkillAPI!");
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("FoodbarMana Addon Disabled!");
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onManaUse(PlayerManaUseEvent e)
	{
		PlayerSkills playerData = e.getPlayerData();
		Player player = playerData.getPlayer();
		int foodMinus = getFoodChangeAmount(e.getMana(), playerData);
		int foodManaLevel = player.getFoodLevel() - foodMinus;
		player.setFoodLevel(foodManaLevel);
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onManaGain(PlayerManaGainEvent e)
	{
		PlayerSkills playerData = e.getPlayerData();
		Player player = playerData.getPlayer();
		int foodPlus = getFoodChangeAmount(e.getMana(), playerData);
		int foodManaLevel = player.getFoodLevel() + foodPlus;
		player.setFoodLevel(foodManaLevel);
		
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onFoodBarChange(FoodLevelChangeEvent e)
	{
		Player player = (Player) e.getEntity();
		int foodLost = 20 - player.getFoodLevel();
		if (getPlayerMaxMana(player) > 20)
		{
			PlayerSkills pData = skillAPI.getPlayer(player);
			pData.useMana(foodLost);
			int mana = getPlayerMana(player);
			if (mana >= 20) e.setCancelled(true);
			else;
		}
	}
	
	public int getFoodChangeAmount(int manaUsed, PlayerSkills playerData)
	{
		if (playerData.getMaxMana() > 20)
		{
			int foodAmount = manaUsed / 20;
			if (foodAmount > 20) foodAmount = 20;
			else;
			return foodAmount;
		}
		else
		{
			int foodAmount = manaUsed;
			return foodAmount;
		}
	}
	
	public int getPlayerMana(Player player)
	{
		PlayerSkills pData = skillAPI.getPlayer(player);
		int playerMana = pData.getMana();
		return playerMana;
	}
	
	public int getPlayerMaxMana(Player player)
	{
		PlayerSkills pData = skillAPI.getPlayer(player);
		int playerMaxMana = pData.getMaxMana();
		return playerMaxMana;
	}

}
