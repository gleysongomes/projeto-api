package io.github.gleysongomes.projeto.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.projeto.models.ProjetoModel;
import io.github.gleysongomes.projeto.models.SubtarefaModel;
import io.github.gleysongomes.projeto.models.TarefaModel;
import io.github.gleysongomes.projeto.repositories.ProjetoRepository;
import io.github.gleysongomes.projeto.repositories.SubtarefaRepository;
import io.github.gleysongomes.projeto.repositories.TarefaRepository;
import io.github.gleysongomes.projeto.services.ProjetoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjetoServiceImpl implements ProjetoService {

	private final ProjetoRepository projetoRepository;

	private final TarefaRepository tarefaRepository;

	private final SubtarefaRepository subtarefaRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<ProjetoModel> listar(Specification<ProjetoModel> projetoSpec, Pageable pageable) {
		log.debug("SERVICE: listar projetos com filtros: {} e paginação: {}", projetoSpec, pageable);
		return projetoRepository.findAll(projetoSpec, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ProjetoModel> findById(UUID cdProjeto) {
		log.debug("SERVICE: buscar projeto de código: {}", cdProjeto);
		return projetoRepository.findById(cdProjeto);
	}

	@Override
	public void salvar(ProjetoModel projetoModel) {
		log.debug("SERVICE: salvar projeto: {}", projetoModel);
		projetoRepository.save(projetoModel);
	}

	@Override
	public void excluir(ProjetoModel projetoModel) {
		log.debug("SERVICE: excluir projeto: {}", projetoModel);
		List<TarefaModel> tarefas = tarefaRepository.findTarefasByCdProjeto(projetoModel.getCdProjeto());
		if (tarefas != null && !tarefas.isEmpty()) {
			for (TarefaModel tarefa : tarefas) {
				List<SubtarefaModel> subtarefas = subtarefaRepository.findSubtarefasByCdTarefa(tarefa.getCdTarefa());
				subtarefaRepository.deleteAll(subtarefas);
			}
			tarefaRepository.deleteAll(tarefas);
		}
		projetoRepository.delete(projetoModel);
	}

}
