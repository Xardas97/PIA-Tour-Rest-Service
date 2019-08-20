package com.endava.mmarko.pia.models;

import javax.persistence.*;

@Entity
@Table(name = "tour_guides")
public class TourGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
    private String guide;

    @Override
    public String toString() {
        return "TourGuide{" +
                "guide='" + guide + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
