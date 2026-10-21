package domain.services;

import domain.interfaces.IService;
import domain.models.Agenda;
import domain.repositories.AgendaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AgendaService implements IService<Agenda> {
    private final AgendaRepository repository;

    public AgendaService(AgendaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Agenda> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Agenda> findOne(int id) {
        return repository.findById(id);
    }

    @Override
    public Agenda save(Agenda agenda) {
        int usuarioId = agenda.getUsuario().getId();

        boolean horarioOcupado =
                repository.existsByUsuario_IdAndDataAndIdNot(
                        usuarioId,
                        agenda.getData(),
                        agenda.getId()
                );

        if (horarioOcupado) {
            throw new IllegalArgumentException(
                    "Você já possui uma agenda para esta data e horário."
            );
        }

        return repository.save(agenda);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Agenda> buscarComFiltros(
            Integer clienteId,
            Integer servicoId,
            LocalDate dataInicial,
            LocalDate dataFinal) {

        LocalDateTime dataHoraInicio = dataInicial != null
                ? dataInicial.atStartOfDay()
                : null;
        LocalDateTime dataHoraFim = dataFinal != null
                ? dataFinal.atTime(23, 59, 59)
                : null;

        return repository.buscarComFiltros(
                clienteId,
                servicoId,
                dataHoraInicio,
                dataHoraFim
        );
    }
}
