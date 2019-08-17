package com.ferick.alexander;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestPath;
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

    public List<Contract> getContracts() {
        return contractServiceWithJsonResponse().getContracts();
    }

    public String addContract(Contract contract) {
        return contractServiceWithDefaultTypeResponse().addContract(contract);
    }

    public String deleteContracts() {
        return contractServiceWithDefaultTypeResponse().deleteContracts();
    }

    public String deleteContract(RequestPath requestPath) {
        return contractServiceWithDefaultTypeResponse().deleteContract(requestPath);
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
}
