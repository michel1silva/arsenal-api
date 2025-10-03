package com.michel.first_spring_app.repository;

import com.michel.first_spring_app.model.Nacionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NacionalidadeRepository extends JpaRepository<Nacionalidade, String> {

    @Query("SELECT n FROM Nacionalidade n ORDER BY n.pais ASC")
    List<Nacionalidade> findAllOrderByPais();

    Optional<Nacionalidade> findByPais(String pais);

    Optional<Nacionalidade> findByIso2(String iso2);

    @Query("SELECT n FROM Nacionalidade n WHERE LOWER(n.pais) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(n.gentilicoMasc) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Nacionalidade> findByPaisOrGentilicoContainingIgnoreCase(@Param("termo") String termo);
}
