package io.github.gleysongomes.projeto.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusProjetoDto {

	@NotBlank(groups = { StatusProjetoView.StatusProjetoPost.class, StatusProjetoView.StatusProjetoPut.class })
	@JsonView({ StatusProjetoView.StatusProjetoPost.class, StatusProjetoView.StatusProjetoPut.class })
	private String nome;

	@NotNull(groups = { StatusProjetoView.StatusProjetoPost.class, StatusProjetoView.StatusProjetoPut.class })
	@JsonView({ StatusProjetoView.StatusProjetoPost.class, StatusProjetoView.StatusProjetoPut.class })
	private Boolean flAtivo;

	public interface StatusProjetoView {
		public static interface StatusProjetoPost {
		}

		public static interface StatusProjetoPut {
		}

	}
}
