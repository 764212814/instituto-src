package com.ceneval.exoal.jifo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class RespuestaObtenerTodasComputadorasDto {
  private Set<ComputadoraDto> computadoras;
}
