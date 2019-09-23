package com.endava.mmarko.pia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "guides")
@Entity
@Getter
@Setter
@ToString(exclude = {"myTours"})
public class Guide extends AbstractModel<Integer> {
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "tours_guides",
          joinColumns = {@JoinColumn(name = "guide_id")},
          inverseJoinColumns = {@JoinColumn(name = "tour_id")}
  )
  @JsonIgnore
  private List<Tour> myTours;

  public Guide() {
  }

  public Guide(User user) {
    this.user = user;
  }
}
