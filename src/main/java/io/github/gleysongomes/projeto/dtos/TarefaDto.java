package io.github.gleysongomes.projeto.dtos;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import io.github.gleysongomes.projeto.enums.Prioridade;
import io.github.gleysongomes.projeto.enums.UnidadeTempo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarefaDto {

	@NotBlank(groups = { TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private String nome;

	@NotBlank(groups = { TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private String descricao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private LocalDate dtInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private LocalDate dtFim;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private LocalDate dtPrevisaoConclusao;

	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private String hrInicio;

	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private String hrFim;

	@NotNull(groups = { TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private Prioridade prioridade;

	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private Integer tempoGastoEstimado;

	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private UnidadeTempo utTempoGastoEstimado;

	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private Integer tempoGasto;

	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private UnidadeTempo utTempoGasto;

	@NotNull(groups = { TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private Boolean flAtiva;

	@NotNull(groups = { TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private UUID cdStatus;

	@NotNull(groups = { TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	@JsonView({ TarefaView.TarefaPost.class, TarefaView.TarefaPut.class })
	private UUID cdProjeto;

	public interface TarefaView {
		public static interface TarefaPost {
		}

		public static interface TarefaPut {
		}
	}

}
