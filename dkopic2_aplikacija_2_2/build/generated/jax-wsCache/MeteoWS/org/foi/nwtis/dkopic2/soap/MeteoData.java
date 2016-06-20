
package org.foi.nwtis.dkopic2.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for meteoData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meteoData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="address" type="{http://soap.dkopic2.nwtis.foi.org/}address" minOccurs="0"/&gt;
 *         &lt;element name="downloadTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="humidityValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="lastUpdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="pressureValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="temperatureMax" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="temperatureMin" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="temperatureValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="visibility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="weatherValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="windDirectionValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="windSpeedValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meteoData", propOrder = {
    "address",
    "downloadTime",
    "humidityValue",
    "lastUpdate",
    "pressureValue",
    "temperatureMax",
    "temperatureMin",
    "temperatureValue",
    "visibility",
    "weatherValue",
    "windDirectionValue",
    "windSpeedValue"
})
public class MeteoData {

    protected Address address;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar downloadTime;
    protected Double humidityValue;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdate;
    protected Double pressureValue;
    protected Double temperatureMax;
    protected Double temperatureMin;
    protected Double temperatureValue;
    protected String visibility;
    protected String weatherValue;
    protected Double windDirectionValue;
    protected Double windSpeedValue;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the downloadTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDownloadTime() {
        return downloadTime;
    }

    /**
     * Sets the value of the downloadTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDownloadTime(XMLGregorianCalendar value) {
        this.downloadTime = value;
    }

    /**
     * Gets the value of the humidityValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHumidityValue() {
        return humidityValue;
    }

    /**
     * Sets the value of the humidityValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHumidityValue(Double value) {
        this.humidityValue = value;
    }

    /**
     * Gets the value of the lastUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the value of the lastUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdate(XMLGregorianCalendar value) {
        this.lastUpdate = value;
    }

    /**
     * Gets the value of the pressureValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPressureValue() {
        return pressureValue;
    }

    /**
     * Sets the value of the pressureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPressureValue(Double value) {
        this.pressureValue = value;
    }

    /**
     * Gets the value of the temperatureMax property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTemperatureMax() {
        return temperatureMax;
    }

    /**
     * Sets the value of the temperatureMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTemperatureMax(Double value) {
        this.temperatureMax = value;
    }

    /**
     * Gets the value of the temperatureMin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTemperatureMin() {
        return temperatureMin;
    }

    /**
     * Sets the value of the temperatureMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTemperatureMin(Double value) {
        this.temperatureMin = value;
    }

    /**
     * Gets the value of the temperatureValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTemperatureValue() {
        return temperatureValue;
    }

    /**
     * Sets the value of the temperatureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTemperatureValue(Double value) {
        this.temperatureValue = value;
    }

    /**
     * Gets the value of the visibility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * Sets the value of the visibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisibility(String value) {
        this.visibility = value;
    }

    /**
     * Gets the value of the weatherValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeatherValue() {
        return weatherValue;
    }

    /**
     * Sets the value of the weatherValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeatherValue(String value) {
        this.weatherValue = value;
    }

    /**
     * Gets the value of the windDirectionValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWindDirectionValue() {
        return windDirectionValue;
    }

    /**
     * Sets the value of the windDirectionValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWindDirectionValue(Double value) {
        this.windDirectionValue = value;
    }

    /**
     * Gets the value of the windSpeedValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWindSpeedValue() {
        return windSpeedValue;
    }

    /**
     * Sets the value of the windSpeedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWindSpeedValue(Double value) {
        this.windSpeedValue = value;
    }

}
