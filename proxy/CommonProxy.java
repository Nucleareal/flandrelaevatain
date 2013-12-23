package flandrelaevatain.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class CommonProxy
{
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory matrix)
	{
		//player.worldObj.play;
		player.worldObj.playAuxSFX(1021, MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
	}

	public void registerRendering()
	{
	}
}
