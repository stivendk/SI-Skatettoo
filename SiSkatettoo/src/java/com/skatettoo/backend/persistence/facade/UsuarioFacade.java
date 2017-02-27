/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author StivenDavid
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "SkatettooSI1PU")
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
            query = em.createQuery("FROM Usuario u WHERE u.username = ?1 and u.password = ?2 ", Usuario.class);
            query.setParameter(1, us.getUsername());
            query.setParameter(2, us.getPassword());
            if(!query.getResultList().isEmpty()){
                usuario = query.getResultList().get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }

    /*public Usuario autenticar(Usuario u){
        try {
            TypedQuery<Usuario> usuariolist = getEntityManager().createNamedQuery("Usuario.findByUsername", Usuario.class)
                    .setParameter("username", u.getUsername());
            if (usuariolist.getResultList().get(0).getPassword().equals(u.getPassword())) {
                return usuariolist.getResultList().get(0);
            }
        } catch (Exception e) {
        }
        return null;
    }*/

    @Override
    public List<Usuario> sucursalUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
