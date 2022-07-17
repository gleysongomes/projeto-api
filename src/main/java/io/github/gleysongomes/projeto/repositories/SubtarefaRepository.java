package io.github.gleysongomes.projeto.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.gleysongomes.projeto.models.SubtarefaModel;

public interface SubtarefaRepository
		extends JpaRepository<SubtarefaModel, UUID>, JpaSpecificationExecutor<SubtarefaModel> {

	@Query(value = "select * from tb_subtarefa where tarefa_cd_tarefa = :cdTarefa", nativeQuery = true)
	List<SubtarefaModel> findSubtarefasByCdTarefa(@Param("cdTarefa") UUID cdTarefa);

}
