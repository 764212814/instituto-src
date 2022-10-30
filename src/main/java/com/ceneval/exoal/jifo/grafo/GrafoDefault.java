package com.ceneval.exoal.jifo.grafo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
final class GrafoDefault implements Grafo {

  private final Map<String, Nodo> nodos = new HashMap<>();

  protected GrafoDefault(GrafoDefault grafoDefault) {
    this.nodos.putAll(grafoDefault.obtenerNodos());
  }

  @Override
  public Nodo agregarNodoVacio(String nombreNodo) {
    Nodo nuevoNodo = new NodoDefault(nombreNodo);
    this.nodos.put(nombreNodo, nuevoNodo);
    return nuevoNodo;
  }

  @Override
  public void agregarArista(AristaConPeso arista) {
    Nodo nodo1 = obtenerNodo(arista.obtenerNombreNodoConectado1());
    Nodo nodo2 = obtenerNodo(arista.obtenerNombreNodoConectado2());
    if (nodo1 == null) {
      nodo1 = agregarNodoVacio(arista.obtenerNombreNodoConectado1());
    }
    if (nodo2 == null) {
      nodo2 = agregarNodoVacio(arista.obtenerNombreNodoConectado2());
    }
    nodo1.agregarArista(arista);
    nodo2.agregarArista(arista);
  }

  @Override
  public Nodo obtenerNodo(String nombreNodo) {
    return this.nodos.get(nombreNodo);
  }

  @Override
  public int cantidadDeNodos() {
    return this.nodos.size();
  }

  @Override
  public Map<Integer, AristaConPeso> obtenerAristas() {
    return this.nodos.entrySet().stream().flatMap(nodo -> nodo.getValue().obtenerAristas().stream()).collect(Collectors.toMap(AristaConPeso::obtenerIdentificador, n -> n, (n1, n2) -> n1));
  }

  public Map<String, Nodo> obtenerNodos() {
    return this.nodos;
  }
}
