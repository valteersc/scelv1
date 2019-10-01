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

import com.fatec.scel.model.Aluno;
import com.fatec.scel.model.AlunoRepository;

@RestController
@RequestMapping(path = "/Alunos")
public class AlunoController {
//insert into Aluno values ('1', 'Pressman','aaaa', 'engenharia')
	@Autowired
	private AlunoRepository repository;

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ConsultarAlunos");
		modelAndView.addObject("Alunos", repository.findAll());
		return modelAndView;
	}

	/**
	 * quando o usuario digita localhost:8080/api/add
	 * 
	 * @param Aluno
	 * @return o html /CadastraAluno
	 */
	@GetMapping("/cadastrar")
	public ModelAndView cadastraAluno(Aluno Aluno) {

		ModelAndView mv = new ModelAndView("CadastrarAluno");
		mv.addObject("Aluno", Aluno);

		return mv;
	}

	@GetMapping("/edit/{ra}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView mostraFormAdd(@PathVariable("ra") String ra) {
		ModelAndView modelAndView = new ModelAndView("AtualizaAluno");

		modelAndView.addObject("Aluno", repository.findByra(ra)); // o repositorio e injetado no controller

		return modelAndView; // addObject adiciona objetos para view

	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {

		repository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("ConsultarAlunos");
		modelAndView.addObject("Alunos", repository.findAll());
		return modelAndView;

	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Aluno Aluno, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("ConsultarAlunos");
		if (result.hasErrors()) {
			return new ModelAndView("CadastrarAluno");
		}
		try {
			Aluno jaExiste=null;
			jaExiste = repository.findByra(Aluno.getra());
			if (jaExiste == null) {
				repository.save(Aluno);
				modelAndView = new ModelAndView("ConsultarAlunos");
				modelAndView.addObject("Alunos", repository.findAll());
				return modelAndView;
			} else {
				return new ModelAndView("CadastrarAluno");
			}
		} catch (Exception e) {
			System.out.println("erro ===> " +e.getMessage());
			return modelAndView; // captura o erro mas nao informa o motivo.
		}
	}

	@PostMapping("/update/{id}")
	public ModelAndView atualiza(@PathVariable("id") Long id, @Valid Aluno Aluno, BindingResult result) {
	
		if (result.hasErrors()) {
			Aluno.setId(id);
			return new ModelAndView("AtualizaAluno");
		}
		Aluno umAluno = repository.findById(id).get();
		umAluno.setemail(Aluno.getemail());
		umAluno.setra(Aluno.getra());
		umAluno.setnome(Aluno.getnome());
		repository.save(umAluno);
		ModelAndView modelAndView = new ModelAndView("ConsultarAlunos");
		modelAndView.addObject("Alunos", repository.findAll());
		return modelAndView;
	}
}
