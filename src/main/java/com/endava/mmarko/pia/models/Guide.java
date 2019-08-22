package com.endava.mmarko.pia.models;

import javax.persistence.*;
import java.util.List;

@Table(name="guides")
@Entity
public class Guide {
    @Id
    private int id;
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

    @Override
    public String toString() {
        return user.toString();
    }

    public List<Tour> getMyTours() {
        return myTours;
    }

    public void setMyTours(List<Tour> myTours) {
        this.myTours = myTours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
