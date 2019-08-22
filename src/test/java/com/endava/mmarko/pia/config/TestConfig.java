package com.endava.mmarko.pia.config;

import com.endava.mmarko.pia.errors.GlobalExceptionHandler;
import com.endava.mmarko.pia.repositories.*;
import com.endava.mmarko.pia.services.*;
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
    public GuideService guideService() {
        return Mockito.mock(GuideService.class);
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
    public UserRepo userRepo() {
        return Mockito.mock(UserRepo.class);
    }

    @Bean
    public GuideRepo guideRepo() {
        return Mockito.mock(GuideRepo.class);
    }

    @Bean
    GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
