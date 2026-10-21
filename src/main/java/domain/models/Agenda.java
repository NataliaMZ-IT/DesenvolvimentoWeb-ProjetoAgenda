package domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Agenda implements Serializable {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Informe a descrição.")
    @Size(max = 5000,
            message = "A descrição deve ter no máximo 5000 caracteres.")
    private String descricao;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Informe a data.")
    @FutureOrPresent(message = "A data não pode estar no passado.")
    private LocalDateTime data;

    @ManyToOne
    @NotNull(message = "Selecione um tipo de serviço.")
    private Servico servico;

    @ManyToOne
    @NotNull(message = "Selecione um cliente.")
    private Cliente cliente;

    @ManyToOne
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDataFormatada() {
        return data == null ? "" : data.format(DATE_FORMATTER);
    }
}