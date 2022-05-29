package io.github.gleysongomes.projeto.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.projeto.models.ProjetoModel;
import io.github.gleysongomes.projeto.repositories.ProjetoRepository;
import io.github.gleysongomes.projeto.services.ProjetoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjetoServiceImpl implements ProjetoService {

	private final ProjetoRepository projetoRepository;

	@Override
	public Page<ProjetoModel> listar(Specification<ProjetoModel> projetoSpec, Pageable pageable) {
		log.debug("SERVICE: listar projetos com filtros: {} e paginação: {}", projetoSpec, pageable);
		return projetoRepository.findAll(projetoSpec, pageable);
	}

	@Override
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
		projetoRepository.delete(projetoModel);
	}

}
