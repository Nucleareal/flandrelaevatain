package flandrelaevatain;

import net.minecraft.src.nucleareal.NCore;

public enum ReflectionLaevatainHelper
{
	RenderItem("itemRenderer", ""),
	ToolMaterial("toolMaterial", "field_77862_b"),
	;

	private String named;
	private String deobfName;

	private ReflectionLaevatainHelper(String fieldName, String deobfName)
	{
		this.named = fieldName;
		this.deobfName = deobfName;
	}

	public String getFieldName()
	{
		return NCore.getIsDebugMode() ? named : deobfName;
	}
}
