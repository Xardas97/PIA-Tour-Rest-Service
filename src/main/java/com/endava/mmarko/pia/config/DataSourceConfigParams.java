package com.endava.mmarko.pia.config;

class DataSourceConfigParams {
    public enum DataSourceType{MySQL, SQLServer}

    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/bgwalkingtours";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "root";
    private static final String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String SQL_SERVER_URL = "jdbc:sqlserver://walkingtours-server.database.windows.net:1433;database=walkingtours";
    private static final String SQL_SERVER_USERNAME = "mm160633d";
    private static final String SQL_SERVER_PASSWORD = "WT,rt:01";

    final String driver;
    final String url;
    final String username;
    final String password;

    public DataSourceConfigParams(DataSourceType dataSourceType) {
        switch(dataSourceType){
            case MySQL:
                driver = MYSQL_DRIVER;
                url = MYSQL_URL;
                username = MYSQL_USERNAME;
                password = MYSQL_PASSWORD;
                break;
            case SQLServer:
                driver = SQL_SERVER_DRIVER;
                url = SQL_SERVER_URL;
                username = SQL_SERVER_USERNAME;
                password = SQL_SERVER_PASSWORD;
                break;
            default: throw new AssertionError();
        }
    }
}
