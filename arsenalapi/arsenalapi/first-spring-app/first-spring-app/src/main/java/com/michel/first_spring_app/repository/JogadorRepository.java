package com.michel.first_spring_app.repository;

import com.michel.first_spring_app.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    
    @Query("SELECT j FROM Jogador j WHERE " +
           "LOWER(j.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.posicao) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.pais) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.gentilicoMasc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.categoria) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Jogador> findBySearchTerm(@Param("searchTerm") String searchTerm);

    @Query("SELECT j FROM Jogador j WHERE " +
           "LOWER(j.posicao) = LOWER(:position)")
    List<Jogador> findByPosicao(@Param("position") String position);

    List<Jogador> findByCategoria(String categoria);

    @Query("SELECT j FROM Jogador j WHERE " +
           "LOWER(j.posicao) = LOWER(:position) AND " +
           "LOWER(j.categoria) = LOWER(:category)")
    List<Jogador> findByPosicaoAndCategoria(@Param("position") String position, @Param("category") String category);

    @Query("SELECT j FROM Jogador j WHERE " +
           "(LOWER(j.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.posicao) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.pais) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.gentilicoMasc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.categoria) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
           "LOWER(j.posicao) = LOWER(:position)")
    List<Jogador> findBySearchTermAndPosicao(@Param("searchTerm") String searchTerm, @Param("position") String position);

    @Query("SELECT j FROM Jogador j WHERE " +
           "(LOWER(j.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.posicao) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.pais) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.gentilicoMasc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.categoria) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
           "LOWER(j.categoria) = LOWER(:category)")
    List<Jogador> findBySearchTermAndCategoria(@Param("searchTerm") String searchTerm, @Param("category") String category);

    @Query("SELECT j FROM Jogador j WHERE " +
           "(LOWER(j.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.posicao) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.pais) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.nacionalidade.gentilicoMasc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(j.categoria) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
           "LOWER(j.posicao) = LOWER(:position) AND " +
           "LOWER(j.categoria) = LOWER(:category)")
    List<Jogador> findBySearchTermAndPosicaoAndCategoria(@Param("searchTerm") String searchTerm, 
                                                         @Param("position") String position, 
                                                         @Param("category") String category);
}

