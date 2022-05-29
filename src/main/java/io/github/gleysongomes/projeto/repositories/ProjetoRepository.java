package io.github.gleysongomes.projeto.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.github.gleysongomes.projeto.models.ProjetoModel;

public interface ProjetoRepository extends JpaRepository<ProjetoModel, UUID>, JpaSpecificationExecutor<ProjetoModel> {

}
