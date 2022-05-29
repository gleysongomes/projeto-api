package io.github.gleysongomes.projeto.specifications;

import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.projeto.models.ProjetoModel;
import io.github.gleysongomes.projeto.models.StatusProjetoModel;
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
	public interface StatusProjetoSpec extends Specification<StatusProjetoModel> {

	}
}
