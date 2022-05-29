package io.github.gleysongomes.projeto.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.projeto.models.StatusProjetoModel;

public interface StatusProjetoService {

	Optional<StatusProjetoModel> findById(UUID cdStatusProjeto);

	Page<StatusProjetoModel> listar(Specification<StatusProjetoModel> statusProjetoSpec, Pageable pageable);

	void salvar(StatusProjetoModel statusProjetoModel);

	void excluir(StatusProjetoModel statusProjetoModel);

}
