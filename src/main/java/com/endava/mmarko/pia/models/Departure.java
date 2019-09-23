package com.endava.mmarko.pia.models;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "departures")
@Setter
@Getter
@ToString
public class Departure extends AbstractModel<Integer> {
  @ManyToOne
  @JoinColumn(name = "tour_id")
  private Tour tour;
  @ManyToOne
  @JoinColumn(name = "guide_id")
  private Guide guide;
  private Date date;

  public Departure() {
  }

  public Departure(Tour tour, Guide guide, Date date) {
    this.tour = tour;
    this.guide = guide;
    this.date = date;
  }
}
