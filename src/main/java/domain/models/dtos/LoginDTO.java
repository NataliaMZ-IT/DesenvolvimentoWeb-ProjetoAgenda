package domain.models.dtos;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Informe o nome de usuário.")
    private String usuario;

    @NotBlank(message = "Informe a senha.")
    private String senha;

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

}
