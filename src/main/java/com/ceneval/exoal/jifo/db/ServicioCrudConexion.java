package com.ceneval.exoal.jifo.db;

import com.ceneval.exoal.jifo.dto.ConexionDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Service
class ServicioCrudConexion implements CrudConexion {

  @Autowired
  private RepositorioConexion repositorioConexion;

  @Override
  @Transactional
  public void crearNuevaConexion(Computadora computadora1, Computadora computadora2, BigDecimal latencia) {
    BigDecimal latenciaAPersistir;
    if (latencia == null || latencia.compareTo(BigDecimal.ZERO) <= 0) {
      latenciaAPersistir = generarBigDecimalAleatorio(BigDecimal.ONE, BigDecimal.TEN);
    } else {
      latenciaAPersistir = latencia;
    }
    Conexion nuevaConexion = Conexion.builder()
      .latencia(latenciaAPersistir)
      .computadoras(Arrays.asList(computadora1, computadora2))
      .build();
    repositorioConexion.save(nuevaConexion);
  }

  @Override
  @Transactional
  public void eliminarConexion(Integer identificadorConexion) {
    repositorioConexion.deleteById(identificadorConexion);
  }

  @Override
  public void eliminarTodasLasConexiones() {
    repositorioConexion.deleteAll();
  }

  @Override
  @Transactional
  public void eliminarConexiones(Computadora computadora) {
    computadora.getConexiones().forEach(conexion -> repositorioConexion.delete(conexion));
  }

  @Override
  public ConexionDto mapearEntidadConexion(Conexion conexion, String nombreComputadora) {
    String computadoraConectada = conexion.getComputadoras().stream()
      .map(Computadora::getNombre)
      .filter(nombre -> !nombre.equals(nombreComputadora))
      .findFirst()
      .orElse(null);
    return new ConexionDto(conexion.getIdentificador(), conexion.getLatencia(), computadoraConectada);
  }

  private BigDecimal generarBigDecimalAleatorio(BigDecimal min, BigDecimal max) {
    BigDecimal randomBigDecimal = min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
    return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
  }
}
