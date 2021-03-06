/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Permiso;
import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.frontend.util.GeneradorPss;
import com.skatettoo.reportes.Generador;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "loginManagedBean")
@SessionScoped
public class LoginManagedBean implements Serializable {

    private static final long serialVersionUID = 24L;

    private Usuario usuario;
    private String password;
    private Part file;
    private List<Permiso> permis;
    private List<Usuario> consulta;
    private Sucursal suc;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
    @Inject
    SucursalManagedBean su;
    @EJB
    UsuarioFacadeLocal usfc;
    @Inject
    RegistroController rc;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Sucursal getSuc() {
        return suc;
    }

    public RegistroController getRc() {
        return rc;
    }

    public void setSuc(Sucursal suc) {
        this.suc = suc;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<Usuario> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<Usuario> consulta) {
        this.consulta = consulta;
    }

    public LoginManagedBean() {
    }

    public SucursalManagedBean getSu() {
        return su;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Permiso> getPermis() {
        return permis;
    }

    @PostConstruct
    public void init() {
        usuario = (Usuario) FacesUtils.getObjectMapSession("usuario");
        permis = getUsuario().getIdRol().getPermisoList();
    }

    public void modificarUs() {
        try {
            Usuario us = (Usuario) FacesUtils.getObjectMapSession("usuario");
            if (rc.validar(password)) {
                password = usfc.passwordHash(password);
                if (!us.getPassword().equals(password)) {
                    usuario.setPassword(password);
                }
                usfc.edit(usuario); 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Se a guardado satisfactoriamente!", "Se modifico"));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Las contraseñas no coinciden!", "Se modifico"));

            }
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error" + " " + e.getMessage());
        }
    }

    public List<Usuario> listE() {
        return usuario.getIdSucursal().getUsuarioList();
    }

    public String actualizarSucursal() {
        setSuc(usuario.getIdSucursal());
        return "/pages/admin/gsucursall.xhtml?faces-redirect=true";
    }

    public String actualizarFSuc() {
        setSuc(usuario.getIdSucursal());
        FacesUtils.setObjectAcceso("sucursal", usuario.getIdSucursal());
        return "/pages/admin/cambiarfoto.xhtml?faces-rediderect=true";
    }

    public void editarSucursal() {
        try {
            su.editarSucursal();
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("succes"));
        }
    }

    public void editarSucursalP() {
        try {
            su.getSucu().setFotoSuc(UploadFIles.uploadFile(file, GeneradorPss.generadorPassword()));
            su.editarSucursal();
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("succes"));
        }
    }

    public String gestSurcursal() {
        usuario.setIdSucursal(suc);
        FacesUtils.setObjectAcceso("sucursal", suc);
        return "/pages/admin/gsucursall?faces-redirect=true";
    }

    public void reportTat(Usuario t) {
        setUsuario(t);
        FacesUtils.setObjectAcceso("tatuador", t);
    }

    public void validatePassword(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        // Let required="true" do its job.
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {

            FacesMessage msg = new FacesMessage(prop.getString("errorPass"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();

        }

    }

    public void reporte() throws Exception {
        Generador g = new Generador(usuario.getIdSucursal().getUsuarioList(), usuario.getIdSucursal().getNombre());
        g.generarPDF();
    }

    public List<Usuario> estadoUsuario() {
        List<Usuario> t = new ArrayList<>();
        for (Usuario l : usuario.getIdSucursal().getUsuarioList()) {
            if (l.getEstadoUsuario() == 4) {
                t.add(l);
            }
        }
        return t;
    }

    public List<Usuario> estadoUsuarioA() {
        List<Usuario> t = new ArrayList<>();
        for (Usuario l : usuario.getIdSucursal().getUsuarioList()) {
            if (l.getEstadoUsuario() != 4) {
                t.add(l);
            }
        }
        return t;
    }

    public List<Usuario> masSolicitado() {
        List<Usuario> t = new ArrayList<>();
        for (Usuario l : usuario.getIdSucursal().getUsuarioList()) {
            if (l.getCitaList1().size() >= usuario.getIdSucursal().getCitaList().size()) {
                t.add(l);
            }
        }
        return t;
    }

    public List<Usuario> tamasSolicitado() {
        Rol r = new Rol();
        r.setIdRol(2);
        List<Usuario> u = new ArrayList<>();
        for (Usuario t : usuario.getIdSucursal().getUsuarioList()) {
            if (t.getIdRol() != r) {
                u.add(t);
                if (t.getCitaList().size() >= usuario.getIdSucursal().getCitaList().size()) {

                }
            }
        }
        return u;
    }

    public void activar(Usuario u) {
        try {
            setUsuario(u);
            u.setEstadoUsuario(1);
            usfc.edit(u);
            Email e = new Email(prop.getString("emailTas"), prop.getString("newT") + "\n" + prop.getString("newTema") + " " + u.getEmail() + "\n" + prop.getString("newTpss") + " " + u.getPassword(), u.getEmail());
            e.enviarEmail();
            FacesUtils.mensaje(prop.getString("nue"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError") + " " + e.getMessage());
        }
    }

    public void desactivar(Usuario u) {
        try {
            setUsuario(u);
            u.setEstadoUsuario(4);
            usfc.edit(u);
            Email e = new Email(prop.getString("asun"), prop.getString("descrip"), u.getEmail());
            e.enviarEmail();
            FacesUtils.mensaje(prop.getString("msjdes"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError") + " " + e.getMessage());
        }
    }

    public String cerrarSesion() {
        FacesUtils.removerObjectAcceso("usuario");
        FacesUtils.removerObjectAcceso("tatuador");
        FacesUtils.removerObjectAcceso("sucursal");
        FacesUtils.removerObjectAcceso("estilo");
        FacesUtils.removerObjectAcceso("disenio");
        FacesUtils.removerObjectAcceso("localidad");
        FacesUtils.removerObjectAcceso("noticia");
        FacesUtils.removerObjectAcceso("cita");
        usuario = null;
        return "/index.xhtml?faces-redirect=true";
    }

}
