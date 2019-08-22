package com.endava.mmarko.pia;

import com.endava.mmarko.pia.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DepartureServiceTest.class,
        GuideServiceTest.class,
        ReservationServiceTest.class,
        TourServiceTest.class,
        UserServiceTest.class
})
public class ServiceTestSuite {
}
