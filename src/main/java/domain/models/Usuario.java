package domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Informe o nome de usuário.")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
    private String usuario;

    @NotBlank(message = "Informe a senha de usuário.")
    @Size(min = 10, message = "A senha deve ter no mínimo 10 caracteres.")
    private String senha;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dttCadastro;

    @ManyToOne
    private TipoAcesso tipoAcesso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDttCadastro() {
        return dttCadastro;
    }

    public void setDttCadastro(LocalDate dttCadastro) {
        this.dttCadastro = dttCadastro;
    }

    public TipoAcesso getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(TipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }
}