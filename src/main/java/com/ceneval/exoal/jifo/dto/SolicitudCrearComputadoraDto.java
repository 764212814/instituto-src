package com.ceneval.exoal.jifo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class SolicitudCrearComputadoraDto {
  private String nombreComputadora;

  /**
   * Evitar caracteres especiales por temas de seguridad.
   *
   * @return nombreComputadora sin caracteres especiales
   */
  public String getNombreComputadora() {
    return nombreComputadora.replaceAll("[^A-Za-z0-9]", "");
  }
}
