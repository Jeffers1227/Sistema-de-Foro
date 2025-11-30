package com.example.forum.controller;

import com.example.forum.entity.Comment;
import com.example.forum.service.CommentService;
import com.example.forum.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired private ForumService forumService;
    @Autowired private CommentService commentService;

    // Agregar comentario
    @PostMapping("/{forumId}")
    public String addComment(@PathVariable Long forumId, @RequestParam String text) {
        forumService.addComment(forumId, text);
        return "redirect:/forums/" + forumId;
    }

    // Validar comentario (Solo dueño del foro)
    @PostMapping("/validate/{commentId}")
    public String validateComment(@PathVariable Long commentId, @RequestParam Long forumId) {
        forumService.validateComment(commentId);
        return "redirect:/forums/" + forumId;
    }

    // --- NUEVO: EDITAR COMENTARIO ---
    
    @GetMapping("/edit/{id}")
    public String editCommentForm(@PathVariable Long id, Model model, Authentication auth) {
        Comment comment = commentService.findById(id).orElseThrow();
        
        // Seguridad: Solo el autor del comentario puede editarlo
        if (!comment.getAuthor().getUsername().equals(auth.getName())) {
            return "redirect:/forums/" + comment.getForum().getId() + "?error=NoPermiso";
        }

        model.addAttribute("comment", comment);
        return "comments/edit"; // Necesitaremos crear esta plantilla
    }

    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, @RequestParam String text, Authentication auth) {
        Comment comment = commentService.findById(id).orElseThrow();
        
        // Seguridad: Solo el autor del comentario puede actualizarlo
        if (!comment.getAuthor().getUsername().equals(auth.getName())) {
            return "redirect:/forums/" + comment.getForum().getId();
        }

        commentService.update(id, text);
        return "redirect:/forums/" + comment.getForum().getId();
    }

    // --- NUEVO: ELIMINAR COMENTARIO ---

    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, Authentication auth) {
        Comment comment = commentService.findById(id).orElseThrow();
        Long forumId = comment.getForum().getId();
        String currentUsername = auth.getName();

        // LOGICA DE SEGURIDAD QUE PEDISTE:
        // 1. Es el autor del comentario
        boolean isCommentAuthor = comment.getAuthor().getUsername().equals(currentUsername);
        // 2. Es el dueño del foro (Moderación)
        boolean isForumOwner = comment.getForum().getAuthor().getUsername().equals(currentUsername);
        // 3. Es Admin
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (isCommentAuthor || isForumOwner || isAdmin) {
            commentService.delete(id);
        }

        return "redirect:/forums/" + forumId;
    }
}