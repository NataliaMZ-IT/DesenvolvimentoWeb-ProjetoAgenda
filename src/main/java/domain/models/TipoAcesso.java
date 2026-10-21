package domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
public class TipoAcesso implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Informe a descrição do tipo de acesso.")
    @Size(max = 20, message = "A descrição deve ter no máximo 20 caracteres.")
    private String descricao;

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
}