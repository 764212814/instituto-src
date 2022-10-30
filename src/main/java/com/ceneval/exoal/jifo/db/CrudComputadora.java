package com.ceneval.exoal.jifo.db;

import com.ceneval.exoal.jifo.dto.RespuestaObtenerTodasComputadorasDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearComputadoraDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearConexionDto;
import com.ceneval.exoal.jifo.dto.SolicitudEliminarComputadoraDto;
import com.ceneval.exoal.jifo.dto.SolicitudDesconectarComputadorasDto;

public interface CrudComputadora {
  void crearNuevaComputadora(SolicitudCrearComputadoraDto solicitudCrearComputadoraDto);

  void conectarComputadoras(SolicitudCrearConexionDto solicitudCrearConexionDto);

  void desconectarComputadoras(SolicitudDesconectarComputadorasDto solicitudDesconectarComputadorasDto);

  void eliminarComputadora(SolicitudEliminarComputadoraDto solicitudEliminarComputadoraDto);

  void eliminarTodasLasComputadoras();

  RespuestaObtenerTodasComputadorasDto obtenerTodasComputadoras();
}
