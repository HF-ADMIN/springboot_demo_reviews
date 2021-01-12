package com.example.demo_reviews.aop;

import com.example.demo_reviews.dto.ReviewsDTO;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.opentracing.Span;
import io.opentracing.Tracer;

@Aspect
@Component
public class ReviewsSpanAspect {

    private final static String[] headers_to_propagate = {
        // All applications should propagate x-request-id. 
        "x-request-id",

        // Lightstep tracing header. Propagate this if you use lightstep tracing in Istio 
        "x-ot-span-context",

        // Datadog tracing header. Propagate these headers if you use Datadog tracing.
        "x-datadog-trace-id", "x-datadog-parent-id", "x-datadog-sampling-priority",

        // W3C Trace Context. Compatible with OpenCensusAgent and Stackdriver Istio configurations.
        "traceparent", "tracestate",

        // Cloud trace context. Compatible with OpenCensusAgent and Stackdriver Istio configurations.
        "x-cloud-trace-context",

        // Grpc binary trace context.
        "grpc-trace-bin",

        // b3 trace headers. Compatible with Zipkin, OpenCensusAgent, and Stackdriver Istio configurations. 
        "x-b3-traceid", "x-b3-spanid", "x-b3-parentspanid", "x-b3-sampled", "x-b3-flags",

        // Application-specific headers to forward.
        "end-user", "user-agent"};
    
    @Autowired
    private Tracer tracer;

    Logger logger = LoggerFactory.getLogger(ReviewsSpanAspect.class);

    // @Autowired
    // public SpanAspect(Tracer tracer){
    //     this.tracer = tracer;
    // }

    /**
     * @methodName  spanLogging
     * @return      Object
     * @description DemoOpentracingApplication Class에서 Bean으로 등록한 tracer를 사용하여 Span을 생성하고 종료합니다.
     *              com.example.demo_productpage.controller.EmployeeController.*(..) 패턴으로 Around AOP를 적용합니다.
     */    
    @Around("execution(* com.example.demo_reviews.controller.ReviewsController.*(..))")
    public Object spanLogging(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("[                             spanLogging Start                                ]");

        Span parentSpan = tracer.scopeManager().activeSpan();
        Span AOPSpan = tracer.buildSpan(pjp.getSignature().getName()).asChildOf(parentSpan).start();
        AOPSpan.log("                                                AOPSpan span log");
        Object result = pjp.proceed();
        AOPSpan.finish();
        
        logger.info("[                             AOPSpan End                                ]");
        return result;
    }

    /**
     * @methodName  makeHttpHeaders
     * @return      org.springframework.http.HttpHeaders
     * @description headers_to_propagate Array에 명시된 Headers 정보를 가지고 Jaeger Trace Propagation을 위해서 HttpHeaders를 Exchange합니다.
     */
    public HttpHeaders makeHttpHeaders(HttpHeaders httpHeaders) {
        logger.info("[                             makeHttpHeaders                                ]");
        final HttpHeaders headers = new HttpHeaders();
        for (String header : headers_to_propagate) {
            if (httpHeaders.containsKey(header)) {
                logger.info("==========================  [ AOP:makeHttpHeaders ] KEY : " + header + ", VALUE : "
                        + httpHeaders.get(header).get(0));
                headers.add(header, httpHeaders.get(header).get(0));
            }
        }

        return headers;
    }

    /**
     * @methodName  headerExchangeGet
     * @return      Object
     * @description ProceedingJoinPoint에서 Args로 넘어온 HttpHeaders를 가져와서 makeHttpHeaders Method를 사용하여 Http Header 정보를 Exchange한다.
     *              com.example.demo_reviews.controller.ReviewsController.get*(..) 패턴으로 Around AOP를 적용합니다.
     */    
    @Around("execution(* com.example.demo_reviews.controller.ReviewsController.get*(..))")
    public Object headerExchangeGet(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("[                             headerExchangeGet Start                                ]");
        HttpHeaders headers = null;
        Object result = null;
        String prodCode = null;

        // getMehtod의 Type : all / single
        for(Object obj : pjp.getArgs()) {
            logger.info("==========================  [ headerExchangeGet : PJP Args ] " + obj.toString());
            if(obj instanceof HttpHeaders) {
                logger.info("==========================  [ headerExchangeGet : PJP HttpHeaders ] ");
                headers = makeHttpHeaders((HttpHeaders) obj);
            }

            if(obj instanceof String) prodCode = (String) obj;
        }
        
        // Span span = tracer.buildSpan(pjp.getSignature().getName()).start();
        if("getReviewsInfo".equals(pjp.getSignature().getName())) {
            logger.info("==========================  [ headerExchangeGet : PJP Method Name ] getReviewsInfo");
            Object[]  args = {headers, prodCode};
            result = pjp.proceed(args);
        }
        // span.finish();

        logger.info("[                             headerExchangeGet End                               ]");
        ResponseEntity<Object> entity = (ResponseEntity<Object>) result;
        headers = makeHttpHeaders(entity.getHeaders());
        return ResponseEntity.ok().headers(headers).body(entity.getBody());
    }


    /**
     * @methodName  headerExchangePost
     * @return      Object
     * @description ProceedingJoinPoint에서 Args로 넘어온 HttpHeaders를 가져와서 makeHttpHeaders Method를 사용하여 Http Header 정보를 Exchange한다.
     *              com.example.demo_reviews.controller.ReviewsController.post(..) 패턴으로 Around AOP를 적용합니다.
     */    
    @Around("execution(* com.example.demo_reviews.controller.ReviewsController.post(..))")
    public Object headerExchangePost(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("[                             headerExchangePost Start                                ]");
        HttpHeaders headers = null;
        ReviewsDTO.Request request = null;

        for(Object obj : pjp.getArgs()) {
            if(obj instanceof HttpHeaders) {
                logger.info("==========================  [ headerExchangePost : PJP HttpHeaders ] ");
                headers = makeHttpHeaders((HttpHeaders) obj);
            }

            if(obj instanceof ReviewsDTO.Request) {
                request = (ReviewsDTO.Request) obj;
            }
        }

        // Span span = tracer.buildSpan(pjp.getSignature().getName()).start();
        Object[] args = {headers, request};
        Object result = pjp.proceed(args);
        // span.finish();

        logger.info("[                             headerExchangePost End                               ]");
        ResponseEntity<Object> entity = (ResponseEntity<Object>) result;
        headers = makeHttpHeaders(entity.getHeaders());
        return ResponseEntity.ok().headers(headers).body(entity.getBody());
    }

}