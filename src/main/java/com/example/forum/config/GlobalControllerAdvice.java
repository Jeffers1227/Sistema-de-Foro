package com.example.forum.config;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("prettyTime")
    public PrettyTime prettyTime() {
        // Configuramos para que salga en español ("hace 5 minutos")
        return new PrettyTime(new Locale("es"));
    }
}