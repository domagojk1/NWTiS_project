package org.foi.nwtis.dkopic2.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.foi.nwtis.dkopic2.entity.Address;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-20T19:27:09")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> ime;
    public static volatile SingularAttribute<User, String> prezime;
    public static volatile SingularAttribute<User, String> uloga;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> mail;
    public static volatile ListAttribute<User, Address> addressList;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, Integer> status;

}