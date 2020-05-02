package com.ascending.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@WebFilter(filterName = "logFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class LogFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final List<String> excludedWords = Arrays.asList("newpassword", "confirmpassword", "passwd", "password");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("I am in logger filter processing");
        long startTime =  System.nanoTime();
        HttpServletRequest req = (HttpServletRequest) request;
        String logInfo = logInfo(req);
        chain.doFilter(request, response);
        logger.info(logInfo.replace("responseTime", String.valueOf(System.nanoTime() - startTime)));
    }

    private boolean isIgnoreWord(String word, List<String> excludedWords){
        for (String excludedWord : excludedWords){
            if (word.toLowerCase().contains(excludedWord)) return true;
        }
        return false;
    }

    private String logInfo(HttpServletRequest req){
        String formData = null;
        String httpMethod = req.getMethod();
        LocalDateTime startDateTime = LocalDateTime.now();
        String requestURL = req.getRequestURI();
        String userIP = req.getRemoteHost();
        String sessionID = req.getSession().getId();
        Enumeration<String> parameterNames = req.getParameterNames();
        List<String > parameters = new ArrayList<>();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            if (isIgnoreWord(parameterName, excludedWords)) continue;
            String parameterValues = Arrays.asList(req.getParameterValues(parameterName)).toString();
            parameters.add(parameterName + "=" + parameterValues);
        }
        if(! parameters.isEmpty()){
            formData = parameters.toString().replaceAll("^.|.$", "");
        }
        return new StringBuilder("| ")
                .append(formatter.format(startDateTime)).append(" | ")
                .append(userIP).append(" | ")
                .append(httpMethod).append(" | ")
                .append(requestURL).append(" | ")
                .append(sessionID).append(" | ")
                .append("responseTime ms").append(" | ")
                .append(formData).toString();
    }
}
