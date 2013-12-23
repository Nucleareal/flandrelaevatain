package flandrelaevatain.strategy;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flandrelaevatain.item.ItemLaevatain;

public class _Default implements ISpellCard
{
	protected int Pickaxe = 0;
	protected int Sword = 1;
	protected int Laevatain = 2;
	protected int Feather = 3;
	protected int Torch = 4;
	protected int Igniter = 5;
	protected int Shear = 6;
	protected int BucketEmpty = 7;
	protected int BucketWater = 8;
	protected int BucketLava = 9;

	protected static final Random rand = new Random();

	@Override
	public boolean canHarvestBlock(Block block)
	{
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack ist, Block block)
	{
		return Float.MAX_VALUE;
	}

	@Override
	public boolean hitEntity(ItemStack ist, EntityLivingBase to, EntityLivingBase from)
	{
		return false;
	}

	@Override
	public float getStrVsBlock(ItemStack ist, Block block, int meta)
	{
		return getStrVsBlock(ist, block);
	}

	@Override
	public boolean onBlockDestroyed(World world, int x, int y, int z, int m, ItemStack ist, EntityLivingBase by)
	{
		damageItem(ist, by);
		return true;
	}

	public int getFormState()
	{
		return Pickaxe;
	}

	@Override
	public String getStateCode()
	{
		return "999999999";
	}

	@Override
	public String getStateName()
	{
		return "No Effect";
	}

	@Override
	public int getWeaponDamage()
	{
		return 0;
	}

	@Override
	public void onUpdate(World world, ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
	}

	@Override
	public int getTextColor()
	{
		return 0xFFFFFF;
	}

	protected void damageItem(ItemStack ist, EntityLivingBase user)
	{
		ItemLaevatain l = (ItemLaevatain)ist.getItem();
		l.damageItem(ist, user);
	}

	protected void damageItem(ItemStack ist, EntityPlayer user)
	{
		damageItem(ist, (EntityLivingBase)user);
	}

	@Override
	public boolean onItemUse(World world, EntityPlayer player, ItemStack ist, int x, int y, int z, int side, float fx, float fy, float fz)
	{
		return false;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return -1;
	}

	@Override
	public ItemStack onItemRightClick(World world, EntityPlayer player, ItemStack ist, double posX, double posY, double posZ)
	{
		return ist;
	}

	@Override
	public boolean onBlockStartBreak(World worldObj, EntityPlayer player, ItemStack ist, int x, int y, int z)
	{
		return false;
	}

	@Override
	public boolean onInteractEntityWithIst(EntityLivingBase lv, EntityPlayer ep, ItemStack ist)
	{
		return false;
	}
}
