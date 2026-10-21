package domain.services;

import domain.interfaces.IService;
import domain.models.Usuario;
import domain.repositories.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements IService<Usuario> {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> findOne(int id) {
        return repository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    // Metodo para codificar a senha
    public Usuario codificarSenha(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuario;
    }

    // Metodo para verificar se um usuário igual já está cadastrado
    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        if (repository.existsByUsuario(usuario.getUsuario())) {
            throw new IllegalArgumentException(
                    "Este nome de usuário já está cadastrado.");
        }

        usuario.setDttCadastro(LocalDate.now());

        codificarSenha(usuario);

        return repository.save(usuario);
    }

    // Metodo para buscar usuario e verificar senha
    @Transactional(readOnly = true)
    public Optional<Usuario> autenticar(
            String nomeUsuario, String senha) {

        return repository.findByUsuario(nomeUsuario)
                .filter(usuario ->
                        passwordEncoder.matches(
                                senha, usuario.getSenha()));
    }
}