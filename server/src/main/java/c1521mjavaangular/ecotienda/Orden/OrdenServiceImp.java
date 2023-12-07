package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Usuarios.UsuariosDto;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenServiceImp implements IOrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public void crearOrden(OrdenDto ordenDto) {
        Orden orden = new Orden();
        orden.setDetalles(ordenDto.getOrdenDetalles());
        orden.setUsuarios(usuariosRepository.findById(ordenDto.getUsuariosDto().getId()).get());
        ordenRepository.save(orden);
    }

    @Override
    public Optional<OrdenDto> buscarOrden(Integer id) {
        return Optional.ofNullable(ordenRepository.findById(id)
                .stream()
                .map(this::convertEntityToDtoList)
                .collect(Collectors.toList()).get(0));
    }

    @Override
    public void modificarOrden(OrdenDto ordenDto) {
        Optional<Orden> ordenExistenteOptional = ordenRepository.findById(ordenDto.getId());

        if (ordenExistenteOptional.isPresent()){
            Orden ordenExistente = ordenExistenteOptional.get();

            ordenExistente.setUsuarios(usuariosRepository.findById(ordenDto.usuariosDto.getId()).get());
            ordenExistente.setDetalles(ordenDto.getOrdenDetalles());

            ordenRepository.save(ordenExistente);
        }
        else {
            throw new RuntimeException("no existe la orden con el id " + ordenDto.getId());
        }

    }

    @Override
    public void eliminarOrden(Integer id) {
        ordenRepository.deleteById(id);
    }

    @Override
    public List<OrdenDto> obtenerOrdenes() {
        return ordenRepository.findAll()
                .stream()
                .map(this::convertEntityToDtoList)
                .collect(Collectors.toList());
    }

    public OrdenDto convertEntityToDtoList(Orden orden){
        OrdenDto ordenDto = new OrdenDto();
        UsuariosDto usuariosDto = new UsuariosDto();

        usuariosDto.setId(orden.getUsuarios().getId());
        usuariosDto.setDireccion(orden.getUsuarios().getDireccion());
        usuariosDto.setNombre(orden.getUsuarios().getNombre());
        usuariosDto.setEmail(orden.getUsuarios().getEmail());

        ordenDto.setId(orden.getId());
        ordenDto.setUsuariosDto(usuariosDto);

        return ordenDto;
    }
}
