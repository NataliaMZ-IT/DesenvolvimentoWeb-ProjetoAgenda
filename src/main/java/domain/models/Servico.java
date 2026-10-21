package domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Servico implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Informe o nome do serviço.")
    @Size(max = 50, message = "O serviço deve ter no máximo 50 caracteres.")
    private String nome;

    @NotNull(message = "Informe o valor do serviço.")
    @DecimalMin(value = "0.01",
            inclusive = true,
            message = "O valor deve ser no mínimo 0.01")
    private BigDecimal valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}