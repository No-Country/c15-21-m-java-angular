package c1521mjavaangular.ecotienda.services;

import c1521mjavaangular.ecotienda.dto.OrdenDto;
import c1521mjavaangular.ecotienda.dto.UsuarioDto;
import c1521mjavaangular.ecotienda.models.Orden;
import c1521mjavaangular.ecotienda.repositories.OrdenRepository;
import c1521mjavaangular.ecotienda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenServiceImp implements IOrdenService{

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void crearOrden(OrdenDto ordenDto) {
        Orden orden = new Orden();
        orden.setDetalles(ordenDto.getOrdenDetalles());
        orden.setUsuarios(usuarioRepository.findById(ordenDto.getUsuarioDto().getId()).get());
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

            ordenExistente.setUsuarios(usuarioRepository.findById(ordenDto.usuarioDto.getId()).get());
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
        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setId(orden.getUsuarios().getId());
        usuarioDto.setDireccion(orden.getUsuarios().getDireccion());
        usuarioDto.setNombre(orden.getUsuarios().getNombre());
        usuarioDto.setEmail(orden.getUsuarios().getEmail());

        ordenDto.setId(orden.getId());
        ordenDto.setUsuarioDto(usuarioDto);

        return ordenDto;
    }
}
