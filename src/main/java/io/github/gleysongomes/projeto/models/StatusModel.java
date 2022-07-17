package io.github.gleysongomes.projeto.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.gleysongomes.projeto.enums.GrupoStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_status", uniqueConstraints = {
		@UniqueConstraint(name = "uk_nome_grupo_status", columnNames = { "nome", "grupo_status" }) })
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StatusModel extends RepresentationModel<StatusModel> implements Serializable {

	private static final long serialVersionUID = -6367592214987515494L;

	@Id
	@Column(name = "cd_status")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID cdStatus;

	@Column(nullable = false, length = 100)
	private String nome;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "dt_cadastro", nullable = false)
	private LocalDateTime dtCadastro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "dt_atualizacao")
	private LocalDateTime dtAtualizacao;

	@Column(name = "fl_ativo", nullable = false)
	private Boolean flAtivo;

	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Fetch(FetchMode.SUBSELECT)
	private Set<ProjetoModel> projetos;

	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Fetch(FetchMode.SUBSELECT)
	private Set<TarefaModel> tarefas;

	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Fetch(FetchMode.SUBSELECT)
	private Set<SubtarefaModel> subtarefas;

	@Column(name = "grupo_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private GrupoStatus grupoStatus;
}
