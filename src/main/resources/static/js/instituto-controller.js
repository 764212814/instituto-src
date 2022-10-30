$(document).ready(function () {
  mermaid.initialize({startOnLoad: false, flowchartConfig: {width: '100%'}});
  mostarTodasLasComputadoras();
});

function submitCrearComputadora() {
  resetearNotificacionUsuario("error-crear-computadora");
  $('#boton-crear-computadora').attr('disabled', true);
  crearComputadoraRest($('#crear-computadora-nombre').val())
    .then(
      function () {
        mostarTodasLasComputadoras();
        $('#crear-computadora-nombre').val('');
        $('#boton-crear-computadora').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-crear-computadora').attr('disabled', false);
        notificarUsuario("error-crear-computadora", mensajeError);
      }
    );
}

function submitEliminarComputadora() {
  resetearNotificacionUsuario("error-eliminar-computadora");
  $('#boton-eliminar-computadora').attr('disabled', true);
  eliminarComputadoraRest($('#eliminar-computadora-nombre').val())
    .then(
      function () {
        mostarTodasLasComputadoras();
        $('#eliminar-computadora-nombre').val('');
        $('#boton-eliminar-computadora').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-eliminar-computadora').attr('disabled', false);
        notificarUsuario("error-eliminar-computadora", mensajeError);
      }
    );
}

function submitCrearConexion() {
  resetearNotificacionUsuario("error-crear-conexion");
  $('#boton-crear-conexion').attr('disabled', true);
  crearConexionRest($('#crear-conexion-nombre1').val(), $('#crear-conexion-nombre2').val(), $('#crear-conexion-latencia').val())
    .then(
      function () {
        mostarTodasLasComputadoras();
        $('#crear-conexion-nombre1').val('');
        $('#crear-conexion-nombre2').val('');
        $('#crear-conexion-latencia').val('');
        $('#boton-crear-conexion').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-crear-conexion').attr('disabled', false);
        notificarUsuario("error-crear-conexion", mensajeError);
      }
    );
}

function submitEliminarConexion() {
  resetearNotificacionUsuario("error-eliminar-conexion");
  $('#boton-eliminar-conexion').attr('disabled', true);
  eliminarConexionRest($('#eliminar-conexion-nombre1').val(), $('#eliminar-conexion-nombre2').val())
    .then(
      function () {
        mostarTodasLasComputadoras();
        $('#eliminar-conexion-nombre1').val('');
        $('#eliminar-conexion-nombre2').val('');
        $('#boton-eliminar-conexion').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-eliminar-conexion').attr('disabled', false);
        notificarUsuario("error-eliminar-conexion", mensajeError);
      }
    );
}

function submitResetearComputadoras() {
  resetearNotificacionUsuario("error-reset-computadoras");
  $('#boton-reset-computadoras').attr('disabled', true);
  resetearInstitutoRest()
    .then(
      function () {
        mostarTodasLasComputadoras();
        $('#boton-reset-computadoras').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-reset-computadoras').attr('disabled', false);
        notificarUsuario("error-reset-computadoras", mensajeError);
      }
    );
}

function submitEliminarComputadoras() {
  resetearNotificacionUsuario("error-eliminar-computadoras");
  $('#boton-eliminar-computadoras').attr('disabled', true);
  eliminarComputadorasRest()
    .then(
      function () {
        mostarTodasLasComputadoras();
        $('#boton-eliminar-computadoras').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-eliminar-computadoras').attr('disabled', false);
        notificarUsuario("error-eliminar-computadoras", mensajeError);
      }
    );
}

