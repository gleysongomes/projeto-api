package io.github.gleysongomes.projeto.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.projeto.models.StatusModel;
import io.github.gleysongomes.projeto.repositories.StatusRepository;
import io.github.gleysongomes.projeto.services.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StatusProjetoImpl implements StatusService {

	private final StatusRepository statusRepository;

	@Override
	@Transactional(readOnly = true)
	public Optional<StatusModel> findById(UUID cdStatus) {
		log.debug("SERVICE: listar status de código: {}", cdStatus);
		return statusRepository.findById(cdStatus);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<StatusModel> listar(Specification<StatusModel> statusSpec, Pageable pageable) {
		log.debug("SERVICE: buscar status com filtros: {} e paginação", statusSpec, pageable);
		return statusRepository.findAll(statusSpec, pageable);
	}

	@Override
	public void salvar(StatusModel statusModel) {
		log.debug("SERVICE: salvar status: {}", statusModel);
		statusRepository.save(statusModel);
	}

	@Override
	public void excluir(StatusModel statusModel) {
		log.debug("SERVICE: excluir status: {}", statusModel);
		statusRepository.delete(statusModel);
	}

}
