package com.endava.mmarko.pia.aspects;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logging {
  private static final Logger LOGGER = Logger.getLogger(Logging.class.getName());
  private static final String LOG_LOCATION = "C:\\Users\\Marko.Micovic\\OneDrive - ENDAVA\\Desktop\\logger.log";
  private static FileHandler handler = null;

  @Pointcut("execution(* com.endava.mmarko.pia.services.*.*(..))")
  public void service() {
  }

  @Around("service()")
  public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
    StringBuilder builder = new StringBuilder();

    builder.append("\nFunction: ").append(joinPoint.getSignature().toShortString());

    Object[] arguments = joinPoint.getArgs();
    if (arguments.length > 0) {
      builder.append("\nArguments: ");
      for (Object arg : arguments) {
        builder.append(arg.toString()).append("\t");
      }
    }
    builder.append("\n");

    LOGGER.log(Level.INFO, builder.toString());

    return joinPoint.proceed();
  }

  @PostConstruct
  public void init() {
    try {
      handler = new FileHandler(LOG_LOCATION, true);
      Logger log = Logger.getLogger("");
      handler.setFormatter(new SimpleFormatter());
      log.addHandler(handler);
      log.setLevel(Level.CONFIG);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @PreDestroy
  public void deInit() {
    handler.close();
  }
}
