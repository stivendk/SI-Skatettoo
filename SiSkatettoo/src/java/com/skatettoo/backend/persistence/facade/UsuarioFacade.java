/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.frontend.controllers.FacesUtils;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.frontend.util.GeneradorPss;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author APRENDIZ
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "SiSkatettooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario iniciarSesion(Usuario us) throws Exception {
        Usuario usuario = null;
        TypedQuery<Usuario> query;
        try {
            query = em.createQuery("FROM Usuario u WHERE u.email = ?1 and u.password = ?2 ", Usuario.class);
            query.setParameter(1, us.getEmail());
            query.setParameter(2, us.getPassword());
            if (!query.getResultList().isEmpty()) {
                usuario = query.getResultList().get(0);
                if (usuario.getIdRol().getIdRol() == 2) {
                    if (true) {

                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }

    @Override
    public String registrarCl(Usuario us, Rol r) {
        try {
            r.setIdRol(1);
            us.setIdRol(r);
            getEntityManager().persist(us);
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
        return "";
    }

    @Override
    public Usuario consultarUsu(Usuario us) {
        try {
            return getEntityManager().createNamedQuery("Usuario.findByEmail", Usuario.class).setParameter("email", us.getEmail()).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object autenticar(Usuario usu) {
        Usuario u = new Usuario();
        String ps= passwordHash(usu.getPassword());
        try {
            TypedQuery<Usuario> query = getEntityManager().createNamedQuery("Usuario.findByEmail", Usuario.class).setParameter("email", usu.getEmail());
            if (query.getResultList().size() > 0) {
                u = query.getResultList().get(0);
                if (u.getPassword().equals(ps)) {
                    if (u.getIdRol().getIdRol() == 2) {
                        if (u.getEstadoUsuario() != 4) {
                            return u;
                        } else {
                            return 4;
                        }
                    } else {
                        return u;
                    }
                } else {
                    return 3;
                }
            } else {
                return 2;
            }
        } catch (Exception e) {
            Logger.getLogger("Error presentado en UsuarioFacade " + e.getMessage());
            return 1;
        }
    }

    @Override
    public Object enviarCorreo(Usuario us) {
        Usuario usuario = null;
        TypedQuery<Usuario> query;
        try {
            query = em.createQuery("FROM Usuario u WHERE u.email = ?1 ", Usuario.class);
            query.setParameter(1, us.getEmail());
            if (!query.getResultList().isEmpty()) {
                usuario = query.getResultList().get(0);
                usuario.setPassword(GeneradorPss.generadorPassword());
                getEntityManager().merge(usuario);
                Email e = new Email("Contraseña", "Tu contraseña es: " + " " + usuario.getPassword(), usuario.getEmail());
                e.enviarEmail();
            }
        } catch (Exception e) {
            
        }
        return "";
    }

    @Override
    public List<Usuario> consultarEstado(String estado, Sucursal s) {
        return em.createNamedQuery("Usuario.findByEstadoUsuario").setParameter("estado", estado).getResultList();
    }

    @Override
    public String passwordHash(String password) {
       Query query;
       String ps = null;
        try {
            query = em.createNativeQuery("SELECT encriptarpss(?)");
            query.setParameter(1, password);
            ps= (String) query.getSingleResult();
        } catch (Exception e) {
            throw e;
        }
        return ps;
        
    }
    
    
}
