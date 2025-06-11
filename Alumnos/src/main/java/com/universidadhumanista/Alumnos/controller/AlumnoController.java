package com.universidadhumanista.Alumnos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/estudiante")
public class AlumnoController {

	@GetMapping("/saludo")
	public String helloWorld() {
		return "Hola Estudiante";
	}

}
