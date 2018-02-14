package uc3m.practica;

/**
 * Created by Jaime-4 on 14/02/2018.
 */

public class Localizacion {
    public String street,city,state,postcode;

    public Localizacion(String street, String city, String state, String postcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }
}
