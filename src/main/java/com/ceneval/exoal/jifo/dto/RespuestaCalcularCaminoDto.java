package com.ceneval.exoal.jifo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Queue;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class RespuestaCalcularCaminoDto {
  private Queue<String> caminoMinimo;
  private BigDecimal pesoTotal;
}
