package com.farmacia.msinventario.controller;

import com.farmacia.msinventario.repository.InventarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final InventarioRepository repository;

    public HomeController(InventarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/inventario")
    public String inventario(Model model) {

        model.addAttribute(
                "inventarios",
                repository.findAll()
        );

        return "inventario";
    }
}