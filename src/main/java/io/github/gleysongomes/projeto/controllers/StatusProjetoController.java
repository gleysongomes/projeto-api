package io.github.gleysongomes.projeto.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.github.gleysongomes.projeto.dtos.StatusProjetoDto;
import io.github.gleysongomes.projeto.models.StatusProjetoModel;
import io.github.gleysongomes.projeto.services.StatusProjetoService;
import io.github.gleysongomes.projeto.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/status-projeto", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusProjetoController {

	private final StatusProjetoService statusProjetoService;

	private final Logger log = LoggerFactory.getLogger(StatusProjetoController.class);

	@GetMapping
	public ResponseEntity<Page<StatusProjetoModel>> listar(SpecificationTemplate.StatusProjetoSpec statusProjetoSpec,
			@PageableDefault(page = 0, size = 10, sort = "dtCadastro", direction = Sort.Direction.DESC) Pageable pageable) {
		log.debug("CONTROLLER: listar status do projeto com paginação: {}", pageable);

		Page<StatusProjetoModel> statusProjetoModelPage = statusProjetoService.listar(statusProjetoSpec, pageable);

		if (!statusProjetoModelPage.isEmpty()) {
			for (StatusProjetoModel statusProjetoModel : statusProjetoModelPage.toList()) {
				statusProjetoModel.add(
						linkTo(methodOn(StatusProjetoController.class).buscar(statusProjetoModel.getCdStatusProjeto()))
								.withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(statusProjetoModelPage);
	}

	@PostMapping
	public ResponseEntity<StatusProjetoModel> adicionar(
			@RequestBody @Validated(StatusProjetoDto.StatusProjetoView.StatusProjetoPost.class) @JsonView(StatusProjetoDto.StatusProjetoView.StatusProjetoPost.class) StatusProjetoDto statusProjetoDto) {
		log.debug("CONTROLLER: salvar status do projeto: {}", statusProjetoDto);
		var statusProjetoModel = new StatusProjetoModel();
		BeanUtils.copyProperties(statusProjetoDto, statusProjetoModel);
		statusProjetoModel.setDtCadastro(LocalDateTime.now(ZoneId.of("UTC")));
		statusProjetoService.salvar(statusProjetoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(statusProjetoModel);
	}

	@PutMapping("/{cdStatusProjeto}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "cdStatusProjeto") UUID cdStatusProjeto,
			@RequestBody @Validated(StatusProjetoDto.StatusProjetoView.StatusProjetoPut.class) @JsonView(StatusProjetoDto.StatusProjetoView.StatusProjetoPut.class) StatusProjetoDto statusProjetoDto) {
		log.debug("CONTROLLER: atualizar status do projeto: {}", statusProjetoDto);
		Optional<StatusProjetoModel> statusProjetoModelOptional = statusProjetoService.findById(cdStatusProjeto);
		if (!statusProjetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status do projeto não encontrado.");
		} else {
			var statusProjetoModel = statusProjetoModelOptional.get();
			BeanUtils.copyProperties(statusProjetoDto, statusProjetoModel);
			statusProjetoModel.setDtAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
			statusProjetoService.salvar(statusProjetoModel);
			return ResponseEntity.status(HttpStatus.OK).body(statusProjetoModel);
		}
	}

	@GetMapping("/{cdStatusProjeto}")
	public ResponseEntity<Object> buscar(@PathVariable(value = "cdStatusProjeto") UUID cdStatusProjeto) {
		log.debug("CONTROLLER: buscar status do projeto de código: {}", cdStatusProjeto);
		Optional<StatusProjetoModel> statusProjetoModelOptional = statusProjetoService.findById(cdStatusProjeto);
		if (!statusProjetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status projeto não encontrado.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(statusProjetoModelOptional.get());
		}
	}

	@DeleteMapping("/{cdStatusProjeto}")
	public ResponseEntity<Object> excluir(@PathVariable(value = "cdStatusProjeto") UUID cdStatusProjeto) {
		log.debug("CONTROLLER: excluir status do projeto de código: {}", cdStatusProjeto);
		Optional<StatusProjetoModel> statusProjetoModelOptional = statusProjetoService.findById(cdStatusProjeto);
		if (!statusProjetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status do projeto não encontrado.");
		} else {
			statusProjetoService.excluir(statusProjetoModelOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Status do projeto excluído com sucesso.");
		}
	}
}
