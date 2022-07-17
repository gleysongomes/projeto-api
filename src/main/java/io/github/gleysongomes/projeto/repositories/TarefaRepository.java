package io.github.gleysongomes.projeto.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.gleysongomes.projeto.models.TarefaModel;

public interface TarefaRepository extends JpaRepository<TarefaModel, UUID>, JpaSpecificationExecutor<TarefaModel> {

	@Query(value = "select * from tb_tarefa where projeto_cd_projeto = :cdProjeto", nativeQuery = true)
	List<TarefaModel> findTarefasByCdProjeto(@Param("cdProjeto") UUID cdProjeto);

	Optional<TarefaModel> findByNomeAndProjetoCdProjeto(String nome, UUID cdProjeto);

	Optional<TarefaModel> findByNomeAndProjetoCdProjetoAndCdTarefaNot(String nome, UUID cdProjeto, UUID cdTarefa);

}
