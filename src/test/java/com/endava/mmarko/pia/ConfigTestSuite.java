package com.endava.mmarko.pia;

import com.endava.mmarko.pia.config.MySQLTests;
import com.endava.mmarko.pia.config.SQLServerTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MySQLTests.class,
        SQLServerTests.class
})
public class ConfigTestSuite {
}
