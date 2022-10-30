package com.ceneval.exoal.jifo.grafo;

import com.ceneval.exoal.jifo.dto.ComputadoraDto;
import com.ceneval.exoal.jifo.dto.RespuestaObtenerRecubridorMinimoDto;
import com.ceneval.exoal.jifo.dto.ResultadoCalcularCaminosDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearCaminosDto;
import com.ceneval.exoal.jifo.dto.RespuestaCalcularCaminoDto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import static com.ceneval.exoal.jifo.grafo.UtilidadMapeadorGrafo.convertirComputadorasAGrafo;
import static com.ceneval.exoal.jifo.grafo.UtilidadMapeadorGrafo.convertirGrafoAComputadoras;

@Service
class ServicioGrafoDefault implements ServicioGrafo {

  @Override
  public RespuestaObtenerRecubridorMinimoDto calcularArbolRecubridorMinimo(Set<ComputadoraDto> computadoras) {
    Grafo instituto = convertirComputadorasAGrafo(computadoras);
    if (instituto.cantidadDeNodos() <= 1) {
      throw new IllegalArgumentException("Deben existir 2 o mas computadoras para proceder");
    }
    String nombreDeAlgunaComputadoraAlAzar = instituto.obtenerNodos().keySet().stream().findFirst().orElseThrow();
    ResultadoCalcularCaminosDto respuestaCalcularCaminoDto = caminosMinimos(instituto, nombreDeAlgunaComputadoraAlAzar);
    if (respuestaCalcularCaminoDto.getCaminosMinimos().size() != computadoras.size()) {
      throw new IllegalArgumentException("La red de computadoras no es conexa");
    }
    Grafo arbolRecubridorMinimo = calcularArbolRecubridorMinimo(instituto);
    return new RespuestaObtenerRecubridorMinimoDto(convertirGrafoAComputadoras(arbolRecubridorMinimo));
  }

  @Override
  public List<RespuestaCalcularCaminoDto> calcularCaminosMinimos(SolicitudCrearCaminosDto solicitudCrearCaminosDto, Set<ComputadoraDto> computadoras) {
    if (solicitudCrearCaminosDto.getNombreComputadoraInicial() == null || solicitudCrearCaminosDto.getNombreComputadoraInicial().equals("")) {
      throw new IllegalArgumentException("Nombre de la computadora inicial es requerido en la solicitud");
    }
    Grafo instituto = convertirComputadorasAGrafo(computadoras);
    return calcularCaminosMinimos(instituto, solicitudCrearCaminosDto.getNombreComputadoraInicial(), solicitudCrearCaminosDto.getNombreComputadoraFinal());
  }

  private List<RespuestaCalcularCaminoDto> calcularCaminosMinimos(Grafo grafo, String nombreNodoInicial, String nombreNodoFinal) {
    Nodo nodoInicial = grafo.obtenerNodo(nombreNodoInicial);
    if (nodoInicial == null) {
      throw new IllegalArgumentException("No existe la computadora inicial solicitada");
    }
    ResultadoCalcularCaminosDto respuestaCalcularCaminoDto = caminosMinimos(grafo, nombreNodoInicial);
    if (nombreNodoFinal != null && !"".equals(nombreNodoFinal)) {
      if (!respuestaCalcularCaminoDto.getCaminosMinimos().containsKey(nombreNodoFinal)) {
        throw new IllegalArgumentException("No existe conexion(es) disponibles entre las computadoras seleccionadas");
      }
      return Collections.singletonList(new RespuestaCalcularCaminoDto(respuestaCalcularCaminoDto.getCaminosMinimos().get(nombreNodoFinal),
        respuestaCalcularCaminoDto.getPesosMinimos().get(nombreNodoFinal)));
    }
    List<RespuestaCalcularCaminoDto> respuesta = new LinkedList<>();
    for (Map.Entry<String, Queue<String>> entry : respuestaCalcularCaminoDto.getCaminosMinimos().entrySet()) {
      respuesta.add(new RespuestaCalcularCaminoDto(entry.getValue(), respuestaCalcularCaminoDto.getPesosMinimos().get(entry.getKey())));
    }
    return respuesta;
  }

