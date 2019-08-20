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
    @JoinColumn(name = "tour_guide_id")
    private TourGuide guide;
    private Date date;

    @Override
    public String toString() {
        return "Departure{" +
                "guide=" + guide +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TourGuide getGuide() {
        return guide;
    }

    public void setGuide(TourGuide guide) {
        this.guide = guide;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
