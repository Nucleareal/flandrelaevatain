package flandrelaevatain.strategy;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;

public class Strategy
{
	private Strategy()
	{
	}
	private static Strategy instance = new Strategy();
	public static Strategy get()
	{
		return instance;
	}

	/*             */

	private ISpellCard Default = new _Default();
	private ISpellCard Sword = new _Sword();
	private ISpellCard Laevatain = new _Laevatain();
	private ISpellCard Feather = new _Feather();
	private ISpellCard ChunkEater = new _ChunkEater();
	private ISpellCard SkyRunner = new _SkyRunner();
	private ISpellCard Regenerator = new _RegenHealth();
	private ISpellCard Miner = new _Miner();
	private ISpellCard Smelter = new _Smelter();
	private ISpellCard MineSmelter = new _MineSmelter();
	private ISpellCard Torch = new _Torch();
	private ISpellCard ArrowEater = new _ArrowEater();
	private ISpellCard Igniter = new _Igniter();
	private ISpellCard Shear = new _Shear();
	private ISpellCard BucketEmpty = new _BucketEmpty();
	private ISpellCard BucketWater = new _BucketWater();
	private ISpellCard BucketLava = new _BucketLava();
	private ISpellCard Bed = new _Bed();

	/*             */

	private List<ISpellCard> cards;

	{
		cards = new LinkedList<ISpellCard>();
		cards.add(Default);
		cards.add(Sword);
		cards.add(Laevatain);
		cards.add(Feather);
		cards.add(ChunkEater);
		cards.add(Smelter);
		cards.add(SkyRunner);
		cards.add(Regenerator);
		cards.add(MineSmelter);
		cards.add(Miner);
		cards.add(Torch);
		cards.add(ArrowEater);
		cards.add(Igniter);
		cards.add(Shear);
		cards.add(BucketEmpty);
		cards.add(BucketWater);
		cards.add(BucketLava);
		cards.add(Bed);
	}

	public void registerSpellCardNames()
	{
		LanguageRegistry.instance().addStringLocalization("No Information", "No Information");
		LanguageRegistry.instance().addStringLocalization(Default.getStateName(), "No Effect");
		LanguageRegistry.instance().addStringLocalization(Sword.getStateName(), "Sword");
		LanguageRegistry.instance().addStringLocalization(Laevatain.getStateName(), "Laevatain");
		LanguageRegistry.instance().addStringLocalization(Feather.getStateName(), "Feather");
		LanguageRegistry.instance().addStringLocalization(ChunkEater.getStateName(), "Chunk Eater");
		LanguageRegistry.instance().addStringLocalization(SkyRunner.getStateName(), "Ground Foreigner");
		LanguageRegistry.instance().addStringLocalization(Regenerator.getStateName(), "Regenerator");
		LanguageRegistry.instance().addStringLocalization(Smelter.getStateName(), "Smelter");
		LanguageRegistry.instance().addStringLocalization(MineSmelter.getStateName(), "Melt Chain");
		LanguageRegistry.instance().addStringLocalization(Miner.getStateName(), "Break Chain");
		LanguageRegistry.instance().addStringLocalization(Torch.getStateName(), "Lighter");
		LanguageRegistry.instance().addStringLocalization(ArrowEater.getStateName(), "Arrow Eater");
		LanguageRegistry.instance().addStringLocalization(Igniter.getStateName(), "Igniter");
		LanguageRegistry.instance().addStringLocalization(Shear.getStateName(), "Shear");
		LanguageRegistry.instance().addStringLocalization(BucketEmpty.getStateName(), "Liquid Remover");
		LanguageRegistry.instance().addStringLocalization(BucketWater.getStateName(), "Water Provider");
		LanguageRegistry.instance().addStringLocalization(BucketLava.getStateName(), "Lava Provider");
		LanguageRegistry.instance().addStringLocalization(Bed.getStateName(), "Rest Provider");

		LanguageRegistry.instance().addStringLocalization("No Information", "ja_JP", "登録なし");
		LanguageRegistry.instance().addStringLocalization(Default.getStateName(), "ja_JP", "効果なし");
		LanguageRegistry.instance().addStringLocalization(Sword.getStateName(), "ja_JP", "剣");
		LanguageRegistry.instance().addStringLocalization(Laevatain.getStateName(), "ja_JP", "波動剣");
		LanguageRegistry.instance().addStringLocalization(Feather.getStateName(), "ja_JP", "羽");
		LanguageRegistry.instance().addStringLocalization(ChunkEater.getStateName(), "ja_JP", "領域を食らうもの");
		LanguageRegistry.instance().addStringLocalization(SkyRunner.getStateName(), "ja_JP", "翼");
		LanguageRegistry.instance().addStringLocalization(Regenerator.getStateName(), "ja_JP", "再生者");
		LanguageRegistry.instance().addStringLocalization(Smelter.getStateName(), "ja_JP", "熱破壊");
		LanguageRegistry.instance().addStringLocalization(MineSmelter.getStateName(), "ja_JP", "融解の連鎖");
		LanguageRegistry.instance().addStringLocalization(Miner.getStateName(), "ja_JP", "破壊の連鎖");
		LanguageRegistry.instance().addStringLocalization(Torch.getStateName(), "ja_JP", "明るさ齎すもの");
		LanguageRegistry.instance().addStringLocalization(ArrowEater.getStateName(), "ja_JP", "矢を食らうもの");
		LanguageRegistry.instance().addStringLocalization(Igniter.getStateName(), "ja_JP", "文明の利器");
		LanguageRegistry.instance().addStringLocalization(Shear.getStateName(), "ja_JP", "鋏");
		LanguageRegistry.instance().addStringLocalization(BucketEmpty.getStateName(), "ja_JP", "液体を食らうもの");
		LanguageRegistry.instance().addStringLocalization(BucketWater.getStateName(), "ja_JP", "湧水の祭器");
		LanguageRegistry.instance().addStringLocalization(BucketLava.getStateName(), "ja_JP", "灼熱の祭器");
		LanguageRegistry.instance().addStringLocalization(Bed.getStateName(), "ja_JP", "休息を齎すもの");
	}

