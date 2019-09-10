package com.endava.mmarko.pia.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table(name="guides")
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
            joinColumns = { @JoinColumn(name = "guide_id") },
            inverseJoinColumns = { @JoinColumn(name = "tour_id") }
    )
    private List<Tour> myTours;

    public Guide() {
    }

    public Guide(User user, List<Tour> myTours) {
        this.user = user;
        this.myTours = myTours;
    }
}
