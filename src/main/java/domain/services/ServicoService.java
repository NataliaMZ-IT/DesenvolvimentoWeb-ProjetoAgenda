package domain.services;

import domain.interfaces.IService;
import domain.models.Servico;
import domain.repositories.AgendaRepository;
import domain.repositories.ServicoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ServicoService implements IService<Servico> {
    private final ServicoRepository repository;
    private final AgendaRepository agendaRepository;

    public ServicoService(ServicoRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public List<Servico> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Servico> findOne(int id) {
        return repository.findById(id);
    }

    @Override
    public Servico save(Servico servico) {
        return repository.save(servico);
    }

    @Override
    public void delete(int id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Serviço não encontrado."));

        if (agendaRepository.existsByServico_Id(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir este serviço, pois existem agendamentos vinculados a ele.");
        }

        repository.deleteById(id);
    }
}
