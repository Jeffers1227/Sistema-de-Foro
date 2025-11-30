package com.example.forum.controller;

import com.example.forum.entity.Forum;
import com.example.forum.entity.User;
import com.example.forum.repository.ForumRepository;
import com.example.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    @Autowired private UserRepository userRepository;
    @Autowired private ForumRepository forumRepository; // Inyectamos el repo de foros

    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username, Model model) {
        // 1. Buscamos al usuario
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2. Buscamos sus últimos 5 foros
        Page<Forum> userForums = forumRepository.findByAuthorOrderByCreatedAtDesc(user, PageRequest.of(0, 5));

        // 3. Enviamos todo a la vista
        model.addAttribute("userProfile", user);
        model.addAttribute("forums", userForums.getContent()); // Enviamos la lista de foros
        
        return "profile";
    }
}