  private ResultadoCalcularCaminosDto caminosMinimos(Grafo grafo, String nombreNodoInicial) {
    HashMap<String, BigDecimal> historialPesosMinimos = new HashMap<>();
    historialPesosMinimos.put(nombreNodoInicial, BigDecimal.ZERO);

    HashMap<String, Queue<String>> historialCaminosMinimos = new HashMap<>();
    historialCaminosMinimos.put(nombreNodoInicial, new LinkedList<>(Collections.singletonList(nombreNodoInicial)));

    TreeSet<Nodo> nodosPendientesDeProcesar = new TreeSet<>((o1, o2) -> {
      BigDecimal pesoMinimoNodo1 = historialPesosMinimos.get(o1.obtenerNombre());
      BigDecimal pesoMinimoNodo2 = historialPesosMinimos.get(o2.obtenerNombre());
      int diferenciaDePesos = pesoMinimoNodo1.compareTo(pesoMinimoNodo2);
      return diferenciaDePesos == 0 ? o1.obtenerNombre().compareTo(o2.obtenerNombre()) : diferenciaDePesos;
    });
    nodosPendientesDeProcesar.add(grafo.obtenerNodo(nombreNodoInicial));
    Set<Nodo> nodosProcesados = new HashSet<>();

    //Sea n = total de nodos en el grafo
    //Sea A = maximo de aristas en un nodo
    //Sea a = total de aristas en el grafo
    while (!nodosPendientesDeProcesar.isEmpty()) { //O(n) iteracion sobre todos los nodos en el peor de los casos (1era iteracion)
      Nodo nodoMasCercanoPendienteDeProcesar = nodosPendientesDeProcesar.pollFirst();  //O(1) lectura elemento minimo del arbol-mapa ordenado
      for (AristaConPeso arista : nodoMasCercanoPendienteDeProcesar.obtenerAristas()) {  //O(A) iteracion de las aristas a nivel nodo (2da iteracion)
        Nodo nodoAdjacente = grafo.obtenerNodo(arista.obtenerNombreOtroNodo(nodoMasCercanoPendienteDeProcesar.obtenerNombre())); //O(1) lectura de mapa
        if (!nodosProcesados.contains(nodoAdjacente)) { //O(1) lectura de mapa.  condicion***
          BigDecimal pesoMinimoNodoMasCercano = historialPesosMinimos.get(nodoMasCercanoPendienteDeProcesar.obtenerNombre()); //O(1) lectura de mapa
          BigDecimal pesoMinimoNodoAdjacente = historialPesosMinimos.get(nodoAdjacente.obtenerNombre()); //O(1) lectura de mapa
          BigDecimal posiblePesoMinimoNodoAdjacente = pesoMinimoNodoMasCercano.add(arista.obtenerPeso()); //O(1) lectura de mapa
          if (pesoMinimoNodoAdjacente == null || posiblePesoMinimoNodoAdjacente.compareTo(pesoMinimoNodoAdjacente) < 0) {
            historialPesosMinimos.put(nodoAdjacente.obtenerNombre(), posiblePesoMinimoNodoAdjacente);  //O(1) escritura en mapa
            Queue<String> historalDePasos = historialCaminosMinimos.get(nodoMasCercanoPendienteDeProcesar.obtenerNombre()); //O(1) lectura de mapa
            LinkedList<String> historialDePasosAdjacente = new LinkedList<>(historalDePasos);
            historialDePasosAdjacente.add(nodoAdjacente.obtenerNombre());  //O(1) escritura en cola
            historialCaminosMinimos.put(nodoAdjacente.obtenerNombre(), historialDePasosAdjacente); //O(1) escritura en mapa
            nodosPendientesDeProcesar.add(nodoAdjacente);   //O(log(n)) escritura en arbol-mapa ordenado
          }
        }
      }
      nodosProcesados.add(nodoMasCercanoPendienteDeProcesar); //O(1) escritura en mapa
    }
    //Complejidad a "simple vista":
    //# 1era iteracion * # 2da iteracion * O(complejidad maxima interna)
    //==> n * A * O(log(n)) = O(n * A * log(n))
    //Complejidad con mayor exactitud:
    //# 1era iteracion * # 2da iteracion * O(complejidad maxima interna)
    //==> n * A * O(complejidad maxima interna)
    //Siendo A el maximo de aristas en un nodo tenemos que: n * A >= a
    //Sea d = (n * A) - a
    //==> d + a = (n * A)
    //Reemplazando tenemos: (d + a) * O(complejidad maxima interna)
    //==> O(d * complejidad maxima interna en las iteraciones 'd') + O(a * complejidad maxima interna en la iteraciones 'a')
    //Pero por la condicion*** sabemos que solo procede una sola vez por arista
    //Para aristas previamente visitadas tenemos que la complejidad maxima interna es de O(1)
    //==> O(d * 1) + O(a * log(n))
    //==> O(d) + O(a * log(n))
    //==> O(a * log(n))
    return new ResultadoCalcularCaminosDto(historialCaminosMinimos, historialPesosMinimos);
  }

  private Grafo calcularArbolRecubridorMinimo(Grafo grafo) {
    List<AristaConPeso> aristasPendientesDeProcesar = new ArrayList<>(grafo.obtenerAristas().values());
    aristasPendientesDeProcesar.sort(Comparator.comparing(AristaConPeso::obtenerPeso));
    GrafoDefault arbolConPesoMinimo = new GrafoDefault();
    for (AristaConPeso arista : aristasPendientesDeProcesar) {
      GrafoDefault grafoConArista = new GrafoDefault(arbolConPesoMinimo);
      grafoConArista.agregarArista(arista);
      Nodo nodoRecientementeConectado = grafoConArista.obtenerNodo(arista.obtenerNombreNodoConectado1());
      if (calcularSiElGrafoTieneCiclos(nodoRecientementeConectado, grafoConArista, new HashSet<>(), new HashSet<>(), null)) {
        continue;
      }
      arbolConPesoMinimo = grafoConArista;
      if (arbolConPesoMinimo.cantidadDeNodos() == grafo.cantidadDeNodos()) {
        break;
      }
    }
    return arbolConPesoMinimo;
  }

  private boolean calcularSiElGrafoTieneCiclos(Nodo nodoActual, Grafo grafo, Set<Nodo> nodosVisitados, Set<Nodo> nodosSinCiclos, String nombreNodoPadre) {
    if (nodoActual == null) {
      return false;
    }
    nodosVisitados.add(nodoActual);
    for (AristaConPeso arista : nodoActual.obtenerAristas()) {
      if (arista.obtenerNombreOtroNodo(nodoActual.obtenerNombre()).equals(nombreNodoPadre)) {
        continue;
      }
      Nodo nodoAdjacente = grafo.obtenerNodos().get(arista.obtenerNombreOtroNodo(nodoActual.obtenerNombre()));
      if (nodosVisitados.contains(nodoAdjacente)) {
        return true;
      } else if (!nodosSinCiclos.contains(nodoActual) &&
        calcularSiElGrafoTieneCiclos(nodoAdjacente, grafo, nodosVisitados, nodosSinCiclos, nodoActual.obtenerNombre())) {
        return true;
      }
    }
    nodosVisitados.remove(nodoActual);
    nodosSinCiclos.add(nodoActual);
    return false;
  }
}
