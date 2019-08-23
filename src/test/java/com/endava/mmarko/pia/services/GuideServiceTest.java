package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.repositories.GuideRepo;
import com.endava.mmarko.pia.services.GuideService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PiaConfig.class)
@ActiveProfiles("dev")
public class GuideServiceTest {
    private static final int ID = 5;

    @Mock
    private GuideRepo guideRepo;

    @InjectMocks
    private GuideService guideService;

    @Test
    public void deleteTest(){
        guideService.delete(ID);
        verify(guideRepo, times(1)).delete(ID);
        verifyNoMoreInteractions(guideRepo);
    }

    @Test
    public void findAllTest(){
        List<Guide> expected = Arrays.asList(new Guide(), new Guide());

        when(guideRepo.findAll()).thenReturn(expected);

        assertEquals(expected, guideService.findAll());

        verify(guideRepo, times(1)).findAll();
        verifyNoMoreInteractions(guideRepo);
    }

    @Test
    public void findTest(){
        Guide expected = new Guide();
        expected.setId(ID);

        when(guideRepo.findOne(ID)).thenReturn(expected);

        assertEquals(expected, guideService.find(ID));

        verify(guideRepo, times(1)).findOne(ID);
        verifyNoMoreInteractions(guideRepo);
    }

    @Test
    public void saveTest() {
        User user = new User();

        Guide unsaved = new Guide(user, null);
        Guide saved =  new Guide(user, null);
        saved.setId(10);

        when(guideRepo.save(unsaved)).thenReturn(saved);

        assertEquals(saved, guideService.save(unsaved));

        verify(guideRepo, times(1)).save(unsaved);
        verifyNoMoreInteractions(guideRepo);
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
}

