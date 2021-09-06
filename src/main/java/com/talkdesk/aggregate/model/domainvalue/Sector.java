package com.talkdesk.aggregate.model.domainvalue;

/**
* <h1>Sector</h1>
* This enum represents a Sector of a Phone.
*
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-15
*/
public enum Sector {

	Technology("Technology"),
    Banking("Banking"),
    Clothing("Clothing");

    private final String description;

    Sector(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
