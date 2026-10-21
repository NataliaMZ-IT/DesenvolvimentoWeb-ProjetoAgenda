package domain.services;

import domain.interfaces.IService;
import domain.models.TipoAcesso;
import domain.repositories.TipoAcessoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TipoAcessoService implements IService<TipoAcesso> {
    private final TipoAcessoRepository repository;

    public TipoAcessoService(TipoAcessoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TipoAcesso> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TipoAcesso> findOne(int id) {
        return repository.findById(id);
    }

    @Override
    public TipoAcesso save(TipoAcesso tipoAcesso) {
        return repository.save(tipoAcesso);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
