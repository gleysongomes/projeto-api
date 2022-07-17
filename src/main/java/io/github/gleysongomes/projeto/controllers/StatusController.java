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

import io.github.gleysongomes.projeto.dtos.StatusDto;
import io.github.gleysongomes.projeto.models.StatusModel;
import io.github.gleysongomes.projeto.services.StatusService;
import io.github.gleysongomes.projeto.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {

	private final StatusService statusService;

	@GetMapping
	public ResponseEntity<Page<StatusModel>> listar(SpecificationTemplate.StatusSpec statusSpec,
			@PageableDefault(page = 0, size = 10, sort = "dtCadastro", direction = Sort.Direction.DESC) Pageable pageable) {
		log.debug("CONTROLLER: listar status com paginação: {}", pageable);

		Page<StatusModel> statusModelPage = statusService.listar(statusSpec, pageable);

		if (!statusModelPage.isEmpty()) {
			for (StatusModel statusModel : statusModelPage.toList()) {
				statusModel
						.add(linkTo(methodOn(StatusController.class).buscar(statusModel.getCdStatus())).withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(statusModelPage);
	}

	@PostMapping
	public ResponseEntity<StatusModel> adicionar(
			@RequestBody @Validated(StatusDto.StatusView.StatusPost.class) @JsonView(StatusDto.StatusView.StatusPost.class) StatusDto statusDto) {
		log.debug("CONTROLLER: salvar status: {}", statusDto);
		var statusModel = new StatusModel();
		BeanUtils.copyProperties(statusDto, statusModel);
		statusModel.setDtCadastro(LocalDateTime.now(ZoneId.of("UTC")));
		statusService.salvar(statusModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(statusModel);
	}

	@PutMapping("/{cdStatus}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "cdStatus") UUID cdStatus,
			@RequestBody @Validated(StatusDto.StatusView.StatusPut.class) @JsonView(StatusDto.StatusView.StatusPut.class) StatusDto statusDto) {
		log.debug("CONTROLLER: atualizar status: {}", statusDto);
		Optional<StatusModel> statusModelOptional = statusService.findById(cdStatus);
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		} else {
			var statusModel = statusModelOptional.get();
			BeanUtils.copyProperties(statusDto, statusModel);
			statusModel.setDtAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
			statusService.salvar(statusModel);
			return ResponseEntity.status(HttpStatus.OK).body(statusModel);
		}
	}

	@GetMapping("/{cdStatus}")
	public ResponseEntity<Object> buscar(@PathVariable(value = "cdStatus") UUID cdStatus) {
		log.debug("CONTROLLER: buscar status de código: {}", cdStatus);
		Optional<StatusModel> statusModelOptional = statusService.findById(cdStatus);
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status projeto não encontrado.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(statusModelOptional.get());
		}
	}

	@DeleteMapping("/{cdStatus}")
	public ResponseEntity<Object> excluir(@PathVariable(value = "cdStatus") UUID cdStatus) {
		log.debug("CONTROLLER: excluir status de código: {}", cdStatus);
		Optional<StatusModel> statusModelOptional = statusService.findById(cdStatus);
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		} else {
			statusService.excluir(statusModelOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Status excluído com sucesso.");
		}
	}

}
