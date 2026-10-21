
package domain.controllers;

import domain.models.Cliente;
import domain.models.Endereco;
import domain.services.ClienteService;
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
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    private Cliente novoCliente() {
        Cliente cliente = new Cliente();
        cliente.setEndereco(new Endereco());
        return cliente;
    }

    @GetMapping("/clientes")
    public String mostrarClientes(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        model.addAttribute("clientes", service.findAll());

        // Object used by the forms when creating a new client
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", novoCliente());
        }

        return "cliente";
    }

    @GetMapping("/clientes/add")
    public String add(Model model) {
        model.addAttribute("cliente", novoCliente());
        model.addAttribute("clientes", service.findAll());
        return "cliente";
    }

    @GetMapping("/clientes/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Cliente cliente = service.findOne(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        if (cliente.getEndereco() == null) {
            cliente.setEndereco(new Endereco());
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", service.findAll());
        return "cliente";
    }

    @PostMapping("/clientes/delete/{id}")
    public ModelAndView delete(
            @PathVariable("id") int id,
            RedirectAttributes attributes) {

        try {
            service.delete(id);
            attributes.addFlashAttribute(
                    "mensagem", "Cliente excluído com sucesso.");
        } catch (IllegalStateException ex) {
            attributes.addFlashAttribute("erro", ex.getMessage());
        }

        return new ModelAndView("redirect:/clientes");
    }

    @PostMapping("/clientes/save")
    public ModelAndView save(
            @Valid Cliente cliente,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("cliente");
            mv.addObject("cliente", cliente);
            mv.addObject("clientes", service.findAll());
            return mv;
        }

        boolean novoCliente = cliente.getId() == 0;
        service.save(cliente);

        attributes.addFlashAttribute(
                "mensagem",
                novoCliente
                        ? "Cliente cadastrado com sucesso."
                        : "Cliente atualizado com sucesso.");

        return new ModelAndView("redirect:/clientes");
    }
}
