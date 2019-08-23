package com.ferick.alexander;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import java.net.URL;
import java.util.List;

public class PliantrespClient {

    private static final String DEFAULT_SCHEME = "http";
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 4567;
    private static final String ADDRESS_TEMPLATE = "%s://%s:%d";

    private String scheme;
    private String host;
    private int port;
    private String serverUrl;

    public PliantrespClient() {
        this.scheme = DEFAULT_SCHEME;
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, scheme, host, port);
    }

    public PliantrespClient(String scheme, String host, int port) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, scheme, host, port);
    }

    public PliantrespClient(String host, int port) {
        this.scheme = DEFAULT_SCHEME;
        this.host = host;
        this.port = port;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, scheme, host, port);
    }

    public PliantrespClient(String host) {
        this.scheme = DEFAULT_SCHEME;
        this.host = host;
        this.port = DEFAULT_PORT;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, scheme, host, port);
    }

    public PliantrespClient(int port) {
        this.scheme = DEFAULT_SCHEME;
        this.host = DEFAULT_HOST;
        this.port = port;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, scheme, host, port);
    }

    public PliantrespClient(URL url) {
        this.scheme = url.getProtocol();
        this.host = url.getHost();
        this.port = url.getPort();
        this.serverUrl = String.format(ADDRESS_TEMPLATE, scheme, host, port);
    }

    /**
     * Method gets all contracts 'request-response' stored on standalone server
     *
     * @return contract list
     */
    public List<Contract> getContracts() {
        return contractServiceWithJsonResponse().getContracts();
    }

    /**
     * Method stores contract 'request-response' on standalone server
     * If there is already a stored contract matching it, then overwrites it
     *
     * @param contract 'request-response'
     * @return string message whether contract is stored or not
     */
    public String addContract(Contract contract) {
        return contractServiceWithDefaultTypeResponse().addContract(contract);
    }

    /**
     * Method deletes all contracts 'request-response' stored on standalone server
     *
     * @return string message whether contracts are deleted or not
     */
    public String deleteContracts() {
        return contractServiceWithDefaultTypeResponse().deleteContracts();
    }

    /**
     * Method deletes contract 'request-response' stored on standalone server,
     * matching path and request method
     *
     * @param request containing path and request method
     * @return string message whether contract is deleted or not
     */
    public String deleteContract(RequestDTO request) {
        return contractServiceWithDefaultTypeResponse().deleteContract(request);
    }

    /**
     * Method gets all requests stored on standalone server
     *
     * @return request list
     */
    public List<RequestDTO> getRequests() {
        return requestServiceWithJsonResponse().getRequests();
    }

    /**
     * Method gets request stored on standalone server,
     * matching path and request method
     *
     * @param request containing path and request method
     * @return request json or message that
     * there is no suitable request in the storage
     */
    public RequestDTO getRequest(RequestDTO request) {
        return requestServiceWithJsonResponse().getRequest(request);
    }

    /**
     * Method deletes all requests stored on standalone server
     *
     * @return string message whether requests are deleted or not
     */
    public String deleteRequests() {
        return requestServiceWithDefaultTypeResponse().deleteRequests();
    }

    private ContractEndPoints contractServiceWithJsonResponse() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ContractEndPoints.class, serverUrl);
    }

    private ContractEndPoints contractServiceWithDefaultTypeResponse() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .target(ContractEndPoints.class, serverUrl);
    }

    private RequestEndPoints requestServiceWithJsonResponse() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RequestEndPoints.class, serverUrl);
    }

    private RequestEndPoints requestServiceWithDefaultTypeResponse() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .target(RequestEndPoints.class, serverUrl);
    }
}
