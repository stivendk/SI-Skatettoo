/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.backend.persistence.entities.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author APRENDIZ
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    Usuario iniciarSesion(Usuario us)throws Exception;
    
    Usuario consultarUsu(Usuario us);
    
    String registrarCl(Usuario us, Rol r);
    
    Object autenticar(Usuario usu);
    
    Object enviarCorreo(Usuario usu);
}
