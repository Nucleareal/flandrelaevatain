package flandrelaevatain.item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.nucleareal.UtilItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flandrelaevatain.FPR2;
import flandrelaevatain.FPR2KeyBind;
import flandrelaevatain.FlandreLaevatain;
import flandrelaevatain.block.BlockUpgrader;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;
import flandrelaevatain.strategy.ISpellCard;
import flandrelaevatain.strategy.Strategy;

public class ItemLaevatain extends ItemDamageable implements ISpellCardInfo
{
	public ItemLaevatain(int i)
	{
		super(i);
	}

	@Override
	protected int getMaxDamageAmount()
	{
		return 495;
	}

	@Override
	protected String getUnlocalizedNameForDisplay(ItemStack ist)
	{
		return String.valueOf(Strategy.get().getStateWithChange(ist));
	}

	public ItemStack onItemRightClick(ItemStack ist, World world, EntityPlayer player)
    {
		return Strategy.get().onItemRightClick(ist, world, player, player.posX, player.posY, player.posZ);
    }

	@Override
	protected void register(IconRegister r)
	{
		MultiIcon[0] = r.registerIcon(getIconString()+"_Pickaxe");
		MultiIcon[1] = r.registerIcon(getIconString()+"_Sword");
		MultiIcon[2] = r.registerIcon(getIconString()+"_Laevatain");
		MultiIcon[3] = r.registerIcon(getIconString()+"_Feather");
		MultiIcon[4] = r.registerIcon(getIconString()+"_Torch");
		MultiIcon[5] = r.registerIcon(getIconString()+"_Igniter");
		MultiIcon[6] = r.registerIcon(getIconString()+"_Shear");
		MultiIcon[7] = r.registerIcon(getIconString()+"_BucketEmpty");
		MultiIcon[8] = r.registerIcon(getIconString()+"_BucketWater");
		MultiIcon[9] = r.registerIcon(getIconString()+"_BucketLava");
	}

	@Override
	protected int getMaxIconAmount()
	{
		return 10;
	}

	@Override
	protected int getMaxStackAmount()
	{
		return 1;
	}

	@Override
	protected int getIconIndex(ItemStack ist, int layer, EntityPlayer player, ItemStack using, int remain, NBTTagCompound nbt)
	{
		return Strategy.get().getStateWithChange(ist);
	}

	protected EnumRarity getNormalRarity(ItemStack ist)
	{
		return FPR2.RarityFlandreEpic;
	}

	protected EnumRarity getEnchantedRarity(ItemStack ist)
	{
		return FPR2.RarityFlandreEpic;
	}

    public boolean canHarvestBlock(Block block)
    {
        return Strategy.get().canHarvestBlock(block);
    }

    public float getStrVsBlock(ItemStack ist, Block block)
    {
        return Strategy.get().getStrVsBlock(ist, block);
    }

    public boolean hitEntity(ItemStack ist, EntityLivingBase to, EntityLivingBase from)
    {
    	return Strategy.get().hitEntity(ist, to, from);
    }

