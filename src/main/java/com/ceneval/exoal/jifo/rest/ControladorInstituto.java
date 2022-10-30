package com.ceneval.exoal.jifo.rest;

import com.ceneval.exoal.jifo.db.CrudComputadora;
import com.ceneval.exoal.jifo.dto.RespuestaObtenerTodasComputadorasDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearCaminosDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearComputadoraDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearConexionDto;
import com.ceneval.exoal.jifo.dto.SolicitudEliminarComputadoraDto;
import com.ceneval.exoal.jifo.dto.SolicitudDesconectarComputadorasDto;
import com.ceneval.exoal.jifo.grafo.ServicioGrafo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.stream.Stream;

@RestController
@RequestMapping("instituto")
class ControladorInstituto {

  @Autowired
  private CrudComputadora crudComputadora;
  @Autowired
  private ServicioGrafo servicioGrafo;

  @GetMapping("/computadoras")
  public ResponseEntity<?> obtenerTodasComputadoras() {
    try {
      return ResponseEntity.ok().body(crudComputadora.obtenerTodasComputadoras());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante la lectura de las computadoras");
    }
  }

  @PostMapping("/computadora")
  public ResponseEntity<?> crearComputadora(@RequestBody SolicitudCrearComputadoraDto solicitudCrearComputadoraDto) {
    try {
      crudComputadora.crearNuevaComputadora(solicitudCrearComputadoraDto);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante la creación de la computadora");
    }
  }

  @DeleteMapping("/computadora")
  public ResponseEntity<?> eliminarComputadora(@RequestBody SolicitudEliminarComputadoraDto solicitudEliminarComputadoraDto) {
    try {
      crudComputadora.eliminarComputadora(solicitudEliminarComputadoraDto);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante la eliminación de la computadora");
    }
  }

  @DeleteMapping("/computadoras")
  public ResponseEntity<?> eliminarComputadoras() {
    try {
      crudComputadora.eliminarTodasLasComputadoras();
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante la eliminación de las computadoras");
    }
  }

  @PostMapping("/conexion")
  public ResponseEntity<?> crearConexion(@RequestBody SolicitudCrearConexionDto solicitudCrearConexionDto) {
    try {
      crudComputadora.conectarComputadoras(solicitudCrearConexionDto);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante la creación de la conexion");
    }
  }

  @DeleteMapping("/conexion")
  public ResponseEntity<?> eliminarConexion(@RequestBody SolicitudDesconectarComputadorasDto solicitudDesconectarComputadorasDto) {
    try {
      crudComputadora.desconectarComputadoras(solicitudDesconectarComputadorasDto);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante la eliminación de la conexion");
    }
  }

  @GetMapping("/caminos")
  public ResponseEntity<?> obtenerCaminosMinimos(SolicitudCrearCaminosDto solicitudCrearCaminosDto) {
    try {
      return ResponseEntity.ok().body(servicioGrafo.calcularCaminosMinimos(solicitudCrearCaminosDto, crudComputadora.obtenerTodasComputadoras().getComputadoras()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante el cálculo del camino mínimo");
    }
  }

  @GetMapping("/arbol-recubridor-minimo")
  public ResponseEntity<?> obtenerCaminosMinimos() {
    try {
      return ResponseEntity.ok().body(servicioGrafo.calcularArbolRecubridorMinimo(crudComputadora.obtenerTodasComputadoras().getComputadoras()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante el cálculo del árbol recubridor mínimo");
    }
  }

  @PostMapping("/default")
  public ResponseEntity<?> crearComputadorasDefault() {
    try {
      RespuestaObtenerTodasComputadorasDto computadoras = crudComputadora.obtenerTodasComputadoras();
      computadoras.getComputadoras()
        .forEach(c -> crudComputadora.eliminarComputadora(new SolicitudEliminarComputadoraDto(c.getNombre())));
      Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        .forEach(nombre -> crudComputadora.crearNuevaComputadora(new SolicitudCrearComputadoraDto(nombre)));
      Stream.of(
        new SolicitudCrearConexionDto("A", "C", BigDecimal.valueOf(5.5)),
        new SolicitudCrearConexionDto("A", "B", BigDecimal.valueOf(5.4)),
        new SolicitudCrearConexionDto("A", "O", BigDecimal.valueOf(4.3)),
        new SolicitudCrearConexionDto("A", "M", BigDecimal.valueOf(5.4)),
        new SolicitudCrearConexionDto("A", "N", BigDecimal.valueOf(3.7)),
        new SolicitudCrearConexionDto("B", "C", BigDecimal.valueOf(3.5)),
        new SolicitudCrearConexionDto("B", "D", BigDecimal.valueOf(4.5)),
        new SolicitudCrearConexionDto("B", "O", BigDecimal.valueOf(3.3)),
        new SolicitudCrearConexionDto("C", "D", BigDecimal.valueOf(2.9)),
        new SolicitudCrearConexionDto("E", "F", BigDecimal.valueOf(3.5)),
        new SolicitudCrearConexionDto("E", "H", BigDecimal.valueOf(4.3)),
        new SolicitudCrearConexionDto("E", "G", BigDecimal.valueOf(3.0)),
        new SolicitudCrearConexionDto("F", "G", BigDecimal.valueOf(4.4)),
        new SolicitudCrearConexionDto("F", "H", BigDecimal.valueOf(3.5)),
        new SolicitudCrearConexionDto("G", "H", BigDecimal.valueOf(4.3)),
        new SolicitudCrearConexionDto("G", "I", BigDecimal.valueOf(3.5)),
        new SolicitudCrearConexionDto("I", "J", BigDecimal.valueOf(4.2)),
        new SolicitudCrearConexionDto("I", "K", BigDecimal.valueOf(4.7)),
        new SolicitudCrearConexionDto("J", "K", BigDecimal.valueOf(3.0)),
        new SolicitudCrearConexionDto("J", "L", BigDecimal.valueOf(3.2)),
        new SolicitudCrearConexionDto("J", "A", BigDecimal.valueOf(3.7)),
        new SolicitudCrearConexionDto("B", "J", BigDecimal.valueOf(3.2)),
        new SolicitudCrearConexionDto("K", "L", BigDecimal.valueOf(4.8)),
        new SolicitudCrearConexionDto("K", "M", BigDecimal.valueOf(4.2)),
        new SolicitudCrearConexionDto("M", "N", BigDecimal.valueOf(5.3)),
        new SolicitudCrearConexionDto("M", "O", BigDecimal.valueOf(3.5)),
        new SolicitudCrearConexionDto("M", "G", BigDecimal.valueOf(2.8)),
        new SolicitudCrearConexionDto("E", "N", BigDecimal.valueOf(3.1)))
        .forEach(solicitud -> crudComputadora.conectarComputadoras(solicitud));
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error durante la creación de las computadoras y/o conexiones");
    }
  }
}
