package com.endava.mmarko.pia.config;

abstract class DataSourceConfigParams {
    final String driver;
    final String url;
    final String username;
    final String password;

    public DataSourceConfigParams(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
