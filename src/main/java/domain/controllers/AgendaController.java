package domain.controllers;

import domain.models.Agenda;
import domain.models.Usuario;
import domain.services.AgendaService;
import domain.services.ClienteService;
import domain.services.ServicoService;
import domain.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class AgendaController {
    private final AgendaService service;
    private final ServicoService servicoService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    public AgendaController(AgendaService service,
                            ServicoService servicoService,
                            ClienteService clienteService,
                            UsuarioService usuarioService)
    {
        this.service = service;
        this.servicoService = servicoService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/agenda")
    public String mostrarAgenda(
            Model model, HttpSession session,
            @RequestParam(required = false) Integer clienteId,
            @RequestParam(required = false) Integer servicoId,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        model.addAttribute("agendas", service.buscarComFiltros(
                clienteId,
                servicoId,
                dataInicial,
                dataFinal
        ));

        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("servicos", servicoService.findAll());

        model.addAttribute("clienteId", clienteId);
        model.addAttribute("servicoId", servicoId);
        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);

        model.addAttribute("filtrosAtivos",
                clienteId != null
                        || servicoId != null
                        || dataInicial != null
                        || dataFinal != null);

        return "agenda";
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("agendaAdd");
        mv.addObject("agenda", new Agenda());
        mv.addObject("clientes", clienteService.findAll());
        mv.addObject("servicos", servicoService.findAll());

        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id) {
        Agenda agenda = service.findOne(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agenda não encontrada"));

        ModelAndView mv = new ModelAndView("agendaAdd");
        mv.addObject("agenda", agenda);
        mv.addObject("clientes", clienteService.findAll());
        mv.addObject("servicos", servicoService.findAll());

        return mv;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, RedirectAttributes attributes) {
        service.delete(id);
        attributes.addFlashAttribute("mensagem", "Agenda excluída com sucesso.");
        return new ModelAndView("redirect:/agenda");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Agenda agenda, BindingResult result,
                             RedirectAttributes attributes, HttpSession session) {
        if(result.hasErrors()) {
            return add();
        }

        Integer usuarioId = (Integer) session.getAttribute("usuarioLogado");
        if (usuarioId == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = usuarioService.findOne(usuarioId)
        .orElseThrow(() ->
                new IllegalArgumentException("Usuário não encontrado.")
        );

        agenda.setUsuario(usuario);

        boolean novaAgenda = agenda.getId() == 0;

        try{
            service.save(agenda);
            attributes.addFlashAttribute("mensagem", novaAgenda
                    ? "Agenda realizada com sucesso."
                    : "Agenda atualizada com sucesso.");

            return new ModelAndView("redirect:/agenda");
        } catch (IllegalArgumentException ex){
            result.reject("erro", ex.getMessage());
            return add();
        }
    }
}
