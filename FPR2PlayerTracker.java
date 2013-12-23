package flandrelaevatain;

import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.asn1.x509.UserNotice;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;
import flandrelaevatain.item.entity.EntityTorchState;

public class FPR2PlayerTracker implements IPlayerTracker
{
	private static Map<String, EntityTorchState> entityMap;
	static
	{
		entityMap = new HashMap<String, EntityTorchState>();
	}

	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
		dimensionalChange(player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player)
	{
		/**if(entityMap.containsKey(player.username))
		{
			EntityTorchState entity = entityMap.get(player.username);
			if(entity != null && entity.isEntityAlive())
			{
				entity.setDeadByFlandre();
			}
			entityMap.remove(player.username);
		}*/
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player)
	{
		dimensionalChange(player);
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player)
	{
		dimensionalChange(player);
	}

	private void dimensionalChange(EntityPlayer player)
	{
		if(player.dimension != 0 && entityMap.containsKey(player.username))
		{
			EntityTorchState entity = entityMap.get(player.username);
			if(entity != null && entity.isEntityAlive())
			{
				entity.setDeadByFlandre();
			}
			entityMap.remove(player.username);
		}
		else
		if(player.dimension == 0 && !entityMap.containsKey(player.username))
		{
			EntityTorchState entity = new EntityTorchState(player.worldObj);
			entity.setPosition(player.posX, player.posY, player.posZ);
			player.worldObj.spawnEntityInWorld(entity);

			entityMap.put(player.username, entity);
		}
	}
}
