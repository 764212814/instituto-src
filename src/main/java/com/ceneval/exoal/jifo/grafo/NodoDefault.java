package com.ceneval.exoal.jifo.grafo;

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
final class NodoDefault implements Nodo {
  @EqualsAndHashCode.Include
  private final String nombre;
  @EqualsAndHashCode.Exclude
  private final Map<Integer, AristaConPeso> aristas = new HashMap<>();

  protected NodoDefault(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public Set<? extends AristaConPeso> obtenerAristas() {
    return new HashSet<>(aristas.values());
  }

  @Override
  public String obtenerNombre() {
    return nombre;
  }

  @Override
  public void agregarArista(AristaConPeso arista) {
    this.aristas.put(arista.obtenerIdentificador(), arista);
  }
}
