package com.ceneval.exoal.jifo.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RepositorioComputadora extends CrudRepository<Computadora, String> {
  @Query("SELECT COUNT(comp) > 0 FROM Computadora comp INNER JOIN comp.conexiones con WHERE comp.nombre = :nombreComputadora1 AND con.identificador IN " +
    "(SELECT con.identificador FROM Computadora comp INNER JOIN comp.conexiones con WHERE comp.nombre = :nombreComputadora2)")
  boolean estanDirectamenteConectadas(String nombreComputadora1, String nombreComputadora2);
}