package com.example.tea_loufu;

import org.springframework.ui.Model;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class TeaLoufuApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeaLoufuApplication.class, args);
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message", "Welcome to Tea Loufu!");
		return "CRUDProduct"; // Trả về file index.html
	}



	@GetMapping("/cart")
	public String cart() {
		return "cart"; // Trả về file cart.html
	}



	@GetMapping("/orders")
	public String orders() {
		return "order"; // Trả về file order.html
	}
}
