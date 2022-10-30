package com.ceneval.exoal.jifo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class SolicitudDesconectarComputadorasDto {
  private String nombreComputadora1;
  private String nombreComputadora2;
}
