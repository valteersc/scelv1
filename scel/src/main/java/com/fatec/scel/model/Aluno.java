package com.fatec.scel.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;


@Entity(name = "Aluno")
public class Aluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NaturalId
	@Column(nullable = false, length = 13)
	@NotEmpty(message="O ra deve ser preenchido") //o atributo nao pode ser nulo e o tamanho deve ser maior que zero
	private String ra;
	
	@Column(nullable = false, length = 100)
	@NotEmpty(message="O nome deve ser preenchido")
	
	private String nome;
	@Column(nullable = false)
	@NotNull(message="email invalido")
    @Size(min = 1, max = 50, message="email deve ter entre 1 e 50 caracteres")
	private String email;
	
	public Aluno() {
	}

	public Aluno( String i, String t, String a) {
		this.ra = i;
		this.nome = t;
		this.email = a;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public String getra() {
		return ra;
	}
	public void setra(String ra) {
		this.ra = ra;
	}
	public String getnome() {
		return nome;
	}
	public void setnome(String nome) {
		this.nome = nome;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
}