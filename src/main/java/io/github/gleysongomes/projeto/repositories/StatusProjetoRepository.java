package io.github.gleysongomes.projeto.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.github.gleysongomes.projeto.models.StatusProjetoModel;

public interface StatusProjetoRepository extends JpaRepository<StatusProjetoModel, UUID>, JpaSpecificationExecutor<StatusProjetoModel> {

}
