package io.github.gleysongomes.projeto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Prioridade {

	MUITO_BAIXA("MUITO_BAIXA", "Muito Baixa"), 
	BAIXA("BAIXA", "Baixa"), 
	MEDIA("MEDIA", "Média"), 
	ALTA("ALTA", "Alta"),
	MUITO_ALTA("MUITO_ALTA", "Muito Alta");

	private String nome;

	private String descricao;
}
