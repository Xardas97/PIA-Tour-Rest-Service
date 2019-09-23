package com.endava.mmarko.pia.config;

class MySqlDataSourceConfigParams extends DataSourceConfigParams {
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String URL = "jdbc:mysql://localhost:3306/bgwalkingtours";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "root";

  MySqlDataSourceConfigParams() {
    super(DRIVER, URL, USERNAME, PASSWORD);
  }
}
