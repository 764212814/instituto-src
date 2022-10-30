package com.ceneval.exoal.jifo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class SolicitudCrearCaminosDto {
  private String nombreComputadoraInicial;
  private String nombreComputadoraFinal;
}
