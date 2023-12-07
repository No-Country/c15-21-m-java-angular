package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Orden.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {
}
