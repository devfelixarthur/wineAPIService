package com.api.wiveService.WineService.repository;

import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.domain.wine.bean.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {

    @Query("SELECT COUNT(w) FROM Wine w")
    Long countAllWines();

    @Query("SELECT w FROM Wine w")
    Page<Wine> findAllWines(Pageable pageable);

    @Query("SELECT w FROM Wine w WHERE w.nome = :nome AND w.safra = :safra AND w.pais = :pais AND w.uva = :uva")
    Optional<Wine> buscarWine(@Param("nome") String nome, @Param("safra") Integer safra, @Param("pais") String pais, @Param("uva") String uva);
    @Query("SELECT w FROM Wine w WHERE w.id = :id")
    Optional<Wine> buscarWineById(@Param("id") Long id);

}
