package parksw.app.member.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * Address
 * author: sinuki
 * createdAt: 2019/11/10
 **/
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
