package com.wantfood.aplication.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.exception.EntidadeNaoEncontradaException;
import com.wantfood.aplication.domain.exception.NegocioException;

/*
 * Classe global, para todos os controladores
 * extends ResponseEntityExceptionHandler classe que indentifica erros internos
 * */
@ControllerAdvice 
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {	
		/*
		 * Pegando a raiz do problema para ser passado como o erro pro usuário,
		 * afim de especificar melhor a mensagem de erro 
		 * Pegando a causa raiz
		 * */
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		
		/*
		 *Fazendo a verificação, se a causa raiz(rootCause) for uma InvalidFormatException então
		 * chamará o metodo que irá tratar de forma especifica
		 * */
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers,
					status, request);
		}
		
		ProblemType problemType = ProblemType.ERRO_NA_MENSAGEM;
		String detail = "O corpo está requisição está inválido. Possivel erro na sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, headers, status, request);
	}
	
	/*
	 * Método a ser chamado se a causa raiz for um InvalidFormatException
	 * */
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		/*
		 * Criando variável que irá pegar o Path da exception
		 *  e irá formatar o nome dela, delimitando por um '.'
		 * */
		String path = e.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.ERRO_NA_MENSAGEM;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s',"
				+ " que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, e.getValue(), e.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, headers, status, request);
	}

	/*
	 * Criando metodo para controlar os consoles de erro
	 * @ExceptionHandler Aceita mais de um argumento, pode ser usado {EstadoNaoEncontradoException.class},
	 * {EstadoeXEMPLOException.class}
	 * */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
			WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(),status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {	
		/*
		 * Se o corpo for nulo será aplicado as mensagens padrão do spring,
		 * caso não seja a mensagem criada de exception será apresentada
		 */ 
		if(body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase()) //descreve o status que retorna na repsosta
					.status(status.value())
					.build();
		}else if(body instanceof String) {
			body = Problem.builder()
					.title((String) body) //descreve o status que retorna na repsosta
					.status(status.value())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	//Metodo para ser utilizado para criar um build de problem, facilitando a manutenção
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
			ProblemType problemType, String detail){
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
}
