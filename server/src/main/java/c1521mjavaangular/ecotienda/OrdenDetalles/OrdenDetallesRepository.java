package c1521mjavaangular.ecotienda.OrdenDetalles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenDetallesRepository extends JpaRepository<OrdenDetalles, Integer> {

    List<OrdenDetalles> findAllByOrdenId(Long id);
}
