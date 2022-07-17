package io.github.gleysongomes.projeto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnidadeTempo {

	MINUTOS("MINUTOS", "Minuto(s)"), 
	HORAS("HORAS", "Hora(s)"), 
	DIAS("DIAS", "Dia(s)"), 
	SEMANAS("SEMANAS", "Semana(s)"),
	MESES("MESES", "Mese(s)"), 
	ANOS("ANOS", "Ano(s)");

	private String nome;

	private String descricao;
}
