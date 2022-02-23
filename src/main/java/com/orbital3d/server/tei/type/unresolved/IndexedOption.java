package com.orbital3d.server.tei.type.unresolved;

/**
 * Indexed option intended mainly to be used in question template. Reason for
 * this class is that Triple in the Apache Commons lang3 cannot be resolved as a
 * type and too lazy to figure out why.
 * 
 * @author msiren
 *
 */
public final class IndexedOption
{
	private Integer index;
	private String value;
	private String text;

	private IndexedOption(Integer index, String value, String text)
	{
		this.index = index;
		this.value = value;
		this.text = text;
	}

	public Integer getIndex()
	{
		return index;
	}

	public String getValue()
	{
		return value;
	}

	public String getText()
	{
		return text;
	}

	public static IndexedOption of(Integer index, String value, String text)
	{
		if (index == null)
		{
			index = Integer.valueOf(0);
		}
		return new IndexedOption(index, value, text);
	}
}
