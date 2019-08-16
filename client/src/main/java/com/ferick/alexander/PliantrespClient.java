package com.ferick.alexander;

import java.net.URL;

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
}
