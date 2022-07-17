package io.github.gleysongomes.projeto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrupoStatus {

	PROJETO("PROJETO", "Projeto"), 
	TAREFA("TAREFA", "Tarefa"), 
	SUBTAREFA("SUBTAREFA", "Subtarefa");

	private String nome;

	private String descricao;
}
