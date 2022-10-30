package com.ceneval.exoal.jifo.grafo;

import java.math.BigDecimal;

interface AristaConPeso {
  Integer obtenerIdentificador();

  BigDecimal obtenerPeso();

  String obtenerNombreNodoConectado1();

  String obtenerNombreNodoConectado2();

  String obtenerNombreOtroNodo(String nombreNodo);
}
