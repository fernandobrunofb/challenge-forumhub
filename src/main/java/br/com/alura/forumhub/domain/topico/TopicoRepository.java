package br.com.alura.forumhub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    @Query("""
            SELECT t FROM Topico t
            WHERE (:nomeCurso IS NULL OR t.curso.nome = :nomeCurso)
            AND (:ano IS NULL OR t.dataCriacao BETWEEN :inicio AND :fim)
            """)
    Page<Topico> findByFiltros(
            @Param("nomeCurso") String nomeCurso,
            @Param("ano") Integer ano,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable
    );
}