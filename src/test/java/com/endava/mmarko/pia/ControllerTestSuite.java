package com.endava.mmarko.pia;

import com.endava.mmarko.pia.controllers.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DepartureControllerTest.class,
        GuideControllerTest.class,
        ReservationControllerTest.class,
        TourControllerTest.class,
        UserControllerTest.class,
        LoginControllerTest.class,
        HomeControllerTest.class
})
public class ControllerTestSuite {
}