function submitObtenerCaminosMinimos() {
  resetearNotificacionUsuario("error-camino-minimo");
  $('#boton-camino-minimo').attr('disabled', true);
  obtenerCaminosMinimosRest($('#camino-minimo-nombre1').val(), $('#camino-minimo-nombre2').val())
    .then(function (data) {
        var element = document.querySelector(".mermaid");
        var insertSvg = function (svgCode) {
          element.innerHTML = svgCode;
        };
        var definicionGrafo = '%%{init: { "theme": "forest" } }%%\nflowchart LR\n';
        for (var i = 0; i < data.length; i++) {
          if (data.length > 1 && data[i].caminoMinimo.length === 1) {
            continue;
          }
          if (!data[i].caminoMinimo || data[i].caminoMinimo.length === 0) {
            definicionGrafo += $('#camino-minimo-nombre1').val() + '\n';
          } else if (data[i].caminoMinimo.length === 1) {
            definicionGrafo += data[i].caminoMinimo[0] + '\n';
          } else {
            for (var j = 0; j < data[i].caminoMinimo.length - 1; j++) {
              definicionGrafo += data[i].caminoMinimo[j] + i + '((' + data[i].caminoMinimo[j] + '))=== ' + data[i].caminoMinimo[j + 1] + i + '((' + data[i].caminoMinimo[j + 1] + '))\n';
            }
            definicionGrafo += data[i].caminoMinimo[0] + i + '((' + data[i].caminoMinimo[0] + '))<-.LATENCIA TOTAL:' + data[i].pesoTotal + '.->' + data[i].caminoMinimo[data[i].caminoMinimo.length - 1] + i + '((' + data[i].caminoMinimo[data[i].caminoMinimo.length - 1] + '))\n';
          }
        }
        mermaid.render('grafoMinimo', definicionGrafo, insertSvg);
        $('#boton-camino-minimo').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-camino-minimo').attr('disabled', false);
        notificarUsuario("error-camino-minimo", mensajeError);
      });
}

function mostrarConexionCompletaMinima() {
  resetearNotificacionUsuario("error-conexion-completa");
  $('#boton-conexion-completa').attr('disabled', true);
  obtenerConexionCompletaMinimaRest()
    .then(function (data) {
        var element = document.querySelector(".mermaid");
        var insertSvg = function (svgCode) {
          element.innerHTML = svgCode;
        };
        var definicionGrafo = '%%{init: { "theme": "forest" } }%%\nflowchart LR\n';
        var mapaConexiones = {};
        for (var i = 0; i < data.computadoras.length; i++) {
          var computadora = data.computadoras[i];
          if (computadora.conexiones && computadora.conexiones.length > 0) {
            for (var j = 0; j < computadora.conexiones.length; j++) {
              var conexion = computadora.conexiones[j];
              if (!mapaConexiones[conexion.identificador]) {
                mapaConexiones[conexion.identificador] = true;
                definicionGrafo += computadora.nombre + '===|' + conexion.latencia + '| ' + conexion.computadoraConectada + '\n';
              }
            }
          } else {
            definicionGrafo += computadora.nombre + '\n';
          }
        }
        mermaid.render('grafoCompleta', definicionGrafo, insertSvg);
        $('#boton-conexion-completa').attr('disabled', false);
      },
      function (mensajeError) {
        $('#boton-conexion-completa').attr('disabled', false);
        notificarUsuario("error-conexion-completa", mensajeError);
      });
}


function mostarTodasLasComputadoras() {
  obtenerComputadorasRest()
    .then(function (data) {
        var element = document.querySelector(".mermaid");
        var insertSvg = function (svgCode) {
          element.innerHTML = svgCode;
        };
        var definicionGrafo = '%%{init: { "theme": "forest" } }%%\nflowchart\n';
        var mapaConexiones = {};
        for (var i = 0; i < data.computadoras.length; i++) {
          var computadora = data.computadoras[i];
          if (computadora.conexiones && computadora.conexiones.length > 0) {
            for (var j = 0; j < computadora.conexiones.length; j++) {
              var conexion = computadora.conexiones[j];
              if (!mapaConexiones[conexion.identificador]) {
                mapaConexiones[conexion.identificador] = true;
                definicionGrafo += computadora.nombre + '===|' + conexion.latencia + '| ' + conexion.computadoraConectada + '\n';
              }
            }
          } else {
            definicionGrafo += computadora.nombre + '\n';
          }
        }
        mermaid.render('grafo', definicionGrafo, insertSvg);
      },
      function (error) {
        alert(error);
      });
}

function notificarUsuario(idElemento, mensaje) {
  resetearNotificacionUsuario(idElemento);
  $('#' + idElemento).html("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n" +
    mensaje +
    "  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n" +
    "</div>");

}

function resetearNotificacionUsuario(idElemento) {
  $('#' + idElemento).text('');
}