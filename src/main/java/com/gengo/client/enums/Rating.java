package com.gengo.client.enums;

/**
 * Translation rating
 */
public enum Rating
{
	ONE_STAR, TWO_STARS, THREE_STARS, FOUR_STARS, FIVE_STARS;
	
	public int getRatingValue()
	{
	    switch (this)
        {
            case ONE_STAR:
                return 1;
            case TWO_STARS:
                return 2;
            case THREE_STARS:
                return 3;
            case FOUR_STARS:
                return 4;
            case FIVE_STARS:
                return 5;
        }
	    return 0;
	}
	
	public String toString()
	{
	    return Integer.toString(getRatingValue());
	}
};
