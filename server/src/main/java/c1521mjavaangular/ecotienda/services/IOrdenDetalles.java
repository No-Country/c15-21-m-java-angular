package c1521mjavaangular.ecotienda.services;

import c1521mjavaangular.ecotienda.models.OrdenDetalles;

import java.util.List;
import java.util.Optional;

public interface IOrdenDetalles {

    void crearOrdenDetalles(OrdenDetalles ordenDetalles);

    Optional<OrdenDetalles> buscarOrdenDetalles(Long id);

    List<OrdenDetalles> listarOrdenDetalles();

    void modificarOrdenDetalles(OrdenDetalles ordenDetalles);

    void eliminarOrdenDetalles(Long id);
}
