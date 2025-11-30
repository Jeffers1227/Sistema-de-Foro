package com.example.forum.repository;

import com.example.forum.entity.Forum;
import com.example.forum.entity.User; // IMPORTANTE: Importar User
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    // Buscar todos paginados
    Page<Forum> findAll(Pageable pageable);
    
    // Buscar por título
    Page<Forum> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // NUEVO: Buscar foros de un usuario específico ordenados por fecha
    Page<Forum> findByAuthorOrderByCreatedAtDesc(User author, Pageable pageable);
}