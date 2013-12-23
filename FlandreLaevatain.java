package flandrelaevatain;

import net.minecraft.src.nucleareal.IConfigurationLoader;
import net.minecraft.src.nucleareal.NuclearealBase;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import flandrelaevatain.proxy.CommonProxy;

@Mod(modid = "FlandreLaevatain", name = "Flandre's Laevatain", version = "1.6.2|1.0.1 Celestina")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = "FPR2", packetHandler = FPR2PacketHandler.class)
public class FlandreLaevatain extends NuclearealBase implements IConfigurationLoader
{
	public static final String ModName = "FlandreLaevatain";
	public static final String ModChannel = "FPR2";

	@Mod.Instance("FlandreLaevatain")
	public static FlandreLaevatain instance;

	@SidedProxy(clientSide = "flandrelaevatain.proxy.ClientProxy", serverSide = "flandrelaevatain.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Override
	public void onLoad(Configuration conf)
	{
		FPR2.CoreID				= conf.getItem(conf.CATEGORY_ITEM, "CoreID", FPR2.CoreID, "").getInt() - 256;
		FPR2.LaevatainID		= conf.getItem(conf.CATEGORY_ITEM, "LaevatainID", FPR2.LaevatainID, "").getInt() - 256;
		FPR2.LaevatainChipID	= conf.getItem(conf.CATEGORY_ITEM, "ChipID", FPR2.LaevatainChipID, "").getInt() - 256;
		FPR2.SupportCoreID		= conf.getItem(conf.CATEGORY_ITEM, "SupportCoreID", FPR2.SupportCoreID, "").getInt() - 256;

		FPR2.UpgraderID			= conf.getBlock(conf.CATEGORY_BLOCK, "UpgraderID", FPR2.UpgraderID, "").getInt();
	}

	@Override
	protected String Version()
	{
		return "1.6.2|1.0.1 Celestina";
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		FPR2EventPreInit.cause(event, this);
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		FPR2EventInit.cause(event, this);
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		FPR2EventPostInit.cause(event, this);
	}
}
