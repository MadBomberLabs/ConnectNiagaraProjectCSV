package madbomberlabs.com.connectniagaraproject;

import java.io.Serializable;

public class Org
{
    private String id, org, firstName, lastName, email, phone, website, serviceType, service,
    streetAddress, city, state, zip, favorite;

    public Org(String id, String org, String firstName, String lastName, String email, String phone,
               String website, String serviceType, String service, String streetAddress,
               String city, String state, String zip, String favorite)
    {
        this.id = id;
        this.org = org;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.serviceType = serviceType;
        this.service = service;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.favorite = favorite;
    }

    public String getID()
    {
        return id;
    }
    public String getOrg()
    {
        return org;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPhone()
    {
        return phone;
    }
    public String getWebsite()
    {
        return website;
    }
    public String getServiceType()
    {
        return serviceType;
    }
    public String getService()
    {
        return service;
    }
    public String getStreetAddress()
    {
        return streetAddress;
    }
    public String getCity()
    {
        return city;
    }
    public String getState()
    {
        return state;
    }
    public String getZip()
    {
        return zip;
    }
    public String getFavorite()
    {
        return favorite;
    }

}
