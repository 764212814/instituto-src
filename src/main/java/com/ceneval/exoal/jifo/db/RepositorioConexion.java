package com.ceneval.exoal.jifo.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RepositorioConexion extends CrudRepository<Conexion, Integer> {
}