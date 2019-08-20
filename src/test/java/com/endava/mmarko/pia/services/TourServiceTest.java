package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.repositories.TourRepo;
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
public class TourServiceTest {
    @Mock
    private TourRepo tourRepo;

    @InjectMocks
    private TourService tourService;

    @Test
    public void findByGuideTest() {
        List<Tour> expected = new LinkedList<>();
        expected.add(new Tour()); expected.add(new Tour());

        when(tourRepo.findByGuide("guide")).thenReturn(expected);

        assertEquals(tourService.findByGuide("guide"), expected);
    }

    @Test
    public void deleteTest(){
        tourService.delete(1);
        verify(tourRepo, times(1)).delete(1);
    }

    @Test
    public void findAllTest(){
        List<Tour> expected = new LinkedList<>();
        expected.add(new Tour()); expected.add(new Tour());

        when(tourRepo.findAll()).thenReturn(expected);

        assertEquals(tourService.findAll(), expected);
    }

    @Test
    public void findTest(){
        int id = 5;
        Tour expected = new Tour(); expected.setId(id);

        when(tourRepo.findOne(id)).thenReturn(expected);

        assertEquals(expected.getId(), tourService.find(id).getId());
    }

    @Test
    public void saveTest() {

        Tour unsavedTour = new Tour("name", "description", "point", 1);

        Tour savedTour = new Tour(unsavedTour.getName(), unsavedTour.getDescription(), unsavedTour.getMeetingPoint(), unsavedTour.getMinPeople());
        savedTour.setId(10);

        when(tourRepo.save(unsavedTour)).thenReturn(savedTour);

        Tour returnedTour = tourService.save(unsavedTour);
        assertEquals(returnedTour, savedTour);
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
}

