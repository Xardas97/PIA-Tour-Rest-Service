package com.endava.mmarko.pia.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PiaConfig.class)
@ActiveProfiles("prod")
public class SQLServerTests {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://walkingtours-server.database.windows.net:1433;database=walkingtours";
    private static final String USERNAME = "mm160633d";
    private static final String PASSWORD = "WT,rt:01";

    @Autowired
    DataSourceConfigParams dataSourceConfigParams;

    @Test
    public void prodProfileLoadsSqlServerConfig() {
        Assert.assertEquals(DRIVER, dataSourceConfigParams.driver);
        Assert.assertEquals(URL, dataSourceConfigParams.url);
        Assert.assertEquals(USERNAME, dataSourceConfigParams.username);
        Assert.assertEquals(PASSWORD, dataSourceConfigParams.password);
    }
}
