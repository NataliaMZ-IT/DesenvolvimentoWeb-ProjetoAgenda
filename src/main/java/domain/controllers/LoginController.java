package domain.controllers;

import domain.models.TipoAcesso;
import domain.models.dtos.CadastroDTO;
import domain.models.dtos.LoginDTO;
import domain.models.Usuario;
import domain.services.TipoAcessoService;
import domain.services.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;
    private final TipoAcessoService tipoAcessoService;

    public LoginController(UsuarioService usuarioService, TipoAcessoService tipoAcessoService) {
        this.usuarioService = usuarioService;
        this.tipoAcessoService = tipoAcessoService;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        if (!model.containsAttribute("loginDTO")) {
            model.addAttribute("loginDTO", new LoginDTO());
        }

        if (!model.containsAttribute("cadastroDTO")) {
            model.addAttribute("cadastroDTO", new CadastroDTO());
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("loginDTO") LoginDTO loginDTO,
            BindingResult result,
            Model model,
            jakarta.servlet.http.HttpServletRequest request) {

        if (result.hasErrors()) {
            model.addAttribute("cadastroDTO", new CadastroDTO());
            return "login";
        }

        Usuario usuario = usuarioService
                .autenticar(loginDTO.getUsuario(), loginDTO.getSenha())
                .orElse(null);

        if (usuario == null) {
            model.addAttribute("erro", "Usuário ou senha inválidos.");
            model.addAttribute("cadastroDTO", new CadastroDTO());
            return "login";
        }

        // Controle de sessão, caso exista uma sessão já criada, será invalidada antes de criar uma nova
        jakarta.servlet.http.HttpSession sessaoAtual = request.getSession(false);

        if (sessaoAtual != null) {
            sessaoAtual.invalidate();
        }

        request.getSession(true)
                .setAttribute("usuarioLogado", usuario.getId());

        return "redirect:/agenda";
    }

    @PostMapping("/register")
    public String registrar(
            @Valid @ModelAttribute("cadastroDTO") CadastroDTO cadastroDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!cadastroDTO.getSenha().equals(cadastroDTO.getConfirmarSenha())) {
            result.rejectValue(
                    "confirmarSenha",
                    "senha.diferente",
                    "As senhas não coincidem.");
        }

        if (result.hasErrors()) {
            model.addAttribute("loginDTO", new LoginDTO());
            return "usuarioCadastro";
        }

        TipoAcesso tipoAcesso = tipoAcessoService.findOne(cadastroDTO.getTipoAcessoId() == 0
                        ? -1
                        : cadastroDTO.getTipoAcessoId())
                .orElse(null);

        if (tipoAcesso == null) {
            result.rejectValue(
                    "tipoAcessoId",
                    "tipoAcesso.invalido",
                    "Selecione um tipo de acesso válido.");

            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("tiposAcesso", tipoAcessoService.findAll());
            return "usuarioCadastro";
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(cadastroDTO.getUsuario());
        usuario.setSenha(cadastroDTO.getSenha());
        usuario.setTipoAcesso(tipoAcesso);

        try {
            usuarioService.cadastrar(usuario);
        } catch (IllegalArgumentException ex) {
            result.rejectValue(
                    "usuario", "usuario.duplicado", ex.getMessage());
            model.addAttribute("loginDTO", new LoginDTO());

            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("tiposAcesso", tipoAcessoService.findAll());
            return "usuarioCadastro";
        }

        redirectAttributes.addFlashAttribute(
                "mensagem", "Cadastro realizado! Entre com sua conta.");

        return "redirect:/login";
    }

    @GetMapping("/usuarioCadastro")
    public String mostrarCadastro(Model model) {
        model.addAttribute("cadastroDTO", new CadastroDTO());
        model.addAttribute("tiposAcesso", tipoAcessoService.findAll());
        return "usuarioCadastro";
    }
}
