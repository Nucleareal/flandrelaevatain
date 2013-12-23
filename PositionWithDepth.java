package flandrelaevatain;

import net.minecraft.src.nucleareal.Position;

public class PositionWithDepth
{
	public Position pos;
	public int depth;

	public static PositionWithDepth of(int x, int y, int z, int i)
	{
		return of(new Position(x, y, z), i);
	}

	public static PositionWithDepth of(Position pos, int i)
	{
		PositionWithDepth posW = new PositionWithDepth();
		posW.pos = pos;
		posW.depth = i;
		return posW;
	}
}
