package domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
public class Endereco implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Informe o logradouro do endereço.")
    @Size(max = 50, message = "O logradouro deve ter no máximo 50 caracteres.")
    private String logradouro;

    @NotBlank(message = "Informe a UF do endereço.")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 caracteres.")
    private String uf;

    @NotBlank(message = "Informe o estado do endereço.")
    @Size(max = 20, message = "O estado deve ter no máximo 20 caracteres.")
    private String estado;

    @NotBlank(message = "Informe o CEP do endereço.")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres.")
    private String cep;

    @NotBlank(message = "Informe a cidade do endereço.")
    @Size(max = 50, message = "A cidade deve ter no máximo 50 caracteres.")
    private String cidade;

    private int numero;

    private String complemento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}