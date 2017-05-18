/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.security;

import com.skatettoo.backend.persistence.entities.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author StivenDavid
 */
@WebFilter(filterName = "SessionFilter", urlPatterns = {"/pages/*"})
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String s = req.getRequestURL().toString();

        if (!s.contains("xhtml")) {
            res.sendRedirect(req.getContextPath() + "/pages/usuario/inicio.xhtml");
        } else {
            if(req.getSession().getAttribute("usuario")==null){
                res.sendRedirect(req.getContextPath() + "/index.xhtml");
            }else{
                Usuario u = (Usuario) req.getSession().getAttribute("usuario");
                if(s.contains("disenios") && u.getIdRol().getIdRol() != 1){
                    res.sendRedirect(req.getContextPath() + "/pages/usuario/404.xhtml");
                } else if(s.contains("tatuador") && u.getIdRol().getIdRol() != 2){
                    res.sendRedirect(req.getContextPath() + "/pages/usuario/404.xhtml");
                } else if(s.contains("admin") && u.getIdRol().getIdRol() != 3){
                    res.sendRedirect(req.getContextPath() + "/pages/usuario/404.xhtml");
                }
            }
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }

}
