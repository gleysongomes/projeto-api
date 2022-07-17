package io.github.gleysongomes.projeto.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.gleysongomes.projeto.enums.Prioridade;
import io.github.gleysongomes.projeto.enums.UnidadeTempo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_subtarefa")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubtarefaModel extends RepresentationModel<SubtarefaModel> implements Serializable {

	private static final long serialVersionUID = 1461762159703562195L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "cd_subtarefa")
	private UUID cdSubtarefa;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(nullable = false, length = 250)
	private String descricao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "dt_cadastro", nullable = false)
	private LocalDateTime dtCadastro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "dt_atualizacao")
	private LocalDateTime dtAtualizacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "dt_inicio")
	private LocalDate dtInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "dt_fim")
	private LocalDate dtFim;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "dt_previsao_conclusao")
	private LocalDate dtPrevisaoConclusao;

	@Column(name = "hr_inicio", length = 5)
	private String hrInicio;

	@Column(name = "hr_fim", length = 5)
	private String hrFim;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;

	@Column(name = "tempo_gasto_estimado")
	private Integer tempoGastoEstimado;

	@Column(name = "ut_tempo_gasto_estimado")
	@Enumerated(EnumType.STRING)
	private UnidadeTempo utTempoGastoEstimado;

	@Column(name = "tempo_gasto")
	private Integer tempoGasto;

	@Column(name = "ut_tempo_gasto")
	@Enumerated(EnumType.STRING)
	private UnidadeTempo utTempoGasto;

	@Column(name = "fl_ativa", nullable = false)
	private Boolean flAtiva;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private TarefaModel tarefa;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private StatusModel status;
}
