package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ISpellCard
{
	public boolean canHarvestBlock(Block block);

	public float getStrVsBlock(ItemStack ist, Block block);

	public boolean hitEntity(ItemStack ist, EntityLivingBase to, EntityLivingBase from);

	public float getStrVsBlock(ItemStack ist, Block block, int meta);

	public boolean onBlockDestroyed(World world, int x, int y, int z, int m, ItemStack ist, EntityLivingBase by);

	public String getStateCode();

	public String getStateName();

	public int getFormState();

	public int getWeaponDamage();

	public void onUpdate(World world, ItemStack ist, Entity entity, int how, boolean isCurrent);

	public int getTextColor();

	public boolean onItemUse(World world, EntityPlayer player, ItemStack ist, int x, int y, int z, int side, float fx, float fy, float fz);

	public int getRequireCoreLevel();

	public ItemStack onItemRightClick(World world, EntityPlayer player, ItemStack ist, double posX, double posY, double posZ);

	public boolean onBlockStartBreak(World worldObj, EntityPlayer player, ItemStack ist, int x, int y, int z);

	public boolean onInteractEntityWithIst(EntityLivingBase lv, EntityPlayer ep, ItemStack ist);
}
