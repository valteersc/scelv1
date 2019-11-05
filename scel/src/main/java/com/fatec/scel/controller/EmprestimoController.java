package com.fatec.scel.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.scel.model.AlunoRepository;
import com.fatec.scel.model.Emprestimo;
import com.fatec.scel.model.EmprestimoRepository;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;
import com.fatec.scel.model.Servico;


@RestController
@RequestMapping(path = "/emprestimos")

	public class EmprestimoController 
	{
		@Autowired 
		private EmprestimoRepository emprestimoRepository;
		@Autowired 
		private LivroRepository livroRepository;
		@Autowired 
		private AlunoRepository alunoRepository;
		@Autowired 
		private Servico servico;
		/**
		 * quando o usuario digita localhost:8080/emprestimo/cadastrar
		 *
		 * @param emprestimo
		 * @return o html /cadastrarEmprestimo
		*/
		@GetMapping("/cadastrar")
		
		public ModelAndView cadastrarEmprestimo(Emprestimo emprestimo) 
		{
			ModelAndView mv = new ModelAndView("cadastrarEmprestimo");
			mv.addObject("emprestimo", emprestimo);
			return mv;
		}
		
		@GetMapping("/consulta")
		public ModelAndView listar() 
		{
			ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
			modelAndView.addObject("emprestimos", emprestimoRepository.findAll());
			return modelAndView;
		}
		
		@GetMapping("/delete/{id}")
		public ModelAndView delete(@PathVariable("id") Long id) {
			emprestimoRepository.deleteById(id);
			ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
			modelAndView.addObject("emprestimos", emprestimoRepository.findAll());
			return modelAndView;
		}
		
		@PostMapping("/save")
		public ModelAndView save(@Valid Emprestimo emprestimo, BindingResult result) {
			ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
			if (result.hasErrors()) {
				return new ModelAndView("cadastrarEmprestimo");
			}
			try {
				Emprestimo jaExiste = null;
				jaExiste = emprestimoRepository.findByIsbn(emprestimo.getIsbn());
				if (jaExiste == null) {
					emprestimoRepository.save(emprestimo);
					modelAndView = new ModelAndView("consultarEmprestimo");
					modelAndView.addObject("emprestimos", emprestimoRepository.findAll());
					return modelAndView;
				} else {
					return new ModelAndView("cadastrarEmprestimo");
				}
			} catch (Exception e) {
				System.out.println("erro ===> " + e.getMessage());
				return modelAndView; // captura o erro mas nao informa o motivo.
			}
		}

} 
