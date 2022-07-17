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
public class SubtarefaDto {

	@NotBlank(groups = { SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private String nome;

	@NotBlank(groups = { SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private String descricao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private LocalDate dtInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private LocalDate dtFim;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private LocalDate dtPrevisaoConclusao;

	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private String hrInicio;

	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private String hrFim;

	@NotNull(groups = { SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private Prioridade prioridade;

	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private Integer tempoGastoEstimado;

	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private UnidadeTempo utTempoGastoEstimado;

	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private Integer tempoGasto;

	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private UnidadeTempo utTempoGasto;

	@NotNull(groups = { SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private Boolean flAtiva;

	@NotNull(groups = { SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private UUID cdTarefa;

	@NotNull(groups = { SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	@JsonView({ SubtarefaView.SubtarefaPost.class, SubtarefaView.SuntarefaPut.class })
	private UUID cdStatus;

	public interface SubtarefaView {
		public static interface SubtarefaPost {
		}

		public static interface SuntarefaPut {
		}
	}

}
