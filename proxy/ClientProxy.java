package flandrelaevatain.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import flandrelaevatain.item.entity.EntityTorchState;
import flandrelaevatain.item.entity.render.RenderTorchState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ClientProxy extends CommonProxy
{
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory matrix)
	{
	}

	@Override
	public void registerRendering()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityTorchState.class, new RenderTorchState());
	}
}
