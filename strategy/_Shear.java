package flandrelaevatain.strategy;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import flandrelaevatain.item.UpgradeCore;

public class _Shear extends _Default
{
	public int getFormState()
	{
		return Shear;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Abandoned.getPower();
	}

	@Override
	public String getStateCode()
	{
		return "101101010";
	}

	@Override
	public String getStateName()
	{
		return "Shear";
	}

	@Override
	public boolean onInteractEntityWithIst(EntityLivingBase lv, EntityPlayer ep, ItemStack ist)
	{
		if (lv.worldObj.isRemote)
        {
            return false;
        }
        if (lv instanceof IShearable)
        {
            IShearable target = (IShearable)lv;
            if (target.isShearable(ist, lv.worldObj, (int)lv.posX, (int)lv.posY, (int)lv.posZ))
            {
                ArrayList<ItemStack> drops = target.onSheared(new ItemStack(Item.shears), lv.worldObj, (int)lv.posX, (int)lv.posY, (int)lv.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, ist));

                Random rand = new Random();
                for(ItemStack stack : drops)
                {
                    EntityItem ent = lv.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
                damageItem(ist, ep);
            }
            return true;
        }
        return false;
	}
}
