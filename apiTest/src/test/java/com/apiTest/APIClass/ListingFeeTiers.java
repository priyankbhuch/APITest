package com.apiTest.APIClass;

public class ListingFeeTiers {
	private String FixedFee;

    private String MinimumTierPrice;

    public String getFixedFee ()
    {
        return FixedFee;
    }

    public void setFixedFee (String FixedFee)
    {
        this.FixedFee = FixedFee;
    }

    public String getMinimumTierPrice ()
    {
        return MinimumTierPrice;
    }

    public void setMinimumTierPrice (String MinimumTierPrice)
    {
        this.MinimumTierPrice = MinimumTierPrice;
    }

    @Override
    public String toString()
    {
        return "[FixedFee = "+FixedFee+", MinimumTierPrice = "+MinimumTierPrice+"]";
    }

}
