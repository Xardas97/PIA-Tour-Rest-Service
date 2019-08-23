package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.errors.WrongPasswordError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.repositories.UserRepo;
import com.endava.mmarko.pia.services.UserService;
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
public class UserServiceTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final int ID = 5;

    @Mock
    UserRepo userRepo;

    @InjectMocks
    UserService userService;

    @Test
    public void findAllTest(){
        List<User> expected = Arrays.asList(new User(), new User());

        when(userRepo.findAll()).thenReturn(expected);

        assertEquals(expected, userService.findAll());

        verify(userRepo, times(1)).findAll();
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    public void findTest(){
        User expected = new User();
        expected.setUsername(USERNAME);

        when(userRepo.findOne(ID)).thenReturn(expected);

        assertEquals(expected.getUsername(), userService.find(ID).getUsername());

        verify(userRepo, times(1)).findOne(ID);
        verifyNoMoreInteractions(userRepo);
    }

    @Test(expected = WrongPasswordError.class)
    public void findByUsernameAndPasswordWrongPasswordTest() {
        String wrongPassword = PASSWORD + "1";
        User user = new User();
        user.setUsername(USERNAME); user.setPassword(PASSWORD);

        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        userService.findByUsernameAndPassword(USERNAME, wrongPassword);
    }

    @Test(expected = UserNotFoundError.class)
    public void findByUsernameAndPasswordUserNotFoundTest() {
        when(userRepo.findByUsername(USERNAME)).thenReturn(null);
        userService.findByUsernameAndPassword(USERNAME, PASSWORD);
    }

    @Test
    public void findByUsernameAndPasswordSuccessfulTest() {
        User user = new User();
        user.setUsername(USERNAME); user.setPassword(PASSWORD);

        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        assertEquals(userService.findByUsernameAndPassword(USERNAME, PASSWORD), user);

        verify(userRepo, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    public void saveTest() {
        User unsaved = new User(USERNAME, PASSWORD, true, "first name", "last name");
        User saved = new User(USERNAME, PASSWORD, true, "first name", "last name");
        saved.setId(ID);

        when(userRepo.save(unsaved)).thenReturn(saved);

        assertEquals(userService.save(unsaved), saved);

        verify(userRepo, times(1)).save(unsaved);
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    public void deleteTest(){
        userService.delete(ID);
        verify(userRepo, times(1)).delete(ID);
        verifyNoMoreInteractions(userRepo);
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

}
