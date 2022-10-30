package com.ceneval.exoal.jifo.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "computadora")
@Getter(AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
final class Computadora {
  @Id
  private String nombre;
  @ManyToMany(mappedBy = "computadoras", fetch = FetchType.EAGER)
  private Set<Conexion> conexiones;
}
