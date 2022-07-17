package io.github.gleysongomes.projeto.specifications;

import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.projeto.models.ProjetoModel;
import io.github.gleysongomes.projeto.models.StatusModel;
import io.github.gleysongomes.projeto.models.SubtarefaModel;
import io.github.gleysongomes.projeto.models.TarefaModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate {

	@And({ 
		@Spec(path = "nome", spec = Like.class),
		@Spec(path = "dtInicio", spec = Equal.class),
		@Spec(path = "dtFim", spec = Equal.class),
		@Spec(path = "dtPrevisaoConclusao", spec = Equal.class),
		@Spec(path = "hrInicio", spec = Like.class),
		@Spec(path = "hrFim", spec = Like.class)
	})
	public interface ProjetoSpec extends Specification<ProjetoModel> {

	}

	@And({ 
		@Spec(path = "nome", spec = Like.class),
		@Spec(path = "flAtivo", spec = Equal.class)
	})
	public interface StatusSpec extends Specification<StatusModel> {

	}

	@And({
		@Spec(path = "nome", spec = Like.class),
		@Spec(path = "descricao", spec = Like.class),
		@Spec(path = "dtCadastro", spec = Equal.class),
		@Spec(path = "dtAtualizacao", spec = Equal.class),
		@Spec(path = "dtInicio", spec = Equal.class),
		@Spec(path = "dtFim", spec = Equal.class),
		@Spec(path = "dtPrevisaoConclusao", spec = Equal.class),
		@Spec(path = "hrInicio", spec = Like.class),
		@Spec(path = "hrFim", spec = Like.class),
		@Spec(path = "prioridade", spec = Equal.class),
		@Spec(path = "tempoGastoEstimado", spec = Equal.class),
		@Spec(path = "tempoGasto", spec = Equal.class),
		@Spec(path = "flAtiva", spec = Equal.class)
	})
	public interface TarefaSpec extends Specification<TarefaModel> {

	}

	@And({
		@Spec(path = "nome", spec = Like.class),
		@Spec(path = "descricao", spec = Like.class),
		@Spec(path = "dtCadastro", spec = Equal.class),
		@Spec(path = "dtAtualizacao", spec = Equal.class),
		@Spec(path = "dtInicio", spec = Equal.class),
		@Spec(path = "dtFim", spec = Equal.class),
		@Spec(path = "dtPrevisaoConclusao", spec = Equal.class),
		@Spec(path = "hrInicio", spec = Like.class),
		@Spec(path = "hrFim", spec = Like.class),
		@Spec(path = "prioridade", spec = Equal.class),
		@Spec(path = "tempoGastoEstimado", spec = Equal.class),
		@Spec(path = "utTempoGastoEstimado", spec = Equal.class),
		@Spec(path = "tempoGasto", spec = Equal.class),
		@Spec(path = "utTempoGasto", spec = Equal.class),
		@Spec(path = "flAtiva", spec = Equal.class)
	})
	public interface SubtarefaSpec extends Specification<SubtarefaModel> {

	}

}
