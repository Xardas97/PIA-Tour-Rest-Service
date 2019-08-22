package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PiaConfig.class)
public class ReservationServiceTest {

    private static final String USERNAME = "username";
    private static final int USER_ID = 5;
    private static final int RES_ID = 6;

    @Mock
    ReservationRepo reservationRepo;

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void findNotAllowedTest() {
        User user = new User();
        user.setUsername(USERNAME);

        User differentUser = new User();
        differentUser.setUsername(USERNAME + "1");

        Reservation reservation = new Reservation();
        reservation.setId(RES_ID);
        reservation.setClient(user);

        Mockito.when(reservationRepo.findOne(RES_ID)).thenReturn(reservation);

        Assert.assertNull(reservationService.find(USER_ID+1, RES_ID));

        Mockito.verify(reservationRepo, Mockito.times(1)).findOne(RES_ID);
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @Test
    public void findAllowedTest() {
        User user = new User();
        user.setId(USER_ID);

        Reservation reservation = new Reservation();
        reservation.setId(RES_ID);
        reservation.setClient(user);

        Mockito.when(reservationRepo.findOne(RES_ID)).thenReturn(reservation);
        Assert.assertEquals(reservation, reservationService.find(USER_ID, RES_ID));

        Mockito.verify(reservationRepo, Mockito.times(1)).findOne(RES_ID);
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @Test
    public void deleteTest(){
        reservationService.delete(USER_ID, RES_ID);

        Mockito.verify(reservationRepo, Mockito.times(1)).deleteByClientAndId(USER_ID, RES_ID);
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @Test
    public void findByClientTest(){
        List<Reservation> expected = Arrays.asList(new Reservation(), new Reservation());

        Mockito.when(reservationRepo.findByClient(USER_ID)).thenReturn(expected);

        Assert.assertEquals(expected, reservationService.findByClient(USER_ID));

        Mockito.verify(reservationRepo, Mockito.times(1)).findByClient(USER_ID);
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @Test
    public void saveImpossibleTest(){
        Departure departure = new Departure();
        User client = new User();

        Reservation unsaved = new Reservation(departure, client);
        Reservation saved = new Reservation(departure, client);
        saved.setId(RES_ID);

        Mockito.when(reservationRepo.findByDepartureAndClient(departure, client)).thenReturn(new Reservation());

        Assert.assertNull(reservationService.save(unsaved));

        Mockito.verify(reservationRepo, Mockito.times(1)).findByDepartureAndClient(departure, client);
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @Test
    public void savePossibleTest(){
        Departure departure = new Departure();
        User client = new User();

        Reservation unsaved = new Reservation(departure, client);
        Reservation saved = new Reservation(departure, client);
        saved.setId(RES_ID);

        Mockito.when(reservationRepo.findByDepartureAndClient(departure, client)).thenReturn(null);
        Mockito.when(reservationRepo.save(unsaved)).thenReturn(saved);

        Assert.assertEquals(saved, reservationService.save(unsaved));

        Mockito.verify(reservationRepo, Mockito.times(1)).findByDepartureAndClient(departure, client);
        Mockito.verify(reservationRepo, Mockito.times(1)).save(unsaved);
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
}
