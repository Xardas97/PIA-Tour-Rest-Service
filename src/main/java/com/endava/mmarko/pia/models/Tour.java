package com.endava.mmarko.pia.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Column(name = "meeting_point")
    private String meetingPoint;
    @Column(name = "min_people")
    private int minPeople;
    @ManyToMany(mappedBy = "myTours", fetch = FetchType.EAGER)
    private List<Guide> myGuides;

    public Tour() {}

    public Tour(String name, String description, String meetingPoint, int minPeople) {
        this.name = name;
        this.description = description;
        this.meetingPoint = meetingPoint;
        this.minPeople = minPeople;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "name='" + name + '\'' +
                ", meetingPoint='" + meetingPoint + '\'' +
                ", minPeople=" + minPeople +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(int minPeople) {
        this.minPeople = minPeople;
    }

    public void setMyGuides(List<Guide> myGuides) {
        this.myGuides = myGuides;
    }
}
