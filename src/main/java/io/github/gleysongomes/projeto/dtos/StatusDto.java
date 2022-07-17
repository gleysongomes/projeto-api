package io.github.gleysongomes.projeto.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import io.github.gleysongomes.projeto.enums.GrupoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDto {

	@NotBlank(groups = { StatusView.StatusPost.class, StatusView.StatusPut.class })
	@JsonView({ StatusView.StatusPost.class, StatusView.StatusPut.class })
	private String nome;

	@NotNull(groups = { StatusView.StatusPost.class, StatusView.StatusPut.class })
	@JsonView({ StatusView.StatusPost.class, StatusView.StatusPut.class })
	private Boolean flAtivo;

	@NotNull(groups = { StatusView.StatusPost.class, StatusView.StatusPut.class })
	@JsonView({ StatusView.StatusPost.class, StatusView.StatusPut.class })
	private GrupoStatus grupoStatus;

	public interface StatusView {
		public static interface StatusPost {
		}

		public static interface StatusPut {
		}

	}
}
