package domain.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Informe o nome do cliente.")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
    private String nome;

    @NotBlank(message = "Informe o telefone do cliente.")
    private String telefone;

    @NotBlank(message = "Informe o e-mail do cliente.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @Valid
    @NotNull(message = "O endereço é obrigatório.")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}