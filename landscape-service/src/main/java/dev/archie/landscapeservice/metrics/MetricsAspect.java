package dev.archie.landscapeservice.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class MetricsAspect {

    private final MeterRegistry meterRegistry;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isRestController() {
    }

    @Pointcut("execution(public * *(..))")
    public void isPublicMethod() {
    }

    @Pointcut("isRestController() && isPublicMethod()")
    public void isRestEndpoint() {
    }

    @Around("isRestEndpoint()")
    public Object timeSuccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getStaticPart().getSignature();
        String methodName = signature.getName();
        Timer.Sample sample = Timer.start(meterRegistry);
        String exceptionClass = "none";

        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            exceptionClass = ex.getClass().getSimpleName();
            throw ex;
        } finally {
            try {
                sample.stop(
                        Timer.builder(signature.getDeclaringTypeName() + "." + methodName)
                                .description(methodName)
                                .tags("exception", exceptionClass)
                                .publishPercentileHistogram(true)
                                .publishPercentiles(0.05, 0.5, 0.95)
                                .register(meterRegistry)
                );
            } catch (Exception ignored) {
            }
        }
    }


}
