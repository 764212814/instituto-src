package com.ceneval.exoal.jifo.grafo;

import java.util.Set;

interface Nodo {
  Set<? extends AristaConPeso> obtenerAristas();

  String obtenerNombre();

  void agregarArista(AristaConPeso arista);
}
