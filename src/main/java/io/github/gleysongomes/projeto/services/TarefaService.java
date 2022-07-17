package io.github.gleysongomes.projeto.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.projeto.models.TarefaModel;

public interface TarefaService {

	Page<TarefaModel> listar(Specification<TarefaModel> tarefaSpec, Pageable pageable);

	Optional<TarefaModel> buscar(UUID cdTarefa);

	void salvar(TarefaModel tarefaModel);

	void excluir(TarefaModel tarefaModel);

	Optional<TarefaModel> findByNomeAndProjetoCdProjeto(String nome, UUID cdProjeto);

	Optional<TarefaModel> findByNomeAndProjetoCdProjetoAndCdTarefaNot(String nome, UUID cdProjeto, UUID cdTarefa);
}
