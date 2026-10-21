package domain.services;

import domain.interfaces.IService;
import domain.models.Cliente;
import domain.repositories.AgendaRepository;
import domain.repositories.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IService<Cliente> {
    private final ClienteRepository repository;
    private final AgendaRepository agendaRepository;

    public ClienteService(ClienteRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Cliente> findOne(int id) {
        return repository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public void delete(int id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cliente não encontrado."));

        if (agendaRepository.existsByCliente_Id(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir este cliente, pois existem agendamentos vinculados a ele.");
        }

        repository.deleteById(id);
    }


}
