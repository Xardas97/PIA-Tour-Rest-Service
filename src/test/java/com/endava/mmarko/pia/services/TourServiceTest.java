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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PiaConfig.class)
public class TourServiceTest {
    private static final int ID = 5;

    @Mock
    private TourRepo tourRepo;

    @InjectMocks
    private TourService tourService;

    @Test
    public void deleteTest(){
        tourService.delete(ID);
        verify(tourRepo, times(1)).delete(ID);
        verifyNoMoreInteractions(tourRepo);
    }

    @Test
    public void findAllTest(){
        List<Tour> expected = Arrays.asList(new Tour(), new Tour());

        when(tourRepo.findAll()).thenReturn(expected);

        assertEquals(expected, tourService.findAll());

        verify(tourRepo, times(1)).findAll();
        verifyNoMoreInteractions(tourRepo);
    }

    @Test
    public void findTest(){
        Tour expected = new Tour();
        expected.setId(ID);

        when(tourRepo.findOne(ID)).thenReturn(expected);

        assertEquals(expected, tourService.find(ID));

        verify(tourRepo, times(1)).findOne(ID);
        verifyNoMoreInteractions(tourRepo);
    }

    @Test
    public void saveTest() {
        Tour unsaved = new Tour("name", "description", "point", 1);
        Tour saved =  new Tour("name", "description", "point", 1);
        saved.setId(10);

        when(tourRepo.save(unsaved)).thenReturn(saved);

        assertEquals(saved, tourService.save(unsaved));

        verify(tourRepo, times(1)).save(unsaved);
        verifyNoMoreInteractions(tourRepo);
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
}

