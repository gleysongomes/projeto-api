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

import io.github.gleysongomes.projeto.dtos.ProjetoDto;
import io.github.gleysongomes.projeto.models.ProjetoModel;
import io.github.gleysongomes.projeto.models.StatusModel;
import io.github.gleysongomes.projeto.services.ProjetoService;
import io.github.gleysongomes.projeto.services.StatusService;
import io.github.gleysongomes.projeto.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/projetos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjetoController {

	private final ProjetoService projetoService;

	private final StatusService statusService;

	@GetMapping
	public ResponseEntity<Page<ProjetoModel>> listar(SpecificationTemplate.ProjetoSpec projetoSpec,
			@PageableDefault(page = 0, size = 10, sort = "dtCadastro", direction = Sort.Direction.DESC) Pageable pageable) {
		log.debug("CONTROLLER: listar projetos com paginação: {}", pageable);

		Page<ProjetoModel> projetoModelPage = projetoService.listar(projetoSpec, pageable);

		if (!projetoModelPage.isEmpty()) {
			for (ProjetoModel projetoModel : projetoModelPage.toList()) {
				projetoModel.add(
						linkTo(methodOn(ProjetoController.class).buscar(projetoModel.getCdProjeto())).withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(projetoModelPage);
	}

	@PostMapping
	public ResponseEntity<Object> adicionar(
			@RequestBody @Validated(ProjetoDto.ProjetoView.ProjetoPost.class) @JsonView(ProjetoDto.ProjetoView.ProjetoPost.class) ProjetoDto projetoDto) {
		log.debug("CONTROLLER: salvar projeto: {}", projetoDto);
		Optional<StatusModel> statusModelOptional = statusService.findById(projetoDto.getCdStatus());
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		} else {
			var projetoModel = new ProjetoModel();
			BeanUtils.copyProperties(projetoDto, projetoModel);
			projetoModel.setDtCadastro(LocalDateTime.now(ZoneId.of("UTC")));
			projetoModel.setStatus(statusModelOptional.get());
			projetoService.salvar(projetoModel);
			return ResponseEntity.status(HttpStatus.CREATED).body(projetoModel);
		}
	}

	@PutMapping("/{cdProjeto}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "cdProjeto") UUID cdProjeto,
			@RequestBody @Validated(ProjetoDto.ProjetoView.ProjetoPut.class) @JsonView(ProjetoDto.ProjetoView.ProjetoPut.class) ProjetoDto projetoDto) {
		log.debug("CONTROLLER: atualizar projeto: {}", projetoDto);
		Optional<ProjetoModel> projetoModelOptional = projetoService.findById(cdProjeto);
		if (!projetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
		}
		Optional<StatusModel> statusModelOptional = statusService.findById(projetoDto.getCdStatus());
		if (!statusModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado.");
		}
		var projetoModel = projetoModelOptional.get();
		BeanUtils.copyProperties(projetoDto, projetoModel);
		projetoModel.setDtAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
		projetoModel.setStatus(statusModelOptional.get());
		projetoService.salvar(projetoModel);
		return ResponseEntity.status(HttpStatus.OK).body(projetoModel);
	}

	@GetMapping("/{cdProjeto}")
	public ResponseEntity<Object> buscar(@PathVariable(value = "cdProjeto") UUID cdProjeto) {
		log.debug("CONTROLLER: buscar projeto de código: {}", cdProjeto);
		Optional<ProjetoModel> projetoModelOptional = projetoService.findById(cdProjeto);
		if (!projetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(projetoModelOptional.get());
		}
	}

	@DeleteMapping("/{cdProjeto}")
	public ResponseEntity<Object> excluir(@PathVariable(value = "cdProjeto") UUID cdProjeto) {
		log.debug("CONTROLLER: excluir projeto de código: {}", cdProjeto);
		Optional<ProjetoModel> projetoModelOptional = projetoService.findById(cdProjeto);
		if (!projetoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
		} else {
			projetoService.excluir(projetoModelOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Projeto excluído com sucesso.");
		}
	}

}
