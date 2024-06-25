package com.api.wiveService.WineService.repository;

import com.api.wiveService.WineService.domain.comments.bean.Comments;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {


    List<Comments> findByWineId(Long id);

    @Query("SELECT c FROM Comments c WHERE c.wine.id = :id AND c.status = 'Ativo'")
    List<Comments> findByWineIdAndStatus(@Param("id") Long id);

    @Query("SELECT c FROM Comments c WHERE c.status = 'Ativo'")
    Page<Comments> findByStatus(String status, Pageable pageable);

    @Query("SELECT c FROM Comments c WHERE c.wine.id = :id AND c.status = :status")
    Page<Comments> findByStatusAndWineId(@Param("id") Long id, @Param("status") String status, Pageable pageable);
}
