package com.ceneval.exoal.jifo.grafo;

import com.ceneval.exoal.jifo.dto.ComputadoraDto;
import com.ceneval.exoal.jifo.dto.ConexionDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class UtilidadMapeadorGrafo {
  protected static Set<ComputadoraDto> convertirGrafoAComputadoras(Grafo grafo) {
    return grafo.obtenerNodos().values().stream().map(UtilidadMapeadorGrafo::convertirNodoAComputadora).collect(Collectors.toSet());
  }

  private static ComputadoraDto convertirNodoAComputadora(Nodo nodo) {
    Set<ConexionDto> conexionesMapeadas = nodo.obtenerAristas().stream()
      .map(arista -> UtilidadMapeadorGrafo.covertirAristaAConexion(arista, nodo.obtenerNombre()))
      .collect(Collectors.toSet());
    return new ComputadoraDto(nodo.obtenerNombre(), conexionesMapeadas);
  }

  private static ConexionDto covertirAristaAConexion(AristaConPeso arista, String nombreComputadora) {
    return new ConexionDto(arista.obtenerIdentificador(), arista.obtenerPeso(), arista.obtenerNombreOtroNodo(nombreComputadora));
  }

  protected static Grafo convertirComputadorasAGrafo(Set<ComputadoraDto> computadoras) {
    Grafo grafoInstituto = new GrafoDefault();
    for (ComputadoraDto computadoraDto : computadoras) {
      List<AristaConPeso> aristas = UtilidadMapeadorGrafo.extraerAristas(computadoraDto);
      if (aristas.size() == 0) {
        grafoInstituto.agregarNodoVacio(computadoraDto.getNombre());
      } else {
        aristas.forEach(grafoInstituto::agregarArista);
      }
    }
    return grafoInstituto;
  }

  private static List<AristaConPeso> extraerAristas(ComputadoraDto computadora) {
    return computadora.getConexiones().stream()
      .map(conexion -> UtilidadMapeadorGrafo.extraerArista(conexion, computadora))
      .collect(Collectors.toList());
  }

  private static AristaConPeso extraerArista(ConexionDto conexion, ComputadoraDto computadora) {
    return new AristaConPesoDefault(conexion.getIdentificador(), computadora.getNombre(), conexion.getComputadoraConectada(), conexion.getLatencia());
  }
}
