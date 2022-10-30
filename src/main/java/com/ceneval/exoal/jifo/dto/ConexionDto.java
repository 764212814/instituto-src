package com.ceneval.exoal.jifo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class ConexionDto {
  private Integer identificador;
  private BigDecimal latencia;
  private String computadoraConectada;
}
