package com.michel.first_spring_app.controller;

import com.michel.first_spring_app.model.Jogador;
import com.michel.first_spring_app.service.JogadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService service;

    public JogadorController(JogadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Jogador> listar() {
        return service.listarJogadores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return service.buscarJogador(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Jogador não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Jogador> adicionar(@Valid @RequestBody Jogador jogador) {
        Jogador novo = service.adicionarJogador(jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Jogador jogador) {
        return service.atualizarJogador(id, jogador)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Jogador não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        service.removerJogador(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Jogador> listarPorCategoria(@PathVariable String categoria) {
        return service.listarPorCategoria(categoria);
    }
}

