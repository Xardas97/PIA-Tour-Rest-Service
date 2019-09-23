package com.endava.mmarko.pia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"username", "password"})
public class User extends AbstractModel<Integer> {
  private String username;
  @JsonIgnore
  private String password;
  private boolean guide;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;

  public User() {
  }

  public User(String username) {
    this.username = username;
  }

  public User(String username, String password, boolean guide, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.guide = guide;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
