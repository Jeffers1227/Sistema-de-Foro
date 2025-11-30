package com.example.forum.controller;

import com.example.forum.entity.Forum;
import com.example.forum.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForumController {

    @Autowired private ForumService forumService;

    @GetMapping("/")
    public String home(Model model, 
                       @RequestParam(required = false) String query,
                       @RequestParam(defaultValue = "0") int page) {
        
        PageRequest pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
        Page<Forum> forumPage = forumService.getForums(query, pageable);

        model.addAttribute("forums", forumPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", forumPage.getTotalPages());
        model.addAttribute("query", query);
        
        return "index";
    }

    @GetMapping("/forums/new")
    public String newForumForm(Model model) {
        model.addAttribute("forum", new Forum());
        return "forums/form";
    }

    @PostMapping("/forums")
    public String createForum(@Valid @ModelAttribute("forum") Forum forum, BindingResult result) {
        if(result.hasErrors()) {
            return "forums/form";
        }
        forumService.save(forum);
        return "redirect:/";
    }

    @GetMapping("/forums/{id}")
    public String viewForum(@PathVariable Long id, Model model, Authentication authentication) {
        
        // --- NUEVO: Incrementar visitas antes de mostrar ---
        forumService.incrementViews(id);
        // --------------------------------------------------

        Forum forum = forumService.findById(id).orElseThrow(() -> new IllegalArgumentException("Foro inválido"));
        model.addAttribute("forum", forum);
        
        boolean isOwnerOrAdmin = false;
        boolean isOwner = false;

        if (authentication != null) {
            String username = authentication.getName();
            isOwner = forum.getAuthor().getUsername().equals(username);
            boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            isOwnerOrAdmin = isOwner || isAdmin;
        }
        
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isOwnerOrAdmin", isOwnerOrAdmin);
        return "forums/detail";
    }

    @GetMapping("/forums/edit/{id}")
    public String editForumForm(@PathVariable Long id, Model model, Authentication auth) {
        Forum forum = forumService.findById(id).orElseThrow();
        if (!isAuthorized(auth, forum)) {
            return "redirect:/forums/" + id;
        }
        model.addAttribute("forum", forum);
        return "forums/form";
    }

    @PostMapping("/forums/update/{id}")
    public String updateForum(@PathVariable Long id, @Valid @ModelAttribute("forum") Forum forum, BindingResult result, Authentication auth) {
        if(result.hasErrors()) {
            return "forums/form";
        }
        Forum existingForum = forumService.findById(id).orElseThrow();
        if (!isAuthorized(auth, existingForum)) {
            return "redirect:/";
        }
        forumService.update(id, forum);
        return "redirect:/forums/" + id;
    }

    @PostMapping("/forums/delete/{id}")
    public String deleteForum(@PathVariable Long id, Authentication auth) {
        Forum forum = forumService.findById(id).orElseThrow();
        if (!isAuthorized(auth, forum)) {
            return "redirect:/forums/" + id;
        }
        forumService.delete(id);
        return "redirect:/?deleted";
    }

    private boolean isAuthorized(Authentication auth, Forum forum) {
        if (auth == null) return false;
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isOwner = forum.getAuthor().getUsername().equals(username);
        return isOwner || isAdmin;
    }
}