
package domain.controllers;

import domain.models.Servico;
import domain.services.ServicoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @GetMapping("/servicos")
    public String mostrarServicos(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        model.addAttribute("servicos", service.findAll());

        if (!model.containsAttribute("servico")) {
            model.addAttribute("servico", new Servico());
        }

        return "servico";
    }

    @GetMapping("/servicos/add")
    public String add(Model model) {
        model.addAttribute("servico", new Servico());
        model.addAttribute("servicos", service.findAll());
        return "servico";
    }

    @GetMapping("/servicos/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Servico servico = service.findOne(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        model.addAttribute("servico", servico);
        model.addAttribute("servicos", service.findAll());
        return "servico";
    }

    @PostMapping("/servicos/delete/{id}")
    public ModelAndView delete(
            @PathVariable("id") int id,
            RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute(
                    "mensagem", "Serviço excluído com sucesso.");
        } catch (IllegalStateException ex) {
            attributes.addFlashAttribute("erro", ex.getMessage());
        }

        return new ModelAndView("redirect:/servicos");
    }

    @PostMapping("/servicos/save")
    public ModelAndView save(
            @Valid Servico servico,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("servico");
            mv.addObject("servico", servico);
            mv.addObject("servicos", service.findAll());
            return mv;
        }

        boolean novoServico = servico.getId() == 0;
        service.save(servico);

        attributes.addFlashAttribute(
                "mensagem",
                novoServico
                        ? "Serviço cadastrado com sucesso."
                        : "Serviço atualizado com sucesso.");

        return new ModelAndView("redirect:/servicos");
    }
}
