package com.ceneval.exoal.jifo.grafo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
final class AristaConPesoDefault implements AristaConPeso {

  @EqualsAndHashCode.Include
  private final Integer identificador;
  @EqualsAndHashCode.Exclude
  private final String nombreNodo1;
  @EqualsAndHashCode.Exclude
  private final String nombreNodo2;
  @EqualsAndHashCode.Exclude
  private final BigDecimal peso;

  @Override
  public Integer obtenerIdentificador() {
    return identificador;
  }

  @Override
  public BigDecimal obtenerPeso() {
    return peso;
  }

  @Override
  public String obtenerNombreNodoConectado1() {
    return nombreNodo1;
  }

  @Override
  public String obtenerNombreNodoConectado2() {
    return nombreNodo2;
  }

  @Override
  public String obtenerNombreOtroNodo(String nombreNodo) {
    return nombreNodo.equals(obtenerNombreNodoConectado1()) ? obtenerNombreNodoConectado2() : obtenerNombreNodoConectado1();
  }
}
