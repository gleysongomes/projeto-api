package io.github.gleysongomes.projeto.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.projeto.models.SubtarefaModel;
import io.github.gleysongomes.projeto.repositories.SubtarefaRepository;
import io.github.gleysongomes.projeto.services.SubtarefaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubtarefaServiceImpl implements SubtarefaService {

	private final SubtarefaRepository subtarefaRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<SubtarefaModel> listar(Specification<SubtarefaModel> subtarefaSpec, Pageable pageable) {
		log.debug("SERVICE: listar subtarefas com filtros: {} e paginação: {}", subtarefaSpec, pageable);
		return subtarefaRepository.findAll(subtarefaSpec, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SubtarefaModel> buscar(UUID cdSubtarefa) {
		log.debug("SERVICE: buscar subtarefa de código: {}", cdSubtarefa);
		return subtarefaRepository.findById(cdSubtarefa);
	}

	@Override
	public void salvar(SubtarefaModel subtarefaModel) {
		log.debug("SERVICE: salvar subtarefa: {}", subtarefaModel);
		subtarefaRepository.save(subtarefaModel);
	}

	@Override
	public void excluir(SubtarefaModel subtarefaModel) {
		subtarefaRepository.delete(subtarefaModel);
	}

}