	private ISpellCard current = Default;

	public void changeStrategy(ItemStack ist)
	{
		current = new InventoryPickaxe(ist, true).match();
	}

	/*             */

	public ISpellCard getDefaultStrategy()
	{
		return Default;
	}

	public boolean canHarvestBlock(Block block)
	{
		return current.canHarvestBlock(block);
	}

	public float getStrVsBlock(ItemStack ist, Block block)
	{
		changeStrategy(ist);
		return current.getStrVsBlock(ist, block);
	}

	public boolean hitEntity(ItemStack ist, EntityLivingBase to, EntityLivingBase from)
	{
		changeStrategy(ist);
		return current.hitEntity(ist, to, from);
	}

	public float getStrVsBlock(ItemStack ist, Block block, int meta)
	{
		changeStrategy(ist);
		return current.getStrVsBlock(ist, block, meta);
	}

	public boolean onBlockDestroyed(World world, int x, int y, int z, int m, ItemStack ist, EntityLivingBase by)
	{
		changeStrategy(ist);
		return current.onBlockDestroyed(world, x, y, z, m, ist, by);
	}

	public int getStateWithChange(ItemStack ist)
	{
		changeStrategy(ist);
		return current.getFormState();
	}

	public ISpellCard match(byte[] states, int coreLevel)
	{
		ISpellCard card = null;
		for(ISpellCard spell : cards)
		{
			boolean isMathced = true;
			char[] identify = spell.getStateCode().toCharArray();

			for(int i = 0; i < 9; i++)
			{
				byte id = (byte)(identify[i]-'0');
				if(states[i] == id || id == 9);
				else isMathced = false;
			}

			if(isMathced)
			{
				if(spell.getRequireCoreLevel() > coreLevel) continue;
				card = spell;
			}
		}
		return card;
	}

	public int getWeaponDamage()
	{
		return current.getWeaponDamage();
	}

	public void onUpdate(World world, ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
		InventoryPickaxe inv = new InventoryPickaxe(ist, !isCurrent);
		inv.onUpdate(world, ist, entity, how, isCurrent);
	}

	public void onProfileChanged(byte[] states, int from, int to, InventoryPickaxe inv)
	{
		match(states, inv.getSupportCoreLevel());
	}

	public int getFunctionNameColor()
	{
		return current.getTextColor();
	}

	public List<ISpellCard> getAllStrategy()
	{
		return cards;
	}

	public static byte[] spellToArray(ISpellCard spell)
	{
		byte[] res = new byte[9];
		char[] arr = spell.getStateCode().toCharArray();

		for(int i = 0; i < 9; i++)
		{
			char c = arr[i];
			if(c == '9')
				res[i] = 0;
			else
				res[i] = (byte)(c - '0');
		}
		return res;
	}

	public boolean onItemUse(World world, EntityPlayer player, ItemStack ist, int x, int y, int z, int side, float fx, float fy, float fz)
	{
		changeStrategy(ist);
		return current.onItemUse(world, player, ist, x, y, z, side, fx, fy, fz);
	}

	public boolean canUseStrategy(ItemStack ist)
	{
		return new InventoryPickaxe(ist, false).getSupportCoreLevel() >= current.getRequireCoreLevel();
	}

	public ItemStack onItemRightClick(ItemStack ist, World world, EntityPlayer player, double posX, double posY, double posZ)
	{
		changeStrategy(ist);
		return current.onItemRightClick(world, player, ist, posX, posY, posZ);
	}

	public boolean onBlockStartBreak(ItemStack ist, int x, int y, int z, EntityPlayer player)
	{
		changeStrategy(ist);
		return current.onBlockStartBreak(player.worldObj, player, ist, x, y, z);
	}

	public boolean interactToEntityWithItem(EntityLivingBase lv, EntityPlayer ep, ItemStack ist)
	{
		changeStrategy(ist);
		return current.onInteractEntityWithIst(lv, ep, ist);
	}
}
