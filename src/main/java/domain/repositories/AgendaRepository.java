package domain.repositories;

import domain.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    boolean existsByCliente_Id(int clienteId);

    boolean existsByServico_Id(int servicoId);

    boolean existsByUsuario_IdAndDataAndIdNot(int usuarioId, LocalDateTime data, int agendaId);

    // Query to filter the agendas by client, service and range of date
    @Query("""
        SELECT a FROM Agenda a
        WHERE (:clienteId IS NULL OR a.cliente.id = :clienteId)
          AND (:servicoId IS NULL OR a.servico.id = :servicoId)
          AND (:dataInicial IS NULL OR a.data >= :dataInicial)
          AND (:dataFinal IS NULL OR a.data <= :dataFinal)
        ORDER BY a.data ASC
        """)
    List<Agenda> buscarComFiltros(
            @Param("clienteId") Integer clienteId,
            @Param("servicoId") Integer servicoId,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal
    );
}
