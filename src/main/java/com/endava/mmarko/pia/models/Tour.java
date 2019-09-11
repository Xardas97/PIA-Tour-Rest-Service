package com.endava.mmarko.pia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tours")
@Getter
@Setter
@ToString(exclude = {"description", "myGuides"})
public class Tour extends AbstractModel<Integer> {
    private String name;
    private String description;
    @Column(name = "meeting_point")
    private String meetingPoint;
    @Column(name = "min_people")
    private int minPeople;
    @ManyToMany(mappedBy = "myTours", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Guide> myGuides;

    public Tour() {}

    public Tour(String name, String description, String meetingPoint, int minPeople) {
        this.name = name;
        this.description = description;
        this.meetingPoint = meetingPoint;
        this.minPeople = minPeople;
    }
}
