package io.github.gleysongomes.projeto.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.projeto.models.StatusProjetoModel;
import io.github.gleysongomes.projeto.repositories.StatusProjetoRepository;
import io.github.gleysongomes.projeto.services.StatusProjetoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StatusProjetoImpl implements StatusProjetoService {

	private final StatusProjetoRepository statusProjetoRepository;

	@Override
	public Optional<StatusProjetoModel> findById(UUID cdStatusProjeto) {
		log.debug("SERVICE: listar status do projeto de código: {}", cdStatusProjeto);
		return statusProjetoRepository.findById(cdStatusProjeto);
	}

	@Override
	public Page<StatusProjetoModel> listar(Specification<StatusProjetoModel> statusProjetoSpec, Pageable pageable) {
		log.debug("SERVICE: buscar status do projeto com filtros: {} e paginação", statusProjetoSpec, pageable);
		return statusProjetoRepository.findAll(statusProjetoSpec, pageable);
	}

	@Override
	public void salvar(StatusProjetoModel statusProjetoModel) {
		log.debug("SERVICE: salvar status do projeto: {}", statusProjetoModel);
		statusProjetoRepository.save(statusProjetoModel);
	}

	@Override
	public void excluir(StatusProjetoModel statusProjetoModel) {
		log.debug("SERVICE: excluir status do projeto: {}", statusProjetoModel);
		statusProjetoRepository.delete(statusProjetoModel);
	}

}
