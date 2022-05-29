package io.github.gleysongomes.projeto.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_status_projeto")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StatusProjetoModel extends RepresentationModel<StatusProjetoModel> implements Serializable {

	private static final long serialVersionUID = -6367592214987515494L;

	@Id
	@Column(name = "cd_status_projeto")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID cdStatusProjeto;

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
}
