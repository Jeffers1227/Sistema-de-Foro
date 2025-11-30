package com.example.forum.service;

import com.example.forum.entity.Comment;
import com.example.forum.entity.Forum;
import com.example.forum.entity.User;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.ForumRepository;
import com.example.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForumService {

    @Autowired private ForumRepository forumRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;

    public Page<Forum> getForums(String query, Pageable pageable) {
        if (query != null && !query.isEmpty()) {
            return forumRepository.findByTitleContainingIgnoreCase(query, pageable);
        }
        return forumRepository.findAll(pageable);
    }

    public Optional<Forum> findById(Long id) {
        return forumRepository.findById(id);
    }

    public Forum save(Forum forum) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        forum.setAuthor(user);
        return forumRepository.save(forum);
    }

    // --- NUEVO: INCREMENTAR VISITAS ---
    public void incrementViews(Long forumId) {
        forumRepository.findById(forumId).ifPresent(forum -> {
            forum.setViews(forum.getViews() == null ? 1L : forum.getViews() + 1);
            forumRepository.save(forum);
        });
    }
    // ----------------------------------

    public void update(Long id, Forum forumActualizado) {
        Forum forumExistente = forumRepository.findById(id).orElseThrow();
        forumExistente.setTitle(forumActualizado.getTitle());
        forumExistente.setContent(forumActualizado.getContent());
        forumRepository.save(forumExistente);
    }

    public void delete(Long id) {
        forumRepository.deleteById(id);
    }

    public Comment addComment(Long forumId, String text) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByUsername(username).orElseThrow();
        Forum forum = forumRepository.findById(forumId).orElseThrow();

        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(author);
        comment.setForum(forum);
        
        return commentRepository.save(comment);
    }

    public void validateComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        
        if(comment.getForum().getAuthor().getUsername().equals(currentUsername)) {
            comment.setValidated(true);
            commentRepository.save(comment);
        }
    }
}