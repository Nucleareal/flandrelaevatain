package flandrelaevatain.item;

import net.minecraft.client.renderer.texture.IconRegister;

public class ItemCore extends ItemMultiParts
{
	public ItemCore(int i)
	{
		super(i);
	}

	@Override
	protected void register(IconRegister r)
	{
		MultiIcon[0] = r.registerIcon(getIconString()+"_Raw");
		MultiIcon[1] = r.registerIcon(getIconString()+"_Melt");
		MultiIcon[2] = r.registerIcon(getIconString()+"_Stick");
		MultiIcon[3] = r.registerIcon(getIconString()+"_Blade");
		MultiIcon[4] = r.registerIcon(getIconString()+"_Broken");
		MultiIcon[5] = r.registerIcon(getIconString()+"_Plate");
		MultiIcon[6] = r.registerIcon(getIconString()+"_Hammer");
	}

	@Override
	protected int getMaxDamageAmount()
	{
		return 7;
	}

	@Override
	protected int getMaxIconAmount()
	{
		return getMaxDamageAmount();
	}
}
