package io.github.gleysongomes.projeto.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.projeto.models.ProjetoModel;

public interface ProjetoService {

	Page<ProjetoModel> listar(Specification<ProjetoModel> projetoSpec, Pageable pageable);

	Optional<ProjetoModel> findById(UUID cdProjeto);

	void salvar(ProjetoModel projetoModel);

	void excluir(ProjetoModel projetoModel);

}
