package com.ceneval.exoal.jifo.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "conexion")
@Getter(AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
final class Conexion {
  @Id
  @GeneratedValue
  private int identificador;
  @Column(name = "latencia")
  private BigDecimal latencia;
  @ManyToMany(fetch = FetchType.LAZY)
  @ToString.Exclude
  @JoinTable(
    name = "RelacionComputadoraConexion",
    joinColumns = @JoinColumn(name = "identificador"),
    inverseJoinColumns = @JoinColumn(name = "nombre"))
  private List<Computadora> computadoras;
}