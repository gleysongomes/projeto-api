package io.github.gleysongomes.projeto.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.projeto.models.SubtarefaModel;
import io.github.gleysongomes.projeto.models.TarefaModel;
import io.github.gleysongomes.projeto.repositories.SubtarefaRepository;
import io.github.gleysongomes.projeto.repositories.TarefaRepository;
import io.github.gleysongomes.projeto.services.TarefaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TarefaServiceImpl implements TarefaService {

	private final TarefaRepository tarefaRepository;

	private final SubtarefaRepository subtarefaRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<TarefaModel> listar(Specification<TarefaModel> tarefaSpec, Pageable pageable) {
		log.debug("SERVICE: listar tarefas com filtros: {} e paginação: {}", tarefaSpec, pageable);
		return tarefaRepository.findAll(tarefaSpec, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TarefaModel> buscar(UUID cdTarefa) {
		log.debug("SERVICE: buscar tarefa de código: {}", cdTarefa);
		return tarefaRepository.findById(cdTarefa);
	}

	@Override
	public void salvar(TarefaModel tarefaModel) {
		log.debug("SERVICE: salvar tarefa: {}", tarefaModel);
		tarefaRepository.save(tarefaModel);
	}

	@Override
	public void excluir(TarefaModel tarefaModel) {
		log.debug("SERVICE: excluir tarefa: {}", tarefaModel);
		List<SubtarefaModel> subtarefas = subtarefaRepository.findSubtarefasByCdTarefa(tarefaModel.getCdTarefa());
		if (subtarefas != null && !subtarefas.isEmpty()) {
			subtarefaRepository.deleteAll(subtarefas);
		}
		tarefaRepository.delete(tarefaModel);
	}

	@Override
	public Optional<TarefaModel> findByNomeAndProjetoCdProjeto(String nome, UUID cdProjeto) {
		log.debug("SERVICE: buscar tarefa de nome: {} e código do projeto: {}", nome, cdProjeto);
		return tarefaRepository.findByNomeAndProjetoCdProjeto(nome, cdProjeto);
	}

	@Override
	public Optional<TarefaModel> findByNomeAndProjetoCdProjetoAndCdTarefaNot(String nome, UUID cdProjeto,
			UUID cdTarefa) {
		log.debug(
				"SERVICE: buscar tarefa de nome: {} e código do projeto: {}, que não seja a tarefa que está sendo atualizada.");
		return tarefaRepository.findByNomeAndProjetoCdProjetoAndCdTarefaNot(nome, cdProjeto, cdTarefa);
	}

}
