package org.foi.nwtis.dkopic2.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.foi.nwtis.dkopic2.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-20T19:27:09")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> latitude;
    public static volatile SingularAttribute<Address, Integer> idadresa;
    public static volatile SingularAttribute<Address, String> adresa;
    public static volatile SingularAttribute<Address, String> longitude;
    public static volatile SingularAttribute<Address, User> korisnik;

}