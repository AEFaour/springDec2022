package biblio.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggerInterceptor {
@Before("execution(* biblio.service.*.*(..))") //pointcut
	public void logBeforeInfo(JoinPoint joinPoint) {
		
	Object[] args = joinPoint.getArgs();
		String name = joinPoint.getSignature().getName();
		StringBuffer sb = new StringBuffer(name 
						+ " appelé avec en paramètres : ");
		sb.append(Arrays.toString(args));
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		logger.info(sb.toString());
		
	}

}

