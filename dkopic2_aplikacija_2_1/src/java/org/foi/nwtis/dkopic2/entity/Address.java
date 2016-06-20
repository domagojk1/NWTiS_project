/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author domagoj
 */
@Entity
@Table(name = "DKOPIC2_ADRESE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findByIdadresa", query = "SELECT a FROM Address a WHERE a.idadresa = :idadresa"),
    @NamedQuery(name = "Address.findByAdresa", query = "SELECT a FROM Address a WHERE a.adresa = :adresa"),
    @NamedQuery(name = "Address.findByLatitude", query = "SELECT a FROM Address a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "Address.findByLongitude", query = "SELECT a FROM Address a WHERE a.longitude = :longitude")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDADRESA")
    private Integer idadresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ADRESA")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "LATITUDE")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "LONGITUDE")
    private String longitude;
    @JoinColumn(name = "KORISNIK", referencedColumnName = "ID")
    @ManyToOne
    private User korisnik;

    public Address() {
    }

    public Address(Integer idadresa) {
        this.idadresa = idadresa;
    }

    public Address(Integer idadresa, String adresa, String latitude, String longitude) {
        this.idadresa = idadresa;
        this.adresa = adresa;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdadresa() {
        return idadresa;
    }

    public void setIdadresa(Integer idadresa) {
        this.idadresa = idadresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public User getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(User korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idadresa != null ? idadresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.idadresa == null && other.idadresa != null) || (this.idadresa != null && !this.idadresa.equals(other.idadresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.dkopic2.entity.Address[ idadresa=" + idadresa + " ]";
    }
    
}
