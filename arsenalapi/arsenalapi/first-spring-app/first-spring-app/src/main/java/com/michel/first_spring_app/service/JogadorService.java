package com.michel.first_spring_app.service;

import com.michel.first_spring_app.model.Jogador;
import com.michel.first_spring_app.repository.JogadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

    private final JogadorRepository repository;

    public JogadorService(JogadorRepository repository) {
        this.repository = repository;
    }

    public List<Jogador> listarJogadores() {
        return repository.findAll();
    }

    public Optional<Jogador> buscarJogador(Long id) {
        return repository.findById(id);
    }

    public Jogador adicionarJogador(Jogador jogador) {
        return repository.save(jogador);
    }

    public void removerJogador(Long id) {
        repository.deleteById(id);
    }

    public Optional<Jogador> atualizarJogador(Long id, Jogador jogador) {
        return repository.findById(id).map(j -> {
            j.setNome(jogador.getNome());
            j.setDataNascimento(jogador.getDataNascimento());
            j.setNacionalidade(jogador.getNacionalidade());
            j.setPosicao(jogador.getPosicao());
            j.setNumero(jogador.getNumero());
            j.setSalario(jogador.getSalario());
            j.setValorMercado(jogador.getValorMercado());
            j.setValorContrato(jogador.getValorContrato());
            j.setTempoContrato(jogador.getTempoContrato());
            j.setCategoria(jogador.getCategoria());
            return repository.save(j);
        });
    }

    public List<Jogador> listarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria);
    }
}

