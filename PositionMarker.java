package flandrelaevatain;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.nucleareal.Position;

public class PositionMarker
{
	private PositionMarker()
	{
	}
	private static final PositionMarker instance = new PositionMarker();
	public static PositionMarker get()
	{
		return instance;
	}

	private Map<String, Boolean> map;

	public void init()
	{
		map = new HashMap<String, Boolean>();
	}

	public boolean addMark(Position pos, boolean value)
	{
		if(map.containsKey(pos.getVisualityValue()))
		{
			return false;
		}
		else
		{
			map.put(pos.getVisualityValue(), Boolean.valueOf(value));
			return true;
		}
	}

	public boolean getMarked(Position pos)
	{
		return map.containsKey(pos.getVisualityValue());
	}
}
