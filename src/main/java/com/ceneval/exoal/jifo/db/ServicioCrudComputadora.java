package com.ceneval.exoal.jifo.db;

import com.ceneval.exoal.jifo.dto.RespuestaObtenerTodasComputadorasDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearComputadoraDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearConexionDto;
import com.ceneval.exoal.jifo.dto.ComputadoraDto;
import com.ceneval.exoal.jifo.dto.ConexionDto;
import com.ceneval.exoal.jifo.dto.SolicitudEliminarComputadoraDto;
import com.ceneval.exoal.jifo.dto.SolicitudDesconectarComputadorasDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
class ServicioCrudComputadora implements CrudComputadora {

  @Autowired
  private RepositorioComputadora repositorioComputadora;
  @Autowired
  private ServicioCrudConexion servicioCrudConexion;

  @Override
  @Transactional
  public void crearNuevaComputadora(SolicitudCrearComputadoraDto solicitudCrearComputadoraDto) {
    if (solicitudCrearComputadoraDto.getNombreComputadora() == null || solicitudCrearComputadoraDto.getNombreComputadora().equals("")) {
      throw new IllegalArgumentException("Nombre de la computadora es requerido en la solicitud");
    }
    if (repositorioComputadora.existsById(solicitudCrearComputadoraDto.getNombreComputadora())) {
      throw new IllegalArgumentException("Una computadora con ese nombre ya existe");
    }
    Computadora nuevaComputadora = Computadora.builder().nombre(solicitudCrearComputadoraDto.getNombreComputadora()).build();
    repositorioComputadora.save(nuevaComputadora);
  }

  @Override
  @Transactional
  public void conectarComputadoras(SolicitudCrearConexionDto solicitudCrearConexionDto) {
    if (solicitudCrearConexionDto.getNombreComputadora1() == null || solicitudCrearConexionDto.getNombreComputadora1().equals("")) {
      throw new IllegalArgumentException("Nombre de la computadora 1 es requerido en la solicitud");
    }
    if (solicitudCrearConexionDto.getNombreComputadora2() == null || solicitudCrearConexionDto.getNombreComputadora2().equals("")) {
      throw new IllegalArgumentException("Nombre de la computadora 2 es requerido en la solicitud");
    }
    if (solicitudCrearConexionDto.getNombreComputadora2().equals(solicitudCrearConexionDto.getNombreComputadora1())) {
      throw new IllegalArgumentException("No se permite conectar una computadora a si misma");
    }
    Optional<Computadora> computadora1 = repositorioComputadora.findById(solicitudCrearConexionDto.getNombreComputadora1());
    if (computadora1.isEmpty()) {
      throw new IllegalArgumentException("Computadora 1 no encontrada");
    }
    Optional<Computadora> computadora2 = repositorioComputadora.findById(solicitudCrearConexionDto.getNombreComputadora2());
    if (computadora2.isEmpty()) {
      throw new IllegalArgumentException("Computadora 2 no encontrada");
    }
    if (repositorioComputadora.estanDirectamenteConectadas(computadora1.get().getNombre(), computadora2.get().getNombre())) {
      throw new IllegalArgumentException("Computadoras ya están conectadas");
    }
    servicioCrudConexion.crearNuevaConexion(computadora1.get(), computadora2.get(), solicitudCrearConexionDto.getLatencia());
  }

  @Override
  @Transactional
  public void desconectarComputadoras(SolicitudDesconectarComputadorasDto solicitudDesconectarComputadorasDto) {
    if (solicitudDesconectarComputadorasDto.getNombreComputadora1() == null || solicitudDesconectarComputadorasDto.getNombreComputadora1().equals("")) {
      throw new IllegalArgumentException("Nombre de la computadora 1 es requerido en la solicitud");
    }
    if (solicitudDesconectarComputadorasDto.getNombreComputadora2() == null || solicitudDesconectarComputadorasDto.getNombreComputadora2().equals("")) {
      throw new IllegalArgumentException("Nombre de la computadora 2 es requerido en la solicitud");
    }
    Optional<Computadora> computadora1 = repositorioComputadora.findById(solicitudDesconectarComputadorasDto.getNombreComputadora1());
    if (computadora1.isEmpty()) {
      throw new IllegalArgumentException("Computadora 1 no encontrada");
    }
    Optional<Computadora> computadora2 = repositorioComputadora.findById(solicitudDesconectarComputadorasDto.getNombreComputadora2());
    if (computadora2.isEmpty()) {
      throw new IllegalArgumentException("Computadora 2 no encontrada");
    }
    for (Conexion conexion : computadora1.get().getConexiones()) {
      for (Computadora computadora : conexion.getComputadoras()) {
        if (computadora.getNombre().equals(computadora2.get().getNombre())) {
          servicioCrudConexion.eliminarConexion(conexion.getIdentificador());
          return;
        }
      }
    }
    throw new IllegalArgumentException("Computadoras mencionadas no estan conectadas");
  }

  @Override
  @Transactional
  public void eliminarComputadora(SolicitudEliminarComputadoraDto solicitudEliminarComputadoraDto) {
    if (solicitudEliminarComputadoraDto.getNombreComputadora() == null || solicitudEliminarComputadoraDto.getNombreComputadora().equals("")) {
      throw new IllegalArgumentException("Nombre de la computadora es requerido en la solicitud");
    }
    Optional<Computadora> computadora = repositorioComputadora.findById(solicitudEliminarComputadoraDto.getNombreComputadora());
    if (computadora.isEmpty()) {
      throw new IllegalArgumentException("Nombre de la computada sin coincidencias");
    }
    servicioCrudConexion.eliminarConexiones(computadora.get());
    repositorioComputadora.delete(computadora.get());
  }

  @Override
  public void eliminarTodasLasComputadoras() {
    servicioCrudConexion.eliminarTodasLasConexiones();
    repositorioComputadora.deleteAll();
  }

  @Override
  public RespuestaObtenerTodasComputadorasDto obtenerTodasComputadoras() {
    Iterable<Computadora> computadoras = repositorioComputadora.findAll();
    Set<ComputadoraDto> computadorasDto = StreamSupport.stream(computadoras.spliterator(), false)
      .map(this::mapearEntidadComputadora)
      .collect(Collectors.toSet());
    return new RespuestaObtenerTodasComputadorasDto(computadorasDto);
  }


  private ComputadoraDto mapearEntidadComputadora(Computadora computadora) {
    Set<ConexionDto> conexionesMapeadas = computadora.getConexiones().stream()
      .map(conexion -> servicioCrudConexion.mapearEntidadConexion(conexion, computadora.getNombre()))
      .collect(Collectors.toSet());
    return new ComputadoraDto(computadora.getNombre(), conexionesMapeadas);
  }
}
