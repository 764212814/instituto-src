package com.ceneval.exoal.jifo.db;

import com.ceneval.exoal.jifo.dto.ConexionDto;

import java.math.BigDecimal;

public interface CrudConexion {
  void crearNuevaConexion(Computadora computadora1, Computadora computadora2, BigDecimal latencia);

  void eliminarConexiones(Computadora computadora);

  void eliminarConexion(Integer identificadorConexion);

  void eliminarTodasLasConexiones();

  ConexionDto mapearEntidadConexion(Conexion conexion, String nombreComputadora);
}
