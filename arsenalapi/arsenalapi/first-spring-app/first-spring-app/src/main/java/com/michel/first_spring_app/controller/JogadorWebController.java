package com.michel.first_spring_app.controller;

import com.michel.first_spring_app.model.Jogador;
import com.michel.first_spring_app.model.Nacionalidade;
import com.michel.first_spring_app.repository.JogadorRepository;
import com.michel.first_spring_app.repository.NacionalidadeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class JogadorWebController {

    private final JogadorRepository jogadorRepository;
    private final NacionalidadeRepository nacionalidadeRepository;

    public JogadorWebController(JogadorRepository jogadorRepository, NacionalidadeRepository nacionalidadeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.nacionalidadeRepository = nacionalidadeRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Jogador> jogadores = jogadorRepository.findAll();
        
        // Calcular estatísticas
        int totalJogadores = jogadores.size();
        double valorTotalMercado = jogadores.stream()
                .mapToDouble(Jogador::getValorMercado)
                .sum();
        double salarioTotal = jogadores.stream()
                .mapToDouble(Jogador::getSalario)
                .sum();
        
        // Contratos vencendo nos próximos 6 meses
        long contratosVencendo = jogadores.stream()
                .filter(jogador -> {
                    LocalDate dataVencimento = LocalDate.now().plusMonths(jogador.getTempoContrato());
                    return dataVencimento.isBefore(LocalDate.now().plusMonths(6));
                })
                .count();
        
        // Formatar valores monetários
        String valorTotalMercadoFormatado = String.format("R$ %.2f", valorTotalMercado);
        String salarioTotalFormatado = String.format("R$ %.2f", salarioTotal);
        
        model.addAttribute("totalJogadores", totalJogadores);
        model.addAttribute("valorTotalMercado", valorTotalMercadoFormatado);
        model.addAttribute("salarioTotal", salarioTotalFormatado);
        model.addAttribute("contratosVencendo", contratosVencendo);
        
        return "index";
    }

    @GetMapping("/jogadores")
    public String listar(@RequestParam(required = false) String search,
                         @RequestParam(required = false) String position,
                         @RequestParam(required = false) String category,
                         Model model) {
        
        List<Jogador> jogadores;
        
        // Lógica de pesquisa combinada
        if (search != null && !search.trim().isEmpty() && 
            position != null && !position.trim().isEmpty() && 
            category != null && !category.trim().isEmpty()) {
            // Pesquisa + posição + categoria
            jogadores = jogadorRepository.findBySearchTermAndPosicaoAndCategoria(search.trim(), position, category);
        } else if (search != null && !search.trim().isEmpty() && 
                   position != null && !position.trim().isEmpty()) {
            // Pesquisa + posição
            jogadores = jogadorRepository.findBySearchTermAndPosicao(search.trim(), position);
        } else if (search != null && !search.trim().isEmpty() && 
                   category != null && !category.trim().isEmpty()) {
            // Pesquisa + categoria
            jogadores = jogadorRepository.findBySearchTermAndCategoria(search.trim(), category);
        } else if (position != null && !position.trim().isEmpty() && 
                   category != null && !category.trim().isEmpty()) {
            // Posição + categoria
            jogadores = jogadorRepository.findByPosicaoAndCategoria(position, category);
        } else if (search != null && !search.trim().isEmpty()) {
            // Apenas pesquisa
            jogadores = jogadorRepository.findBySearchTerm(search.trim());
        } else if (position != null && !position.trim().isEmpty()) {
            // Apenas posição
            jogadores = jogadorRepository.findByPosicao(position);
        } else if (category != null && !category.trim().isEmpty()) {
            // Apenas categoria
            jogadores = jogadorRepository.findByCategoria(category);
        } else {
            // Todos os jogadores
            jogadores = jogadorRepository.findAll();
        }
        
        model.addAttribute("jogadores", jogadores);
        return "jogadores-list";
    }

    @GetMapping("/jogadores/novo")
    public String novoForm(Model model) {
        model.addAttribute("jogador", new Jogador());
        model.addAttribute("nacionalidades", nacionalidadeRepository.findAllOrderByPais());
        return "jogador-form";
    }

    @PostMapping("/jogadores")
    public String salvar(@ModelAttribute Jogador jogador, @RequestParam String nacionalidadeIso2, 
                        HttpServletRequest request, Model model) {
        try {
            // Validar campos obrigatórios
            String validationError = validateJogadorFields(jogador, nacionalidadeIso2);
            if (validationError != null) {
                model.addAttribute("error", validationError);
                model.addAttribute("jogador", jogador);
                model.addAttribute("nacionalidades", nacionalidadeRepository.findAllOrderByPais());
                return "jogador-form";
            }
            
            // Validar posição
            if (!isValidPosition(jogador.getPosicao())) {
                model.addAttribute("error", "Posição inválida. Posições válidas: Goleiro, Zagueiro, Lateral, Volante, Meio-campo, Atacante");
                model.addAttribute("jogador", jogador);
                model.addAttribute("nacionalidades", nacionalidadeRepository.findAllOrderByPais());
                return "jogador-form";
            }
            
            // Validar categoria
            if (!isValidCategoria(jogador.getCategoria())) {
                model.addAttribute("error", "Categoria inválida. Categorias válidas: principal, sub-23, feminino");
                model.addAttribute("jogador", jogador);
                model.addAttribute("nacionalidades", nacionalidadeRepository.findAllOrderByPais());
                return "jogador-form";
            }
            
            Nacionalidade nacionalidade = nacionalidadeRepository.findById(nacionalidadeIso2)
                    .orElseThrow(() -> new IllegalArgumentException("Nacionalidade inválida"));
            jogador.setNacionalidade(nacionalidade);
            jogadorRepository.save(jogador);
            return "redirect:/jogadores";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar jogador: " + e.getMessage());
            model.addAttribute("jogador", jogador);
            model.addAttribute("nacionalidades", nacionalidadeRepository.findAllOrderByPais());
            return "jogador-form";
        }
    }
    
    private String validateJogadorFields(Jogador jogador, String nacionalidadeIso2) {
        // Verificar se algum campo obrigatório está nulo ou vazio
        if (jogador.getNome() == null || jogador.getNome().trim().isEmpty()) {
            return "Campo alterado não válido: Nome é obrigatório";
        }
        
        if (jogador.getDataNascimento() == null) {
            return "Campo alterado não válido: Data de nascimento é obrigatória";
        }
        
        if (jogador.getPosicao() == null || jogador.getPosicao().trim().isEmpty()) {
            return "Campo alterado não válido: Posição é obrigatória";
        }
        
        if (jogador.getNumero() == null || jogador.getNumero() < 1 || jogador.getNumero() > 99) {
            return "Campo alterado não válido: Número deve estar entre 1 e 99";
        }
        
        if (jogador.getSalario() == null || jogador.getSalario() <= 0) {
            return "Campo alterado não válido: Salário deve ser maior que zero";
        }
        
        if (jogador.getValorMercado() == null || jogador.getValorMercado() <= 0) {
            return "Campo alterado não válido: Valor de mercado deve ser maior que zero";
        }
        
        if (jogador.getValorContrato() == null || jogador.getValorContrato() <= 0) {
            return "Campo alterado não válido: Valor do contrato deve ser maior que zero";
        }
        
        if (jogador.getTempoContrato() == null || jogador.getTempoContrato() < 1 || jogador.getTempoContrato() > 10) {
            return "Campo alterado não válido: Tempo de contrato deve estar entre 1 e 10 anos";
        }
        
        if (jogador.getCategoria() == null || jogador.getCategoria().trim().isEmpty()) {
            return "Campo alterado não válido: Categoria é obrigatória";
        }
        
        if (nacionalidadeIso2 == null || nacionalidadeIso2.trim().isEmpty()) {
            return "Campo alterado não válido: Nacionalidade é obrigatória";
        }
        
        // Verificar se o nome contém apenas caracteres válidos
        if (!jogador.getNome().matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            return "Campo alterado não válido: Nome contém caracteres inválidos";
        }
        
        return null; // Nenhum erro encontrado
    }
    
    private boolean isValidCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            return false;
        }
        
        String[] categoriasValidas = {"principal", "sub-23", "feminino"};
        for (String categoriaValida : categoriasValidas) {
            if (categoriaValida.equalsIgnoreCase(categoria.trim())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidPosition(String posicao) {
        if (posicao == null || posicao.trim().isEmpty()) {
            return false;
        }
        
        String[] posicoesValidas = {"Goleiro", "Zagueiro", "Lateral", "Volante", "Meio-campo", "Atacante"};
        for (String posicaoValida : posicoesValidas) {
            if (posicaoValida.equalsIgnoreCase(posicao.trim())) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/jogadores/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("jogador", jogadorRepository.findById(id).orElseThrow());
        model.addAttribute("nacionalidades", nacionalidadeRepository.findAllOrderByPais());
        return "jogador-form";
    }

    @GetMapping("/jogadores/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        jogadorRepository.deleteById(id);
        return "redirect:/jogadores";
    }

    // Redirecionamento para páginas não encontradas
    @GetMapping("/{path:^(?!jogadores|favicon\\.ico|style\\.css|foto_fundo_app\\.jpg|favicon_bola\\.png).*$}")
    public String redirectToHome() {
        return "redirect:/";
    }
}

