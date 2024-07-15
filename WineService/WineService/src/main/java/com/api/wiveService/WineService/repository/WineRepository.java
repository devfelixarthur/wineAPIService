package com.api.wiveService.WineService.repository;

import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.domain.wine.bean.Wine;
import com.api.wiveService.WineService.domain.wine.dto.ResponseAdegaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("SELECT w FROM Wine w WHERE LOWER(w.nome) LIKE LOWER(CONCAT('%', :wineName, '%')) AND w.status = 'Ativo'")
    Page<Wine> findByNameContaining(String wineName, Pageable pageable);


    @Query("SELECT DISTINCT(w.adega) FROM Wine w WHERE w.status = 'Ativo'")
    List<String> getAllAdegas();

    @Query("SELECT w FROM Wine w WHERE " +
            "(:wineName IS NULL OR :wineName = '' OR LOWER(w.nome) LIKE LOWER(CONCAT('%', :wineName, '%'))) AND " +
            "(:pais IS NULL OR :pais = '' OR w.pais = :pais) AND " +
            "(:uva IS NULL OR :uva = '' OR LOWER(w.uva) LIKE LOWER(CONCAT('%', :uva, '%'))) AND " +
            "(:adega IS NULL OR :adega = '' OR LOWER(w.adega) LIKE LOWER(CONCAT('%', :adega, '%'))) AND " +
            "w.status = 'Ativo'")
    Page<Wine> findWinesByFields(@Param("wineName") String wineName,
                                 @Param("pais") String pais,
                                 @Param("uva") String uva,
                                 @Param("adega") String adega,
                                 Pageable pageable);

    @Query("SELECT DISTINCT(w.uva) FROM Wine w WHERE w.status = 'Ativo'")
    List<String> getAllUvas();
}
