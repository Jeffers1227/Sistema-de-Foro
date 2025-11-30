package com.example.forum.service;

import com.example.forum.entity.Comment;
import com.example.forum.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired private CommentRepository commentRepository;

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public void update(Long id, String newText) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setText(newText);
        // Al editar, quitamos la validación para que el dueño lo revise de nuevo (opcional)
        comment.setValidated(false); 
        commentRepository.save(comment);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}