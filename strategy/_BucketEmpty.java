package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import flandrelaevatain.item.UpgradeCore;

public class _BucketEmpty extends _Default
{
	protected MovingObjectPosition getMovingObjectPositionFromPlayer(World world, EntityPlayer player, boolean par3)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
        Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return world.rayTraceBlocks_do_do(vec3, vec31, par3, !par3);
    }

	@Override
	public ItemStack onItemRightClick(World world, EntityPlayer player, ItemStack ist, double posX, double posY, double posZ)
    {
		boolean isRemover = getPlaceBlockID() == 0;
        MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, isRemover);

        System.out.println("Controll Sequence Reached With Class "+getClass().getSimpleName()+", isRemover:"+isRemover);

        if (mop == null)
        {
            return ist;
        }
        else
        	if (mop.typeOfHit == EnumMovingObjectType.TILE)
            {
                int i = mop.blockX;
                int j = mop.blockY;
                int k = mop.blockZ;

                if (!world.canMineBlock(player, i, j, k))
                {
                    return ist;
                }

                if (isRemover)
                {
                    if (!player.canPlayerEdit(i, j, k, mop.sideHit, ist)) return ist;

                    Material mat = world.getBlockMaterial(i, j, k);
                    if(mat == Material.water || mat == Material.lava)
                	{
                		world.setBlockToAir(i, j, k);
                		return ist;
                	}
                }
                else
                {
                	switch(mop.sideHit)
                	{
                		case 0: --j; break;
                		case 1: ++j; break;
                		case 2: --k; break;
                		case 3: ++k; break;
                		case 4: --i; break;
                		case 5: ++i; break;
                	}
                    if (!player.canPlayerEdit(i, j, k, mop.sideHit, ist))
                    {
                        return ist;
                    }
                    tryPlaceContainedLiquid(world, i, j, k);
                }
            }
            return ist;
    }

	public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3, int par4)
    {
		int blockID = getPlaceBlockID();

		Material material = par1World.getBlockMaterial(par2, par3, par4);
        boolean noSolid = !material.isSolid();

        if (!par1World.isAirBlock(par2, par3, par4) && !noSolid)
        {
            return false;
        }
        else
        {
        	if (!par1World.isRemote && noSolid && !material.isLiquid())
            {
                par1World.destroyBlock(par2, par3, par4, true);
            }
            par1World.setBlock(par2, par3, par4, blockID, 0, 3);
            return true;
        }
    }

	protected int getPlaceBlockID()
	{
		return 0;
	}

	public int getFormState()
	{
		return BucketEmpty;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Flandre.getPower();
	}

	@Override
	public String getStateCode()
	{
		return "000707070";
	}

	@Override
	public String getStateName()
	{
		return "Bucket";
	}
}
