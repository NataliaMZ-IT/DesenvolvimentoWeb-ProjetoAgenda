package domain.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CadastroDTO {

    @NotBlank(message = "Informe o nome de usuário.")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
    private String usuario;

    @NotBlank(message = "Informe a senha.")
    @Size(min = 10, message = "A senha deve ter no mínimo 10 caracteres.")
    private String senha;

    @NotBlank(message = "Confirme sua senha.")
    private String confirmarSenha;

    @NotNull(message = "Selecione um tipo de acesso.")
    private Integer tipoAcessoId;

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

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public Integer getTipoAcessoId() {
        return tipoAcessoId;
    }

    public void setTipoAcessoId(Integer tipoAcessoId) {
        this.tipoAcessoId = tipoAcessoId;
    }
}