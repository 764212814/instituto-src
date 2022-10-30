package com.ceneval.exoal.jifo.grafo;

import com.ceneval.exoal.jifo.dto.ComputadoraDto;
import com.ceneval.exoal.jifo.dto.RespuestaCalcularCaminoDto;
import com.ceneval.exoal.jifo.dto.RespuestaObtenerRecubridorMinimoDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearCaminosDto;

import java.util.List;
import java.util.Set;

public interface ServicioGrafo {
  RespuestaObtenerRecubridorMinimoDto calcularArbolRecubridorMinimo(Set<ComputadoraDto> computadoras);

  List<RespuestaCalcularCaminoDto> calcularCaminosMinimos(SolicitudCrearCaminosDto solicitudCrearCaminosDto, Set<ComputadoraDto> computadoras);
}
