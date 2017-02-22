package sml.manager.api.aspect;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import sml.manager.api.aspect.modelo.Parametro;
import sml.manager.api.aspect.modelo.Requisicao;
import sml.manager.api.aspect.service.TesteService;

@Aspect
@Component
public class ApectManagerAPI {
	
	private TesteService testeService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	private Requisicao requisicao;
	private Parametro parametro;


	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restController() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
	}

	@Pointcut("execution(public * *(..))")
	protected void loggingPublicOperation() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void loggingAllOperation() {
	}
	
	@Before("restController() && allMethod() && args(..,request)")
	public void logBefore(JoinPoint joinPoint, HttpServletRequest request) throws ParseException, JsonProcessingException {
		log.info("Método Acessado :  " + joinPoint.getSignature().getName());
		log.info("Nome da Classe :  " + joinPoint.getSignature().getDeclaringTypeName());
		//log.info("Arguments [{}]", joinPoint.getArgs()[0]);
		log.info("Argumentos : " + Arrays.toString(joinPoint.getArgs()));
		log.info("Classe Alvo : " + joinPoint.getTarget().getClass().getName());

		if (null != request) {
			log.info("Início Header da Requisição ");
			log.info("Tipo de Requisição : " + request.getMethod());
			log.info("IP : " + request.getRemoteAddr());
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				log.info("Nome do Header: " + headerName + " Valor do Header : " + headerValue);
			}
			log.info("Path de Requisição :" + request.getServletPath());
			log.info("Fim Header da Requisição ");
		}
		
		requisicao = new Requisicao();
		parametro = new Parametro();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format1.format(cal.getTime());
		cal.setTime(format1.parse(formatted));
		requisicao.setData(cal);
		requisicao.setIpOrigem(request.getRemoteAddr());
		requisicao.setTipo(request.getMethod());
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(joinPoint.getArgs()[0]);
		parametro.setEntrada(json.toString());
		parametro.setMetodoInvocado(joinPoint.getSignature().getName());
		parametro.setClasseInvocada(joinPoint.getTarget().getClass().getName());
		
	}
	
	@AfterReturning(pointcut = "restController() && allMethod()", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {
		String returnValue = this.getValue(result);
		log.info("Retorno do Método : " + returnValue);
		parametro.setSaida(returnValue);
		parametro.setRequisicao(requisicao);
		requisicao.setParametro(parametro);
		testeService.persistirAluno(requisicao);
	}
	
	@AfterThrowing(pointcut = "restController() && allMethod()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		log.error("Exceção Lançada em " + joinPoint.getSignature().getName() + " ()");
		log.error("Causa : " + exception.getCause());
	}
	
	@Around("restController() && allMethod()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		
		long start = System.currentTimeMillis();
		try {
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			Object result = joinPoint.proceed();
			long elapsedTime = System.currentTimeMillis() - start;
			log.info("Método " + className + "." + methodName + " ()" + " Executado Em : "
					+ elapsedTime + " ms");
			requisicao.setTempoExecucao(elapsedTime);
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Argumento Ilegal " + Arrays.toString(joinPoint.getArgs()) + " Em : "
					+ joinPoint.getSignature().getName() + "()");
			throw e;
		}
	}	private String getValue(Object result) {
		String returnValue = null;
		if (null != result) {
			if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
				returnValue = ReflectionToStringBuilder.toString(result);
			} else {
				returnValue = result.toString();
			}
		}
		return returnValue;
	}
	
	@Autowired(required=true)
	@Qualifier(value="testeService")
	public void setTesteService(TesteService testeService) {
		this.testeService = testeService;
	}

}
