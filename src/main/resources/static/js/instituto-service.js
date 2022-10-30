/**
 * Clientes REST de la API institucion
 */

function crearComputadoraRest(nombreComputadora) {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/computadora",
      method: 'POST',
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        "nombreComputadora": nombreComputadora
      })
    }).then(function () {
      exito('Computadora creada exitosamente');
    }, function (e) {
      error(e.responseText);
    });
  });
}

function eliminarComputadoraRest(nombreComputadora) {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/computadora",
      method: 'DELETE',
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        "nombreComputadora": nombreComputadora
      })
    }).then(function () {
      exito('Computadora eliminada exitosamente');
    }, function (e) {
      error(e.responseText);
    });
  });
}

function eliminarComputadorasRest() {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/computadoras",
      method: 'DELETE'
    }).then(function () {
      exito('Computadoras eliminadas exitosamente');
    }, function (e) {
      error(e.responseText);
    });
  });
}

function crearConexionRest(nombreComputadora1, nombreComputadora2, latencia) {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/conexion",
      method: 'POST',
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        "nombreComputadora1": nombreComputadora1,
        "nombreComputadora2": nombreComputadora2,
        "latencia": latencia
      })
    }).then(function () {
      exito('Conexion creada exitosamente');
    }, function (e) {
      error(e.responseText);
    });
  });
}

function eliminarConexionRest(nombreComputadora1, nombreComputadora2) {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/conexion",
      method: 'DELETE',
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        "nombreComputadora1": nombreComputadora1,
        "nombreComputadora2": nombreComputadora2
      })
    }).then(function () {
      exito('Conexion eliminada exitosamente');
    }, function (e) {
      error(e.responseText);
    });
  });
}

function resetearInstitutoRest() {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/default",
      method: 'POST'
    }).then(function () {
      exito('Informacion reseteada exitosamente');
    }, function (e) {
      error(e.responseText);
    });
  });
}

function obtenerComputadorasRest() {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/computadoras"
    }).then(function (data) {
      exito(data);
    }, function (e) {
      error(e.responseText);
    });
  });
}

function obtenerCaminosMinimosRest(nombreComputadoraInicial, nombreComputadoraFinal) {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/caminos",
      contentType: "application/json; charset=utf-8",
      data: {
        "nombreComputadoraInicial": nombreComputadoraInicial,
        "nombreComputadoraFinal": nombreComputadoraFinal
      }
    }).then(function (data) {
      exito(data);
    }, function (e) {
      error(e.responseText);
    });
  });
}

function obtenerConexionCompletaMinimaRest() {
  return new Promise(function (exito, error) {
    $.ajax({
      url: "/instituto/arbol-recubridor-minimo"
    }).then(function (data) {
      exito(data);
    }, function (e) {
      error(e.responseText);
    });
  });
}