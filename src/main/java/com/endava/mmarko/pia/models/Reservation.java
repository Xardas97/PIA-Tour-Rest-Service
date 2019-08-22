package com.endava.mmarko.pia.models;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    @Override
    public String toString() {
        return "Reservation{" +
                "departure=" + departure +
                ", client=" + client +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
