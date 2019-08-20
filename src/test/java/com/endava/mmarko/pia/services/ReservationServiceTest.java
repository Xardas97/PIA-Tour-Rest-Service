package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PiaConfig.class)
public class ReservationServiceTest {

    @Mock
    ReservationRepo reservationRepo;

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void findNotAllowedTest() {
        int id = 5; String username = "username1";
        String differentUsername = "username2";

        Reservation res = new Reservation();
        res.setId(id); res.setClient(username);

        Mockito.when(reservationRepo.findOne(id)).thenReturn(res);
        Assert.assertEquals(reservationService.find(differentUsername, id), null);
    }

    @Test
    public void findAllowedTest() {
        int id = 5; String username = "username1";

        Reservation res = new Reservation();
        res.setId(id); res.setClient(username);

        Mockito.when(reservationRepo.findOne(id)).thenReturn(res);
        Assert.assertEquals(reservationService.find(username, id), res);
    }

    @Test
    public void deleteTest(){
        reservationService.delete("username", 1);
        Mockito.verify(reservationRepo, Mockito.times(1)).deleteByClientAndId("username", 1);
    }

    @Test
    public void findByClientTest(){
        String client = "client";
        Reservation res1 = new Reservation(); Reservation res2 = new Reservation();
        res1.setClient(client); res2.setClient(client);
        List<Reservation> expected = new LinkedList<>(); expected.add(res1); expected.add(res2);

        Mockito.when(reservationRepo.findByClient(client)).thenReturn(expected);

        Assert.assertEquals(reservationService.findByClient(client), expected);
    }

    @Test
    public void saveImpossibleTest(){
        Reservation unsaved = new Reservation();
        Reservation saved = new Reservation(); saved.setId(10);

        Mockito.when(reservationRepo.findByDepartureAndClient(Matchers.any(), Matchers.any())).thenReturn(new Reservation());
        Mockito.when(reservationRepo.save(unsaved)).thenReturn(saved);

        Assert.assertEquals(reservationService.save(unsaved), null);
    }

    @Test
    public void savePossibleTest(){
        Reservation unsaved = new Reservation();
        Reservation saved = new Reservation(); saved.setId(10);

        Mockito.when(reservationRepo.findByDepartureAndClient(Matchers.any(), Matchers.any())).thenReturn(null);
        Mockito.when(reservationRepo.save(unsaved)).thenReturn(saved);

        Assert.assertEquals(reservationService.save(unsaved), saved);
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
}
