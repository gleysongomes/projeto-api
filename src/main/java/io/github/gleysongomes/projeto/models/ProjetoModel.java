package io.github.gleysongomes.projeto.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_projeto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjetoModel extends RepresentationModel<ProjetoModel> implements Serializable {

	private static final long serialVersionUID = -2730566007510693598L;

	@Id
	@Column(name = "cd_projeto")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID cdProjeto;

	@Column(nullable = false, unique = true)
	private String nome;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY HH:mm:ss")
	@Column(name = "dt_cadastro", nullable = false)
	private LocalDateTime dtCadastro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY HH:mm:ss")
	@Column(name = "dt_atualizacao")
	private LocalDateTime dtAtualizacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY")
	@Column(name = "dt_inicio")
	private LocalDate dtInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY")
	@Column(name = "dt_fim")
	private LocalDate dtFim;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY")
	@Column(name = "dt_previsao_conclusao")
	private LocalDate dtPrevisaoConclusao;

	@Column(name = "hr_inicio")
	private String hrInicio;

	@Column(name = "hr_fim")
	private String hrFim;

	@Column(name = "cd_status_projeto", nullable = false)
	private UUID cdStatusProjeto;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cd_status_projeto", insertable = false, updatable = false)
	private StatusProjetoModel status;
}
