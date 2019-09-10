package com.endava.mmarko.pia.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@ToString
public class Reservation extends AbstractModel<Integer> {
    @ManyToOne
    @JoinColumn(name = "departure_id")
    private Departure departure;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    public Reservation() {}

    public Reservation(Departure departure, User client) {
        this.departure = departure;
        this.client = client;
    }
}
