package com.ceneval.exoal.jifo.grafo;

import com.ceneval.exoal.jifo.dto.ComputadoraDto;
import com.ceneval.exoal.jifo.dto.ConexionDto;
import com.ceneval.exoal.jifo.dto.RespuestaCalcularCaminoDto;
import com.ceneval.exoal.jifo.dto.SolicitudCrearCaminosDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@ExtendWith( SpringExtension.class )
public class ServicioGrafoTest {

    private final ServicioGrafo servicioGrafo = new ServicioGrafoDefault();

    @Test
    public void testGrafoCaminosMinimos() {
        //DADO
        SolicitudCrearCaminosDto solicitudCrearCaminoDto = new SolicitudCrearCaminosDto();
        solicitudCrearCaminoDto.setNombreComputadoraInicial("a");
        solicitudCrearCaminoDto.setNombreComputadoraFinal("c");

        SolicitudCrearCaminosDto solicitudCrearCaminosDto = new SolicitudCrearCaminosDto();
        solicitudCrearCaminosDto.setNombreComputadoraInicial("a");

        ConexionDto ab = new ConexionDto(1, BigDecimal.ONE, "b");
        ConexionDto ac = new ConexionDto(2, BigDecimal.TEN, "c");
        ComputadoraDto a = new ComputadoraDto("a", Set.of(ab, ac));

        ConexionDto bc = new ConexionDto(3, BigDecimal.ONE, "c");
        ComputadoraDto b = new ComputadoraDto("b", Set.of(bc));

        ComputadoraDto c = new ComputadoraDto("c", Set.of());

        //CUANDO
        List<RespuestaCalcularCaminoDto> unicoCamino = servicioGrafo.calcularCaminosMinimos(solicitudCrearCaminoDto, Set.of(a, b, c));
        List<RespuestaCalcularCaminoDto> caminos = servicioGrafo.calcularCaminosMinimos(solicitudCrearCaminosDto, Set.of(a, b, c));

        //DESPUES
        Assertions.assertEquals(1, unicoCamino.size());
        Assertions.assertNotNull(unicoCamino.get(0));
        Assertions.assertEquals(BigDecimal.valueOf(2), unicoCamino.get(0).getPesoTotal());
        Assertions.assertNotNull(unicoCamino.get(0).getCaminoMinimo());
        Assertions.assertEquals(3, unicoCamino.get(0).getCaminoMinimo().size());
        Assertions.assertEquals(List.of("a", "b", "c"), unicoCamino.get(0).getCaminoMinimo());

        Assertions.assertEquals(3, caminos.size());
        Assertions.assertNotNull(caminos.get(0));
        Assertions.assertEquals(BigDecimal.valueOf(0), caminos.get(0).getPesoTotal());
        Assertions.assertNotNull(caminos.get(0).getCaminoMinimo());
        Assertions.assertEquals(1, caminos.get(0).getCaminoMinimo().size());
        Assertions.assertEquals(List.of("a"), caminos.get(0).getCaminoMinimo());

        Assertions.assertNotNull(caminos.get(1));
        Assertions.assertEquals(BigDecimal.valueOf(1), caminos.get(1).getPesoTotal());
        Assertions.assertNotNull(caminos.get(1).getCaminoMinimo());
        Assertions.assertEquals(2, caminos.get(1).getCaminoMinimo().size());
        Assertions.assertEquals(List.of("a", "b"), caminos.get(1).getCaminoMinimo());

        Assertions.assertNotNull(caminos.get(2));
        Assertions.assertEquals(BigDecimal.valueOf(2), caminos.get(2).getPesoTotal());
        Assertions.assertNotNull(caminos.get(2).getCaminoMinimo());
        Assertions.assertEquals(3, caminos.get(2).getCaminoMinimo().size());
        Assertions.assertEquals(List.of("a", "b", "c"), caminos.get(2).getCaminoMinimo());
    }

    @Test
    public void testGrafoCaminoMinimoSinConexiones() {
        //DADO
        SolicitudCrearCaminosDto solicitudCrearCaminosDto = new SolicitudCrearCaminosDto();
        solicitudCrearCaminosDto.setNombreComputadoraInicial("a");
        solicitudCrearCaminosDto.setNombreComputadoraFinal("c");

        ComputadoraDto a = new ComputadoraDto("a", Set.of());

        ComputadoraDto c = new ComputadoraDto("c", Set.of());

        //CUANDO + DESPUES
        Assertions.assertThrows(IllegalArgumentException.class, () -> servicioGrafo.calcularCaminosMinimos(solicitudCrearCaminosDto, Set.of(a, c)));
    }

    @Test
    public void testGrafoCaminoMinimoSinNombreInicial() {
        //DADO
        SolicitudCrearCaminosDto solicitudCrearCaminosDto = new SolicitudCrearCaminosDto();
        solicitudCrearCaminosDto.setNombreComputadoraFinal("c");

        //CUANDO + DESPUES
        Assertions.assertThrows(IllegalArgumentException.class, () -> servicioGrafo.calcularCaminosMinimos(solicitudCrearCaminosDto, Set.of()));
    }

    @Test
    public void testGrafoCaminoMinimoSinCoincidencia() {
        //DADO
        SolicitudCrearCaminosDto solicitudCrearCaminosDto = new SolicitudCrearCaminosDto();
        solicitudCrearCaminosDto.setNombreComputadoraInicial("a");
        solicitudCrearCaminosDto.setNombreComputadoraFinal("c");

        ComputadoraDto c = new ComputadoraDto("c", Set.of());

        //CUANDO + DESPUES
        Assertions.assertThrows(IllegalArgumentException.class, () -> servicioGrafo.calcularCaminosMinimos(solicitudCrearCaminosDto, Set.of(c)));
    }
}
