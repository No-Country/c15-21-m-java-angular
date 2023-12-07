package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Orden.OrdenDto;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {

    void crearOrden(OrdenDto ordenDto);

    Optional<OrdenDto> buscarOrden(Integer id);

    void modificarOrden(OrdenDto orden);

    void eliminarOrden(Integer id);

    List<OrdenDto> obtenerOrdenes();
}