    public boolean onBlockDestroyed(ItemStack ist, World world, int x, int y, int z, int m, EntityLivingBase by)
    {
    	return Strategy.get().onBlockDestroyed(world, x, y, z, m, ist, by);
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public int getItemEnchantability()
    {
        return Item.pickaxeGold.getItemEnchantability();
    }

    public boolean getIsRepairable(ItemStack ist0, ItemStack ist1)
    {
        return false;
    }

    @Override
    public float getStrVsBlock(ItemStack ist, Block block, int meta)
    {
    	return Strategy.get().getStrVsBlock(ist, block, meta);
    }

    /*public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }*/

    public void onUpdate(ItemStack ist, World world, Entity entity, int how, boolean isCurrent)
    {
    	if(entity instanceof EntityPlayer)
    	{
	    	ItemStack ist0 = ((EntityPlayer)entity).getCurrentEquippedItem();
	    	if(FPR2KeyBind.isPressed && ist0 != null && ist0.getItem() instanceof ItemLaevatain)
	    	{
	    		FPR2KeyBind.isPressed = false;
	    		((EntityPlayer)entity).openGui(FlandreLaevatain.instance, FPR2.PickaxeGuiID, world, 0, 0, 0);
	    	}
    	}
    	Strategy.get().onUpdate(world, ist, entity, how, isCurrent);
    }

    protected int getWeaponDamage()
    {
    	return Strategy.get().getWeaponDamage();
    }

    public Multimap getItemAttributeModifiers()
    {
    	if(getWeaponDamage() != 0)
    	{
	        Multimap multimap = super.getItemAttributeModifiers();
	        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)getWeaponDamage(), 0));
	        return multimap;
    	}
    	return super.getItemAttributeModifiers();
    }

    @ForgeSubscribe
    public void onItemBroken(PlayerDestroyItemEvent event)
    {
    	ItemStack ist = event.original;
    	EntityPlayer ep = event.entityPlayer;

    	InventoryPickaxe inp = new InventoryPickaxe(ist, false);
    	inp.dropInto(ep.inventory);

    	if(ist.getItem().itemID == FPR2.Laevatain.itemID)
    	{
    		int invIndex = ep.inventory.currentItem;
    		ep.inventory.setInventorySlotContents(invIndex, FPR2.CoreLaevatainBroken.copy());
    	}
    }

    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
    	return MultiIcon[getIconIndex(stack, renderPass, player, usingItem, useRemaining, UtilItemStack.getNBTFrom(stack))];
    }

    public void addInformation(ItemStack ist, EntityPlayer player, List list, boolean par4)
    {
    	List<ISpellCard> lp = new InventoryPickaxe(ist, true).matchAll();
    	if(lp.size() > 0)
    	{
	    	for(ISpellCard sp : lp)
	    	{
	    		list.add(LanguageRegistry.instance().getStringLocalization(sp.getStateName()));
	    	}
    	}
    	else
    	{
    	}
    }

    /*    */

    @Override
    public Icon getIcon(ItemStack stack, int pass)
    {
    	return getIcon(stack, pass, null, null, 0);
    }

    public boolean onItemUse(ItemStack ist, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz)
    {
        return Strategy.get().onItemUse(world, player, ist, x, y, z, side, fx, fy, fz);
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
        return Strategy.get().interactToEntityWithItem(par3EntityLivingBase, par2EntityPlayer, par1ItemStack);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering()
    {
        return false;
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack ist)
    {
        return true;
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     */
    public boolean getShareTag()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return 0xFFFFFF;
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    }

    public boolean isMap()
    {
        return false;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.none;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 0;
    }

    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
    }

    @SideOnly(Side.CLIENT)
    @Deprecated //Render pass sensitive version below.
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted();
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack ist)
    {
        return super.getRarity(ist);
    }

    public boolean isItemTool(ItemStack par1ItemStack)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return false;
    }

    /*@SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
    	List<ISpellCard> spells = Strategy.get().getAllStrategy();
    	for(ISpellCard spell : spells)
    	{
    		ItemStack ist = FPR2.Laevataine.copy();
    		new InventoryPickaxe(ist, true).writeSpell(0, spell);
    		list.add(ist);
    	}
    }*/

    public boolean canItemEditBlocks()
    {
        return true;
    }

    /* =========================================================== FORGE START ===============================================================*/

    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return true;
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	int[] xyzi = getAroundUpgrader(world, x, y, z);
    	if(xyzi == null) return false;

    	Minecraft mc = Minecraft.getMinecraft();

    	mc.playerController.onPlayerRightClick(player, world, stack, xyzi[0], xyzi[1], xyzi[2], side, mc.objectMouseOver.hitVec);

    	return true;
    }

    private int[] getAroundUpgrader(World world, int x, int y, int z)
	{
    	for(int i = 0; i < 6; i++)
    	{
    		ForgeDirection dir = ForgeDirection.getOrientation(i);

    		int lx = x + dir.offsetX;
    		int ly = y + dir.offsetY;
    		int lz = z + dir.offsetZ;

    		if(world.isAirBlock(lx, ly, lz)) continue;

    		int id = world.getBlockId(lx, ly, lz);

    		if(Block.blocksList[id] instanceof BlockUpgrader)
    		{
    			return new int[]{ lx, ly, lz, id };
    		}
    	}
    	return null;
	}

	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
    {
        return Strategy.get().onBlockStartBreak(itemstack, X, Y, Z, player);
    }

    public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count)
    {
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return false;
    }

    public int getRenderPasses(int metadata)
    {
        return requiresMultipleRenderPasses() ? 2 : 1;
    }

    public ItemStack getContainerItemStack(ItemStack itemStack)
    {
        if (!hasContainerItem())
        {
            return null;
        }
        return new ItemStack(getContainerItem());
    }

    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 6000;
    }

    public boolean hasCustomEntity(ItemStack stack)
    {
        return false;
    }

    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
        return null;
    }

    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        return false;
    }

    public CreativeTabs[] getCreativeTabs()
    {
        List<CreativeTabs> list = new LinkedList<CreativeTabs>();
        list.add(FPR2.Tab);
        list.add(CreativeTabs.tabCombat);
        return list.toArray(new CreativeTabs[0]);
    }

    public float getSmeltingExperience(ItemStack item)
    {
        return -1; //-1 will default to the old lookups.
    }

    public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original)
    {
        return original;
    }

    public boolean shouldPassSneakingClickToBlock(World par2World, int par4, int par5, int par6)
    {
        return false;
    }

    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {
    }

    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
    {
    	return false;
    }

    public boolean isPotionIngredient(ItemStack stack)
    {
        return isPotionIngredient();
    }

    public String getPotionEffect(ItemStack stack)
    {
        return getPotionEffect();
    }

    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2)
    {
        return true;
    }

    @Deprecated //Need to find a new place to hook this
    public float getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
    {
        return 0.0F; //getDamageVsEntity(par1Entity);
    }

    @Deprecated //Replaced with more useful version below
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
        return null;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return getArmorTexture(stack, entity, slot, (slot == 2 ? 2 : 1));
    }

    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
        return null;
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY)
    {
    }

    public int getMaxDamage(ItemStack stack)
    {
        return getMaxDamage();
    }

    public boolean isDamaged(ItemStack stack)
    {
        return super.isDamaged(stack);
    }

    public void setDamage(ItemStack stack, int damage)
    {
        super.setDamage(stack, damage);
    }

    public boolean canHarvestBlock(Block par1Block, ItemStack itemStack)
    {
        return canHarvestBlock(par1Block);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack ist, int pass)
    {
        return super.hasEffect(ist) || isInvHasEffect(ist);
    }

	private boolean isInvHasEffect(ItemStack ist)
	{
		InventoryPickaxe inp = new InventoryPickaxe(ist, false);
		return inp.hasEffect();
	}

	public void damageItem(ItemStack ist, EntityLivingBase user)
	{
		InventoryPickaxe inp = new InventoryPickaxe(ist, false);
		if(inp.canDecreaseDamage(ist)) return;

		ist.damageItem(1, user);
	}
}
