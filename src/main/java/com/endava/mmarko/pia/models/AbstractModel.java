package com.endava.mmarko.pia.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString(exclude = {"id"})
public abstract class AbstractModel<ID> {
  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private ID id;
}
