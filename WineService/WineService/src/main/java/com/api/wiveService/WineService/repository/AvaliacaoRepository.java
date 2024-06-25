package com.api.wiveService.WineService.repository;

import com.api.wiveService.WineService.domain.avaliacao.bean.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>{

    @Query("SELECT a FROM Avaliacao a WHERE a.wine.id = :wineId AND a.status = 'Ativo'")
    List<Avaliacao> findAllByWineId(@Param("wineId") Long wineId);

    @Query("SELECT a FROM Avaliacao a WHERE a.wine.id = :wineId AND a.user.id = :userId")
    Optional<Avaliacao> finByIdUserAndIdWine(@Param("userId") Long userId, @Param("wineId") Long wineId);
}