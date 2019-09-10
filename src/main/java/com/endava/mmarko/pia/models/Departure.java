package com.endava.mmarko.pia.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "departures")
@Setter
@Getter
@ToString
public class Departure extends AbstractModel<Integer> {
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
}
