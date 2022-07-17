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

import io.github.gleysongomes.projeto.dtos.SubtarefaDto;
import io.github.gleysongomes.projeto.models.StatusModel;
import io.github.gleysongomes.projeto.models.SubtarefaModel;
import io.github.gleysongomes.projeto.models.TarefaModel;
import io.github.gleysongomes.projeto.services.StatusService;
import io.github.gleysongomes.projeto.services.SubtarefaService;
import io.github.gleysongomes.projeto.services.TarefaService;
import io.github.gleysongomes.projeto.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping(path = "/subtarefas", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubtarefaController {

	private final StatusService statusService;

	private final TarefaService tarefaService;

	private final SubtarefaService subtarefaService;

	@GetMapping
	public ResponseEntity<Page<SubtarefaModel>> listar(SpecificationTemplate.SubtarefaSpec subtarefaSpec,
			@PageableDefault(page = 0, size = 10, sort = "dtCadastro", direction = Sort.Direction.DESC) Pageable pageable) {
		log.debug("CONTROLLER: listar subtarefas com paginação: {}", pageable);

		Page<SubtarefaModel> subtarefaModelPage = subtarefaService.listar(subtarefaSpec, pageable);

		if (!subtarefaModelPage.isEmpty()) {
			for (SubtarefaModel subtarefaModel : subtarefaModelPage.toList()) {
				subtarefaModel.add(linkTo(methodOn(SubtarefaController.class).buscar(subtarefaModel.getCdSubtarefa()))
						.withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(subtarefaModelPage);
	}

	@PostMapping
	public ResponseEntity<Object> adicionar(
			@RequestBody @Validated(SubtarefaDto.SubtarefaView.SubtarefaPost.class) @JsonView(SubtarefaDto.SubtarefaView.SubtarefaPost.class) SubtarefaDto subtarefaDto) {
		log.debug("CONTROLLER: salvar subtarefa: {}", subtarefaDto);

		Optional<StatusModel> statusModelOptional = statusService.findById(subtarefaDto.getCdStatus());
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		}
		Optional<TarefaModel> tarefaModelOptional = tarefaService.buscar(subtarefaDto.getCdTarefa());
		if (!tarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
		}
		Optional<SubtarefaModel> subtarefaOptional = subtarefaService
				.findByNomeAndTarefaCdTarefa(subtarefaDto.getNome(), subtarefaDto.getCdTarefa());
		if (subtarefaOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Esse nome de subtarefa já existe para essa tarefa.");
		}
		var subtarefaModel = new SubtarefaModel();
		BeanUtils.copyProperties(subtarefaDto, subtarefaModel);
		subtarefaModel.setDtCadastro(LocalDateTime.now(ZoneId.of("UTC")));
		subtarefaModel.setStatus(statusModelOptional.get());
		subtarefaModel.setTarefa(tarefaModelOptional.get());
		subtarefaService.salvar(subtarefaModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(subtarefaModel);
	}

	@PutMapping("/{cdSubtarefa}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "cdSubtarefa") UUID cdSubtarefa,
			@RequestBody @Validated(SubtarefaDto.SubtarefaView.SuntarefaPut.class) @JsonView(SubtarefaDto.SubtarefaView.SuntarefaPut.class) SubtarefaDto subtarefaDto) {
		log.debug("CONTROLLER: atualizar subtarefa: {}", subtarefaDto);
		Optional<SubtarefaModel> subtarefaModelOptional = subtarefaService.buscar(cdSubtarefa);
		if (!subtarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subtarefa não encontrada.");
		}
		Optional<TarefaModel> tarefaModelOptional = tarefaService.buscar(subtarefaDto.getCdTarefa());
		if (!tarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
		}
		Optional<StatusModel> statusModelOptional = statusService.findById(subtarefaDto.getCdStatus());
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		}
		Optional<SubtarefaModel> subtarefaModelDuplicidadeOptional = subtarefaService
				.findByNomeAndTarefaCdTarefaAndCdSubtarefaNot(subtarefaDto.getNome(), subtarefaDto.getCdTarefa(),
						cdSubtarefa);
		if (subtarefaModelDuplicidadeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Esse nome de subtarefa já existe para essa tarefa.");
		}
		var subtarefaModel = subtarefaModelOptional.get();
		BeanUtils.copyProperties(subtarefaDto, subtarefaModel);
		subtarefaModel.setDtAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
		subtarefaModel.setStatus(statusModelOptional.get());
		subtarefaModel.setTarefa(tarefaModelOptional.get());
		subtarefaService.salvar(subtarefaModel);
		return ResponseEntity.status(HttpStatus.OK).body(subtarefaModel);
	}

	@GetMapping("/{cdSubtarefa}")
	public ResponseEntity<Object> buscar(@PathVariable(value = "cdSubtarefa") UUID cdSubtarefa) {
		log.debug("CONTROLLER: buscar subtarefa de código: {}", cdSubtarefa);
		Optional<SubtarefaModel> subtarefaModelOptional = subtarefaService.buscar(cdSubtarefa);
		if (!subtarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subtarefa não encontrada.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(subtarefaModelOptional.get());
		}
	}

	@DeleteMapping("/{cdSubtarefa}")
	public ResponseEntity<Object> excluir(@PathVariable(value = "cdSubtarefa") UUID cdSubtarefa) {
		log.debug("CONTROLLER: excluir tarefa de código: {}", cdSubtarefa);
		Optional<SubtarefaModel> subtarefaModelOptional = subtarefaService.buscar(cdSubtarefa);
		if (!subtarefaModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subtarefa não encontrada.");
		} else {
			subtarefaService.excluir(subtarefaModelOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Subtarefa excluída com sucesso.");
		}
	}

}
