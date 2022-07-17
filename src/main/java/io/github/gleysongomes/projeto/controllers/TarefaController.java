package io.github.gleysongomes.projeto.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.github.gleysongomes.projeto.dtos.TarefaDto;
import io.github.gleysongomes.projeto.models.ProjetoModel;
import io.github.gleysongomes.projeto.models.StatusModel;
import io.github.gleysongomes.projeto.models.TarefaModel;
import io.github.gleysongomes.projeto.services.ProjetoService;
import io.github.gleysongomes.projeto.services.StatusService;
import io.github.gleysongomes.projeto.services.TarefaService;
import io.github.gleysongomes.projeto.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/tarefas", produces = MediaType.APPLICATION_JSON_VALUE)
public class TarefaController {

	private final ProjetoService projetoService;

	private final StatusService statusService;

	private final TarefaService tarefaService;

	@GetMapping
	public ResponseEntity<Page<TarefaModel>> listar(SpecificationTemplate.TarefaSpec tarefaSpec,
			@PageableDefault(page = 0, size = 10, sort = "dtCadastro", direction = Sort.Direction.DESC) Pageable pageable) {
		log.debug("CONTROLLER: listar tarefas com paginação: {}", pageable);

		Page<TarefaModel> tarefaModelPage = tarefaService.listar(tarefaSpec, pageable);

		if (!tarefaModelPage.isEmpty()) {
			for (TarefaModel tarefaModel : tarefaModelPage.toList()) {
				tarefaModel
						.add(linkTo(methodOn(TarefaController.class).buscar(tarefaModel.getCdTarefa())).withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(tarefaModelPage);
	}

	@PostMapping
	public ResponseEntity<Object> adicionar(
			@RequestBody @Validated(TarefaDto.TarefaView.TarefaPost.class) @JsonView(TarefaDto.TarefaView.TarefaPost.class) TarefaDto tarefaDto) {
		log.debug("CONTROLLER: salvar tarefa: {}", tarefaDto);
		Optional<StatusModel> statusModelOptional = statusService.findById(tarefaDto.getCdStatus());
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		}
		Optional<ProjetoModel> projetoModelOptional = projetoService.findById(tarefaDto.getCdProjeto());
		if (!projetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
		}
		Optional<TarefaModel> tarefaModelOptional = tarefaService.findByNomeAndProjetoCdProjeto(tarefaDto.getNome(),
				tarefaDto.getCdProjeto());
		if (tarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Esse nome de tarefa já existe para esse projeto.");
		}
		var tarefaModel = new TarefaModel();
		BeanUtils.copyProperties(tarefaDto, tarefaModel);
		tarefaModel.setDtCadastro(LocalDateTime.now(ZoneId.of("UTC")));
		tarefaModel.setStatus(statusModelOptional.get());
		tarefaModel.setProjeto(projetoModelOptional.get());
		tarefaService.salvar(tarefaModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(tarefaModel);
	}

	@PutMapping("/{cdTarefa}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "cdTarefa") UUID cdTarefa,
			@RequestBody @Validated(TarefaDto.TarefaView.TarefaPut.class) @JsonView(TarefaDto.TarefaView.TarefaPost.class) TarefaDto tarefaDto) {
		log.debug("CONTROLLER: atualizar tarefa: {}", tarefaDto);
		Optional<TarefaModel> tarefaModelOptional = tarefaService.buscar(cdTarefa);
		if (!tarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
		}
		Optional<StatusModel> statusModelOptional = statusService.findById(tarefaDto.getCdStatus());
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		}
		Optional<ProjetoModel> projetoModelOptional = projetoService.findById(tarefaDto.getCdProjeto());
		if (!projetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
		}
		Optional<TarefaModel> tarefaModelDuplicidadeOptional = tarefaService
				.findByNomeAndProjetoCdProjetoAndCdTarefaNot(tarefaDto.getNome(), tarefaDto.getCdProjeto(), cdTarefa);
		if (tarefaModelDuplicidadeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Esse nome de tarefa já existe para esse projeto.");
		}
		var tarefaModel = tarefaModelOptional.get();
		BeanUtils.copyProperties(tarefaDto, tarefaModel);
		tarefaModel.setDtAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
		tarefaModel.setStatus(statusModelOptional.get());
		tarefaModel.setProjeto(projetoModelOptional.get());
		tarefaService.salvar(tarefaModel);
		return ResponseEntity.status(HttpStatus.OK).body(tarefaModel);
	}

	@GetMapping("/{cdTarefa}")
	public ResponseEntity<Object> buscar(@PathVariable(value = "cdTarefa") UUID cdTarefa) {
		log.debug("CONTROLLER: buscar tarefa de código: {}", cdTarefa);
		Optional<TarefaModel> tarefaModelOptional = tarefaService.buscar(cdTarefa);
		if (!tarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(tarefaModelOptional.get());
		}
	}

	@DeleteMapping("/{cdTarefa}")
	public ResponseEntity<Object> excluir(@PathVariable(value = "cdTarefa") UUID cdTarefa) {
		log.debug("CONTROLLER: excluir tarefa de código: {}", cdTarefa);
		Optional<TarefaModel> tarefaModelOptional = tarefaService.buscar(cdTarefa);
		if (!tarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
		} else {
			tarefaService.excluir(tarefaModelOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Tarefa excluída com sucesso.");
		}
	}

}
