package com.endava.mmarko.pia.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "departures")
public class Departure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour Tour;
    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;
    private Date date;

    public Departure() {}

    public Departure(Tour tour, Guide guide, Date date) {
        Tour = tour;
        this.guide = guide;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Departure{" +
                "Tour=" + Tour +
                ", guide=" + guide +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tour getTour() {
        return Tour;
    }

    public void setTour(Tour tour) {
        Tour = tour;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
