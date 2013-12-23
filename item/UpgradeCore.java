package flandrelaevatain.item;

import java.util.Random;

import net.minecraft.item.ItemStack;

public enum UpgradeCore
{
	Abandoned(0, "曖昧な妖力の核"),
	Bane(1, "忌まれる力の核"),
	Collapse(2, "憂い生み出す核"),
	Destroy(3, "永遠の世界の核"),
	Elimination(4, "織り成す力の核"),
	Flandre(5, "フランドールの核"),
	;

	private static Random rand = new Random();

	private String jaJPName;
	private int power;

	private UpgradeCore(int power, String jaJPName)
	{
		this.power = power;
		this.jaJPName = jaJPName;
	}

	public int getPower()
	{
		return power;
	}

	public int getPowerReversed()
	{
		return Flandre.getPower() - getPower();
	}

	public boolean hasEffect()
	{
		return getPower() >= Flandre.getPower();
	}

	public static UpgradeCore of(int i)
	{
		return array[i];
	}

	public static int size()
	{
		return array.length;
	}

	private static UpgradeCore[] array;

	static
	{
		array = values();
	}

	public boolean canDecreaseDamage(ItemStack ist)
	{
		switch(getPower())
		{
			case 0: return rand.nextInt(32) == 0;
			case 1: return rand.nextInt(8)  == 0;
			case 2: return rand.nextInt(2)  == 0;
			case 3: return rand.nextInt(8)  != 0;
			case 4: return rand.nextInt(32) != 0;
			case 5: return true;
		}
		return false;
		//return rand.nextInt((int)Math.pow(2, getPowerReversed())) == 0;
	}

	public boolean canUpgrade(ItemStack ist)
	{
		return getPower() < Flandre.getPower();
	}

	public String getJaJPName()
	{
		return jaJPName;
	}
}
