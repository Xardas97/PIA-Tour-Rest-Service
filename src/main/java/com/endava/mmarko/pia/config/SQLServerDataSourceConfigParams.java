package com.endava.mmarko.pia.config;

public class SQLServerDataSourceConfigParams extends DataSourceConfigParams {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://walkingtours-server.database.windows.net:1433;database=walkingtours";
    private static final String USERNAME = "mm160633d";
    private static final String PASSWORD = "WT,rt:01";

    public SQLServerDataSourceConfigParams() {
        super(DRIVER, URL, USERNAME, PASSWORD);
    }
}
