package domain.services;

import domain.interfaces.IService;
import domain.models.Endereco;
import domain.repositories.EnderecoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService implements IService<Endereco> {
    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Endereco> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Endereco> findOne(int id) {
        return repository.findById(id);
    }

    @Override
    public Endereco save(Endereco endereco) {
        return repository.save(endereco);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
