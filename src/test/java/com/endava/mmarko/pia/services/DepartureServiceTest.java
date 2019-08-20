package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.models.TourGuide;
import com.endava.mmarko.pia.repositories.DepartureRepo;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PiaConfig.class)
public class DepartureServiceTest {
    @Mock
    DepartureRepo departureRepo;

    @Mock
    ReservationRepo reservationRepo;

    @InjectMocks
    DepartureService departureService;

    @Test
    public void findTest(){
        int id = 5;
        Departure expected = new Departure(); expected.setId(id);

        when(departureRepo.findOne(id)).thenReturn(expected);

        assertEquals(expected.getId(), departureService.find(id).getId());
    }

    @Test
    public void findAllTest() {
        List<Departure> expected = new LinkedList<>();
        expected.add(new Departure()); expected.add(new Departure());

        when(departureRepo.findAll()).thenReturn(expected);

        assertEquals(departureService.findAll(), expected);
}

    @Test
    public void saveImpossibleTest() {
        Departure unsaved = new Departure();

        Departure saved = new Departure();
        saved.setId(10);

        when(departureRepo.findByGuideAndDate(any(), any())).thenReturn(new Departure());
        when(departureRepo.save(unsaved)).thenReturn(saved);

        assertEquals(departureService.save(unsaved), null);
    }

    @Test
    public void savePossibleTest() {
        Departure unsaved = new Departure();

        Departure saved = new Departure();
        saved.setId(10);

        when(departureRepo.findByGuideAndDate(any(), any())).thenReturn(null);
        when(departureRepo.save(unsaved)).thenReturn(saved);

        assertEquals(departureService.save(unsaved), saved);
    }

    @Test
    public void updateTest() {

        Departure departure = new Departure();

        when(departureRepo.save(departure)).thenReturn(departure);

        assertEquals(departureService.update(departure), departure);
    }

    @Test
    public void deleteTest(){
        departureService.delete(1);
        verify(departureRepo, times(1)).delete(1);
        verifyNoMoreInteractions(departureRepo);
        //v seems to the the same as the "noMoreInteractions" method, fixed in later versions ??
        //verifyZeroInteractions(departureRepo);
    }

    @Test
    public void findByClientTest(){
        TourGuide guide = new TourGuide();
        String guideName = "guide";
        Departure dep1 = new Departure(); Departure dep2 = new Departure();
        dep1.setGuide(guide); dep2.setGuide(guide);
        List<Departure> expected = new LinkedList<>(); expected.add(dep1); expected.add(dep2);

        when(departureRepo.findByGuide(guideName)).thenReturn(expected);

        assertEquals(departureService.findByGuide(guideName), expected);
    }

    @Test(expected = ResourceNotFoundError.class)
    public void hasEnoughPeopleResourceNotFoundTest() {
        int id = 5;

        when(departureRepo.findOne(id)).thenReturn(null);
        when(reservationRepo.countByDeparture(any())).thenReturn(1);

        departureService.hasEnoughPeople(id);
    }

    @Test
    public void hasEnoughPeopleLessTest() {
        int id = 5;
        hasEnoughPeopleTest(id, -1);

        assertFalse(departureService.hasEnoughPeople(id));
    }

    @Test
    public void hasEnoughPeopleMoreTest() {
        int id = 5;
        hasEnoughPeopleTest(id, 1);

        assertTrue(departureService.hasEnoughPeople(id));
    }

    @Test
    public void hasEnoughPeopleEqualTest() {
        int id = 5;
        hasEnoughPeopleTest(id, 0);

        assertTrue(departureService.hasEnoughPeople(id));
    }

    private void hasEnoughPeopleTest(int id, int reservationChange) {
        int minPeople = 5; int reservations = minPeople + reservationChange;

        Tour tour = new Tour(); tour.setMinPeople(minPeople);
        TourGuide tourGuide = new TourGuide(); tourGuide.setTour(tour);
        Departure dep = new Departure(); dep.setGuide(tourGuide);

        when(departureRepo.findOne(id)).thenReturn(dep);
        when(reservationRepo.countByDeparture(dep)).thenReturn(reservations);
    }

    @Before
    public void init() {
         MockitoAnnotations.initMocks(this);
     }
}
