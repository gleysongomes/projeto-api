package io.github.gleysongomes.projeto.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.projeto.models.StatusModel;

public interface StatusService {

	Optional<StatusModel> findById(UUID cdStatus);

	Page<StatusModel> listar(Specification<StatusModel> statusSpec, Pageable pageable);

	void salvar(StatusModel statusModel);

	void excluir(StatusModel statusModel);

}
