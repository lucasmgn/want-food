package com.wantfood.aplication.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.wantfood.aplication.core.validation.ValidacaoException;
import com.wantfood.aplication.domain.constants.Messages;
import com.wantfood.aplication.domain.exception.BusinessException;
import com.wantfood.aplication.domain.exception.EntityInUseException;
import com.wantfood.aplication.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Classe global, para todos os controladores
 * extends ResponseEntityExceptionHandler classe que indentifica erros internos
 * */
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    //Criado para acessar a getMessage colocando o fieldError e o locale
    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
    		HttpStatus status, WebRequest request) {

        return handleValidationInternal(ex, ex, headers, status, request);
    }

    @ExceptionHandler({ValidacaoException.class})
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleValidationInternal(e, e.getBindingResult(), headers, status, request);
    }

    /*
     * Importante colocar o printStackTrace (pelo menos por enquanto, que não tem
     *  o logging) para mostrar a stacktrace no console
     *  Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
     *  para você durante, especialmente na fase de desenvolvimento
     * */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception e, WebRequest request) {

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.trace(Arrays.toString(e.getStackTrace()));
        var problem = createProblemBuilder(status, ProblemType.SYSTEM_ERROR, Messages.USER_MESSAGE).build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {

        var detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                e.getRequestURL());
        var problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, detail).build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exception, HttpHeaders headers,
    		HttpStatus status, WebRequest request) {
        if (exception instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) exception, headers, status, request);
        }

        return super.handleTypeMismatch(exception, headers, status, request);
    }

	/*
	 * Pegando a raiz do problema para ser passado como o erro pro usuário,
	 * afim de especificar melhor a Message de erro
	 * Pegando a causa raiz
	 * Fazendo a verificação, se a causa raiz(rootCause) for uma InvalidFormatException então
	 * chamará o metodo que irá tratar de forma especifica
	 * */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {

        var rootCause = ExceptionUtils.getRootCause(e);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);

        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        var problem = createProblemBuilder(status, ProblemType.ERRO_MESSAGE, Messages.ERROR_BODY).build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    /*
     * Criando metodo para controlar os consoles de erro
     * @ExceptionHandler Aceita mais de um argumento, pode ser usado {StateNotFoundException.class},
     * {stateeXEMPLOException.class}
     * */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntityNotFoundException exception,
    		WebRequest request) {

        var status = HttpStatus.NOT_FOUND;
        var problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, exception.getMessage()).build();

        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleNegocio(BusinessException exception, WebRequest request) {

        var status = HttpStatus.BAD_REQUEST;
        var detail = exception.getMessage();
        var problem = createProblemBuilder(status, ProblemType.BUSINESS_ERROR, detail).build();

        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntityInUseException exception, WebRequest request) {

        var status = HttpStatus.CONFLICT;
        var detail = exception.getMessage();
        var problem = createProblemBuilder(status, ProblemType.ENTITY_IN_USE, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

	/*
	 * Se o body for nulo será aplicado as mensagens padrão do spring,
	 * caso não seja a Message criada de exception será apresentada
	 */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase()) //descreve o status que retorna na repsosta
                    .status(status.value())
                    .userMessage(Messages.USER_MESSAGE) //Message para o usuário
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body) //descreve o status que retorna na repsosta
                    .status(status.value())
                    .userMessage(Messages.USER_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(exception, body, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (exception == null || exception.getRequiredType() == null) {
            var problem = createProblemBuilder(status, ProblemType.INVALID_PARAMETER, null).build();

            return handleExceptionInternal(exception, problem, headers, status, request);
        } else {
            exception.getRequiredType();
        }

        var detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                exception.getName(), exception.getValue(), exception.getRequiredType().getSimpleName());

        var problem = createProblemBuilder(status, ProblemType.INVALID_PARAMETER, detail).build();

        return handleExceptionInternal(exception, problem, headers, status, request);
    }

    /*
     * Método a ser chamado se a causa raiz for um IgnoredPropertyException ou UnrecongnizePropertyException
     *
     * IgnoredPropertyException e UnrecongnizePropertyException que extends PropertyBindingException
     * Criando variável que irá pegar o Path da exception
     * e irá formatar o name dela, delimitando por um '.'
     * */
    private ResponseEntity<Object> handlePropertyBinding(
            PropertyBindingException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var path = joinPath(exception.getPath());

        var detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        var problem = createProblemBuilder(status, ProblemType.ERRO_MESSAGE, detail)
                .userMessage(Messages.USER_MESSAGE)
                .build();

        return handleExceptionInternal(exception, problem, headers, status, request);
    }

    /*
     * Método a ser chamado se a causa raiz for um InvalidFormatException
     *
     *  InvalidFormatException
     * Criando variável que irá pegar o Path da exception
     * e irá formatar o name dela, delimitando por um '.'
     * */
    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        var path = joinPath(exception.getPath());

        var detail = String.format("A propriedade '%s' recebeu o valor '%s',"
                        + " que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, exception.getValue(), exception.getTargetType().getSimpleName());

        var problem = createProblemBuilder(status, ProblemType.ERRO_MESSAGE, detail)
                .userMessage(Messages.USER_MESSAGE)
                .build();

        return handleExceptionInternal(exception, problem, headers, status, request);
    }


    /*
     * BindingResult Instancia que armazena as constraints de violações,
     * tem acesso em quais fields foram violadas
     *
     * Transformando o bindingResult em uma lista de problemFields,
     * mudando de getFieldErrors para AllErrors, com o objective de pegar não apenas
     * os erros dos atributos, mas também os erros da classes
     *
     * recebe o valor para passar na userMessage,
     * objetivo é ler o file message.properties, o parametro mudou de fieldError para
     * objectError pq agora estou tratando de erros do objeto também
     * */
    private ResponseEntity<Object> handleValidationInternal(Exception e, BindingResult bindingResult,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        var detail = Messages.INVALID_FIELDS;

        var problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {

                    var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    var name = objectError.getObjectName().toUpperCase();

                    //Verificando se o objectError é um fieldError
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)//Pegando o name da prorpiedade que foi violada
                            .userMessage(message)// .getDefaultMessage(), pegando a Message padrão
                            .build();
                }).toList();

        var problem = createProblemBuilder(status, ProblemType.INVALID_DATA, Messages.INVALID_FIELDS)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    //Metodo para ser utilizado para create um build de problem, facilitando a manutenção
    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
    		ProblemType problemType, String detail) {

        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(Messages.USER_MESSAGE);
    }

    //Método joinPath irá concatenar os names das propriedades, separando-as por "."
    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
