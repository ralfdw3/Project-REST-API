package com.api.webvote.v1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client", schema = "webvote")
public class Associate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String cpf;
	
	public Associate() {
		super();
	}

	public Associate(Long id, String name, String cpf) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

}
