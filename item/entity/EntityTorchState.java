package flandrelaevatain.item.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;
import net.minecraft.src.nucleareal.DataContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTorchState extends Entity
{
	public EntityTorchState(World world)
	{
		super(world);
	}

    @Override
    protected void entityInit()
    {
        ignoreFrustumCheck = true;
        setSize(.5F, .6F);
        replace();
    }

    @Override
    public void onEntityUpdate()
    {
        replace();
    }

    @Override
    public void setDead()
    {
    }

    public void setDeadByFlandre()
    {
    	isDead = true;
    }

    @Override
    public void setFire(int seconds)
    {
    }

    @Override
    public boolean isBurning()
    {
        return false;
    }

    @Override
    public void mountEntity(Entity entity)
    {
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damage)
    {
        return false;
    }

    @Override
    public void moveEntity(double x, double y, double z)
    {
    }

    @Override
    public void applyEntityCollision(Entity entity)
    {
    }

    @Override
    public float getShadowSize()
    {
        return 0F;
    }

    @Override
    public boolean isOffsetPositionInLiquid(double x, double y, double z)
    {
        return false;
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt lb)
    {
    }

    @Override
    public boolean canAttackWithItem()
    {
        return false;
    }

    @Override
    public void setPosition(double x, double y, double z)
    {
        replace();
    }

    @Override
    public void setAngles(float yaw, float pitch)
    {
    }

    @Override
    public int getBrightnessForRender(float partialTickTime)
    {
        return 0xf00000;
    }

    @Override
    public float getBrightness(float partialTickTime)
    {
        return 0F;
    }

    private void replace()
    {
        Entity p = DataContainer.fetchClient().player;
        if(p != null)
        {
        	super.setPosition(p.posX, p.posY, p.posZ);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
    }
}
