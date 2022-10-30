package com.ceneval.exoal.jifo.grafo;

import java.util.Map;

interface Grafo {
  Map<String, Nodo> obtenerNodos();

  Nodo obtenerNodo(String nombreNodo);

  int cantidadDeNodos();

  Nodo agregarNodoVacio(String nombreNodo);

  void agregarArista(AristaConPeso arista);

  Map<Integer, AristaConPeso> obtenerAristas();
}
