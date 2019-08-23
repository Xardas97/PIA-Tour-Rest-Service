package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.repositories.DepartureRepo;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import com.endava.mmarko.pia.services.DepartureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PiaConfig.class)
@ActiveProfiles("dev")
public class DepartureServiceTest {
    private static final int ID = 5;

    @Mock
    DepartureRepo departureRepo;

    @Mock
    ReservationRepo reservationRepo;

    @InjectMocks
    DepartureService departureService;

    @Test
    public void findTest(){
        Departure expected = new Departure();
        expected.setId(ID);

        when(departureRepo.findOne(ID)).thenReturn(expected);

        assertEquals(expected, departureService.find(ID));

        verify(departureRepo, times(1)).findOne(ID);
        verifyNoMoreInteractions(departureRepo);
    }

    @Test
    public void findAllTest() {
        List<Departure> expected = Arrays.asList(new Departure(), new Departure());

        when(departureRepo.findAll()).thenReturn(expected);

        assertEquals(expected, departureService.findAll());

        verify(departureRepo, times(1)).findAll();
        verifyNoMoreInteractions(departureRepo);
    }

    @Test
    public void saveNotPossibleTest() {
        Departure unsaved = new Departure();

        when(departureRepo.findByGuideAndDate(any(), any())).thenReturn(new Departure());

        assertEquals(null, departureService.save(unsaved));

        verify(departureRepo, times(1)).findByGuideAndDate(any(), any());
        verifyNoMoreInteractions(departureRepo);
    }

    @Test
    public void savePossibleTest() {
        Departure unsaved = new Departure();
        Departure saved = new Departure();
        saved.setId(ID);

        when(departureRepo.findByGuideAndDate(any(), any())).thenReturn(null);
        when(departureRepo.save(unsaved)).thenReturn(saved);

        assertEquals(saved, departureService.save(unsaved));

        verify(departureRepo, times(1)).findByGuideAndDate(any(), any());
        verify(departureRepo, times(1)).save(unsaved);
        verifyNoMoreInteractions(departureRepo);
    }

    @Test
    public void updateTest() {
        Departure departure = new Departure();

        when(departureRepo.save(departure)).thenReturn(departure);

        assertEquals(departure, departureService.update(departure));

        verify(departureRepo, times(1)).save(departure);
        verifyNoMoreInteractions(departureRepo);
    }

    @Test
    public void deleteTest(){
        departureService.delete(ID);
        verify(departureRepo, times(1)).delete(ID);
        verifyNoMoreInteractions(departureRepo);
    }

    @Test
    public void findByGuideTest(){
        Tour tour = new Tour();
        Guide guide = new Guide();
        Date date = new Date();
        List<Departure> expected = Arrays.asList(new Departure(tour, guide, date), new Departure(tour, guide, date));

        when(departureRepo.findByGuide(ID)).thenReturn(expected);

        assertEquals(expected, departureService.findByGuide(ID));

        verify(departureRepo, times(1)).findByGuide(ID);
        verifyNoMoreInteractions(departureRepo);
    }

    @Test(expected = ResourceNotFoundError.class)
    public void hasEnoughPeopleResourceNotFoundTest() {
        when(departureRepo.findOne(ID)).thenReturn(null);
        departureService.hasEnoughPeople(ID);
    }

    @Test
    public void hasEnoughPeopleNotEnoughTest() {
        assertFalse(hasEnoughPeopleTest(-1));
    }

    @Test
    public void hasEnoughPeopleMoreThenEnoughTest() {
        assertTrue(hasEnoughPeopleTest(1));
    }

    @Test
    public void hasEnoughPeopleHasEnoughTest() {
        assertTrue(hasEnoughPeopleTest(0));
    }

    private boolean hasEnoughPeopleTest(int reservationChange) {
        int minPeople = 5;
        int numOfReservations = minPeople + reservationChange;

        Tour tour = new Tour();
        tour.setMinPeople(minPeople);
        Guide guide = new Guide();

        Departure departure = new Departure();
        departure.setTour(tour);
        departure.setGuide(guide);

        when(departureRepo.findOne(ID)).thenReturn(departure);
        when(reservationRepo.countByDeparture(departure)).thenReturn(numOfReservations);

        boolean hasEnoughPeople = departureService.hasEnoughPeople(ID);

        verify(departureRepo, times(1)).findOne(ID);
        verify(reservationRepo, times(1)).countByDeparture(departure);
        verifyNoMoreInteractions(departureRepo);
        verifyNoMoreInteractions(reservationRepo);

        return hasEnoughPeople;
    }

    @Before
    public void init() {
         MockitoAnnotations.initMocks(this);
     }
}
