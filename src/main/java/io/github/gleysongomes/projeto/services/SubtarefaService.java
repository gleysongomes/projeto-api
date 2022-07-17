package io.github.gleysongomes.projeto.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.projeto.models.SubtarefaModel;

public interface SubtarefaService {

	Page<SubtarefaModel> listar(Specification<SubtarefaModel> subtarefaSpec, Pageable pageable);

	Optional<SubtarefaModel> buscar(UUID cdSubtarefa);

	void salvar(SubtarefaModel subtarefaModel);

	void excluir(SubtarefaModel subtarefaModel);
}
