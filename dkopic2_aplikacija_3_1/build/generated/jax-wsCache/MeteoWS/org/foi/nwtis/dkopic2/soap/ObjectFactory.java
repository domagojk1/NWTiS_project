
package org.foi.nwtis.dkopic2.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.foi.nwtis.dkopic2.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAddressList_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getAddressList");
    private final static QName _GetAddressListResponse_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getAddressListResponse");
    private final static QName _GetInterval_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getInterval");
    private final static QName _GetIntervalResponse_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getIntervalResponse");
    private final static QName _GetLast_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getLast");
    private final static QName _GetLastResponse_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getLastResponse");
    private final static QName _GetMeteo_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getMeteo");
    private final static QName _GetMeteoResponse_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getMeteoResponse");
    private final static QName _GetRank_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getRank");
    private final static QName _GetRankResponse_QNAME = new QName("http://soap.dkopic2.nwtis.foi.org/", "getRankResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.foi.nwtis.dkopic2.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAddressList }
     * 
     */
    public GetAddressList createGetAddressList() {
        return new GetAddressList();
    }

    /**
     * Create an instance of {@link GetAddressListResponse }
     * 
     */
    public GetAddressListResponse createGetAddressListResponse() {
        return new GetAddressListResponse();
    }

    /**
     * Create an instance of {@link GetInterval }
     * 
     */
    public GetInterval createGetInterval() {
        return new GetInterval();
    }

    /**
     * Create an instance of {@link GetIntervalResponse }
     * 
     */
    public GetIntervalResponse createGetIntervalResponse() {
        return new GetIntervalResponse();
    }

    /**
     * Create an instance of {@link GetLast }
     * 
     */
    public GetLast createGetLast() {
        return new GetLast();
    }

    /**
     * Create an instance of {@link GetLastResponse }
     * 
     */
    public GetLastResponse createGetLastResponse() {
        return new GetLastResponse();
    }

    /**
     * Create an instance of {@link GetMeteo }
     * 
     */
    public GetMeteo createGetMeteo() {
        return new GetMeteo();
    }

    /**
     * Create an instance of {@link GetMeteoResponse }
     * 
     */
    public GetMeteoResponse createGetMeteoResponse() {
        return new GetMeteoResponse();
    }

    /**
     * Create an instance of {@link GetRank }
     * 
     */
    public GetRank createGetRank() {
        return new GetRank();
    }

    /**
     * Create an instance of {@link GetRankResponse }
     * 
     */
    public GetRankResponse createGetRankResponse() {
        return new GetRankResponse();
    }

    /**
     * Create an instance of {@link MeteoData }
     * 
     */
    public MeteoData createMeteoData() {
        return new MeteoData();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Geolocation }
     * 
     */
    public Geolocation createGeolocation() {
        return new Geolocation();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Rank }
     * 
     */
    public Rank createRank() {
        return new Rank();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAddressList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getAddressList")
    public JAXBElement<GetAddressList> createGetAddressList(GetAddressList value) {
        return new JAXBElement<GetAddressList>(_GetAddressList_QNAME, GetAddressList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAddressListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getAddressListResponse")
    public JAXBElement<GetAddressListResponse> createGetAddressListResponse(GetAddressListResponse value) {
        return new JAXBElement<GetAddressListResponse>(_GetAddressListResponse_QNAME, GetAddressListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInterval }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getInterval")
    public JAXBElement<GetInterval> createGetInterval(GetInterval value) {
        return new JAXBElement<GetInterval>(_GetInterval_QNAME, GetInterval.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntervalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getIntervalResponse")
    public JAXBElement<GetIntervalResponse> createGetIntervalResponse(GetIntervalResponse value) {
        return new JAXBElement<GetIntervalResponse>(_GetIntervalResponse_QNAME, GetIntervalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLast }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getLast")
    public JAXBElement<GetLast> createGetLast(GetLast value) {
        return new JAXBElement<GetLast>(_GetLast_QNAME, GetLast.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getLastResponse")
    public JAXBElement<GetLastResponse> createGetLastResponse(GetLastResponse value) {
        return new JAXBElement<GetLastResponse>(_GetLastResponse_QNAME, GetLastResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMeteo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getMeteo")
    public JAXBElement<GetMeteo> createGetMeteo(GetMeteo value) {
        return new JAXBElement<GetMeteo>(_GetMeteo_QNAME, GetMeteo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMeteoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getMeteoResponse")
    public JAXBElement<GetMeteoResponse> createGetMeteoResponse(GetMeteoResponse value) {
        return new JAXBElement<GetMeteoResponse>(_GetMeteoResponse_QNAME, GetMeteoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRank }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getRank")
    public JAXBElement<GetRank> createGetRank(GetRank value) {
        return new JAXBElement<GetRank>(_GetRank_QNAME, GetRank.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRankResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.dkopic2.nwtis.foi.org/", name = "getRankResponse")
    public JAXBElement<GetRankResponse> createGetRankResponse(GetRankResponse value) {
        return new JAXBElement<GetRankResponse>(_GetRankResponse_QNAME, GetRankResponse.class, null, value);
    }

}
