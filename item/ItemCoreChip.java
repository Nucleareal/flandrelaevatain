package flandrelaevatain.item;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import flandrelaevatain.FPR2;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;
import flandrelaevatain.strategy.ISpellCard;
import flandrelaevatain.strategy.Strategy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCoreChip extends ItemMultiParts implements ISpellCardInfo
{
	public ItemCoreChip(int i)
	{
		super(i);
		setMaxStackSize(1);
	}

	@Override
	protected int getMaxIconAmount()
	{
		return 1;
	}

	@Override
	protected void register(IconRegister r)
	{
		MultiIcon[0] = r.registerIcon(getIconString());
	}

	@Override
	protected int getMaxDamageAmount()
	{
		return 1;
	}

	public void addInformation(ItemStack ist, EntityPlayer player, List list, boolean par4)
    {
		InventoryPickaxe inp = new InventoryPickaxe(ist, true);
		inp.setInventorySlotContents(1, FPR2.Flandre.copy());

    	List<ISpellCard> lp = inp.matchAll();
    	if(lp.size() > 0)
    	{
	    	for(ISpellCard sp : lp)
	    	{
	    		list.add(LanguageRegistry.instance().getStringLocalization(sp.getStateName()));
	    	}
    	}
    	else
    	{
    		list.add(LanguageRegistry.instance().getStringLocalization("No Information"));
    	}
    }

	@SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
    	List<ISpellCard> spells = Strategy.get().getAllStrategy();
    	for(ISpellCard spell : spells)
    	{
    		ItemStack ist = FPR2.Chip.copy();
    		new InventoryPickaxe(ist, true).writeSpell(0, spell);
    		list.add(ist);
    	}

    	for(int i = 0; i < (spells.size() / 16 + 1); i++)
    	{
    		ItemStack ist = FPR2.Chip.copy();
    		InventoryPickaxe inv = new InventoryPickaxe(ist, true);
    		for(int j = 0; j < 16; j++)
    		{
    			int index = i*16+j;
    			if(index >= spells.size()) break;
    			inv.writeSpell(j, spells.get(index));
    		}
    		list.add(ist);
    	}
    }
}
