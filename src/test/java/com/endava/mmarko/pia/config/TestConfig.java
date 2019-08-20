package com.endava.mmarko.pia.config;

import com.endava.mmarko.pia.errors.GlobalExceptionHandler;
import com.endava.mmarko.pia.repositories.*;
import com.endava.mmarko.pia.services.DepartureService;
import com.endava.mmarko.pia.services.ReservationService;
import com.endava.mmarko.pia.services.TourService;
import com.endava.mmarko.pia.services.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public TourService tourService() {
        return Mockito.mock(TourService.class);
    }

    @Bean
    public DepartureService departureService() {
        return Mockito.mock(DepartureService.class);
    }

    @Bean
    public ReservationService reservationService() {
        return Mockito.mock(ReservationService.class);
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }
    @Bean
    public TourRepo tourRepo() {
        return Mockito.mock(TourRepo.class);
    }

    @Bean
    public DepartureRepo departureRepo() {
        return Mockito.mock(DepartureRepo.class);
    }

    @Bean
    public ReservationRepo reservationRepo() {
        return Mockito.mock(ReservationRepo.class);
    }

    @Bean
    public UserAutoRepo userRepo() {
        return Mockito.mock(UserAutoRepo.class);
    }

    @Bean
    GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
