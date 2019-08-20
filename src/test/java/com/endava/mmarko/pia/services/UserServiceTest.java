package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.config.PiaConfig;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.errors.WrongPasswordError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.repositories.UserAutoRepo;
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
public class UserServiceTest {

    @Mock
    UserAutoRepo userRepo;

    @InjectMocks
    UserService userService;

    @Test
    public void findAllTest(){
        List<User> expected = new LinkedList<>();
        expected.add(new User()); expected.add(new User());

        when(userRepo.findAll()).thenReturn(expected);

        assertEquals(userService.findAll(), expected);
    }

    @Test
    public void findTest(){
        String username = "username";
        User expected = new User(); expected.setUsername(username);

        when(userRepo.findOne(username)).thenReturn(expected);

        assertEquals(expected.getUsername(), userService.find(username).getUsername());
    }

    @Test(expected = WrongPasswordError.class)
    public void findByUsernameAndPasswordWrongPasswordTest() {
        String username = "username"; String password = "password1"; String wrongPassword = "password2";
        User user = new User();
        user.setUsername(username); user.setPassword(password);

        when(userRepo.findOne(username)).thenReturn(user);
        userService.findByUsernameAndPassword(username, wrongPassword);
    }

    @Test(expected = UserNotFoundError.class)
    public void findByUsernameAndPasswordUserNotFoundTest() {
        String username = "username"; String password = "password1";
        when(userRepo.findOne(username)).thenReturn(null);
        userService.findByUsernameAndPassword(username, password);
    }

    @Test
    public void findByUsernameAndPasswordSuccessfulTest() {
        String username = "username"; String password = "password1";
        User user = new User();
        user.setUsername(username); user.setPassword(password);

        when(userRepo.findOne(username)).thenReturn(user);
        assertEquals(userService.findByUsernameAndPassword(username, password), user);
    }
    
    @Test
    public void saveTest() {
        User user = new User("username", "password", (short)1, "first name", "last name");

        when(userRepo.save(user)).thenReturn(user);

        assertEquals(userService.save(user), user);
    }

    @Test
    public void deleteTest(){
        userService.delete("username");
        verify(userRepo, times(1)).delete("username");
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

}
