package com.ferick.alexander;

import java.net.URL;

public class PliantrespClient {

    private static final String DEFAULT_PROTOCOL = "http";
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 4567;
    private static final String ADDRESS_TEMPLATE = "%s://%s:%d";

    private String protocol;
    private String host;
    private int port;
    private String serverUrl;

    public PliantrespClient() {
        this.protocol = DEFAULT_PROTOCOL;
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, protocol, host, port);
    }

    public PliantrespClient(String host, int port) {
        this.protocol = DEFAULT_PROTOCOL;
        this.host = host;
        this.port = port;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, protocol, host, port);
    }

    public PliantrespClient(String host) {
        this.protocol = DEFAULT_PROTOCOL;
        this.host = host;
        this.port = DEFAULT_PORT;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, protocol, host, port);
    }

    public PliantrespClient(int port) {
        this.protocol = DEFAULT_PROTOCOL;
        this.host = DEFAULT_HOST;
        this.port = port;
        this.serverUrl = String.format(ADDRESS_TEMPLATE, protocol, host, port);
    }

    public PliantrespClient(URL url) {
        this.protocol = url.getProtocol();
        this.host = url.getHost();
        this.port = url.getPort();
        this.serverUrl = String.format(ADDRESS_TEMPLATE, protocol, host, port);
    }
}
