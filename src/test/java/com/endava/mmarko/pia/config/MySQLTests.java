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
@ActiveProfiles("dev")
public class MySQLTests {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bgwalkingtours";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Autowired
    DataSourceConfigParams dataSourceConfigParams;

    @Test
    public void successfulConnectionTest() {
        Assert.assertEquals(DRIVER, dataSourceConfigParams.driver);
        Assert.assertEquals(URL, dataSourceConfigParams.url);
        Assert.assertEquals(USERNAME, dataSourceConfigParams.username);
        Assert.assertEquals(PASSWORD, dataSourceConfigParams.password);
    }
}
