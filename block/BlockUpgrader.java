package flandrelaevatain.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.nucleareal.animalcrossing.AnimalCrossing;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import flandrelaevatain.FPR2;
import flandrelaevatain.FlandreLaevatain;
import flandrelaevatain.gui.mover.TileMover;
import flandrelaevatain.gui.upgrader.TileUpgrader;

public class BlockUpgrader extends BlockContainer
{
	public BlockUpgrader(int i)
	{
		super(i, Material.glass);
		setCreativeTab(FPR2.Tab);
		setHardness(8F);
		setStepSound(soundGlassFootstep);

		topIcon  = new Icon[getMaxIcon()];
		sideIcon = new Icon[getMaxIcon()];
	}

	public int getMaxIcon()
	{
		return 2;
	}

	private Icon[] topIcon;
	private Icon[] sideIcon;

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
    {
		switch(meta)
		{
			case 0: return new TileUpgrader();
			case 1: return new TileMover();
		}
		return null;
    }

	public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
		if(world.getBlockId(x, y, z) != blockID) return 0;

		ILightValueProvider tile = (ILightValueProvider)world.getBlockTileEntity(x, y, z);

		if(tile == null) return 0;

		return tile.getLightValue();
    }

	public int getRenderType()
	{
		return FPR2.BlockUpgraderRenderID;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			switch(world.getBlockMetadata(x, y, z))
			{
				case 0: player.openGui(FlandreLaevatain.instance, FPR2.UpgraderGuiID, world, x, y, z); break;
				case 1: player.openGui(FlandreLaevatain.instance, FPR2.MoverGuiID, world, x, y, z); break;
			}
			return true;
		}
	}

	public Icon getIcon(int side, int meta)
    {
        if(side == 0 || side == 1) return topIcon[meta];
        return sideIcon[meta];
    }

	public void registerIcons(IconRegister ir)
    {
		topIcon[0] = ir.registerIcon(getTextureName()+"Upgrader_Top"); sideIcon[0] = ir.registerIcon(getTextureName()+"Upgrader_Side");
		topIcon[1] = ir.registerIcon(getTextureName()+"Mover_Top"); sideIcon[1] = ir.registerIcon(getTextureName()+"Mover_Side");
    }

	@Override
    public int damageDropped(int damage)
    {
    	return damage;
    }

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta)
	{
		switch(meta)
		{
			case 0:
			{
				TileUpgrader tile = (TileUpgrader)world.getBlockTileEntity(x, y, z);
				tile.dropAllItems(world, x, y, z);
				break;
			}
			default: break;
		}

		super.breakBlock(world, x, y, z, id, meta);
	}

	public void getSubItems(int p, CreativeTabs tab, List list)
	{
		for (int i = 0; i < getMaxIcon(); i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

	public boolean isOpaqueCube()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }
}
