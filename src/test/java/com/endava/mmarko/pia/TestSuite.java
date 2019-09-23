package com.endava.mmarko.pia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ControllerTestSuite.class,
        ServiceTestSuite.class
})
public class TestSuite {
}
