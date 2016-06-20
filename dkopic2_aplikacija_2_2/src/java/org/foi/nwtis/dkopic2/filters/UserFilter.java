/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.filters;

import java.io.IOException;
import static java.lang.Math.toIntExact;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dkopic2.entity.Log;
import org.foi.nwtis.dkopic2.session.LogFacade;

/**
 *
 * @author domagoj
 */
public class UserFilter implements Filter {
    
    private FilterConfig config;
    
    @EJB
    LogFacade logFacade;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        String ip = request.getRemoteAddr();
        
        if (session != null)
        {
            if (session.getAttribute("user")!= null || session.getAttribute("admin") != null)
            {
                System.out.println("session");
                String user = "";

                if (session.getAttribute("user") != null)
                {
                    user = (String) session.getAttribute("user");
                }
                else if (session.getAttribute("admin") != null)
                {
                    user = (String) session.getAttribute("admin");
                }

                insertLog(user, url, ip, startTime);
                chain.doFilter(request, response);
            }
            else 
            {
                insertLog("N/A", url, ip, startTime);
                ((HttpServletResponse) response).sendRedirect("../login.xhtml");
            }
        }
    }

    @Override
    public void destroy() {
 
    }
    
    private void insertLog(String user, String url, String ip, long startTime) {
        Log log = new Log();
        log.setKorisnik(user);
        log.setTrajanje(toIntExact(System.currentTimeMillis() - startTime));
        log.setIpadresa(ip);
        log.setUrl(url);
        log.setStatus(1);
        log.setVrijeme(new Date());
        logFacade.create(log);
        System.out.println("Action logged.");
    }
}
