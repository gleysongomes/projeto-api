package io.github.gleysongomes.projeto.dtos;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjetoDto {

	@NotBlank(groups = { ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private String nome;

	@NotBlank(groups = { ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private String descricao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private LocalDate dtInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private LocalDate dtFim;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private LocalDate dtPrevisaoConclusao;

	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private String hrInicio;

	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private String hrFim;

	@NotNull(groups = { ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	@JsonView({ ProjetoView.ProjetoPost.class, ProjetoView.ProjetoPut.class })
	private UUID cdStatus;

	public interface ProjetoView {
		public static interface ProjetoPost {
		}

		public static interface ProjetoPut {
		}
	}
}
