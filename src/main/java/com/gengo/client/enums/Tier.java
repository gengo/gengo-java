package com.gengo.client.enums;

public enum Tier
{
	MACHINE,
	STANDARD,
	PRO,
	ULTRA;
	
	public String toRequestString()
	{
	    return super.toString().toLowerCase();
	}
	
	public String toString()
	{
	    return toRequestString();
	}
	
};
