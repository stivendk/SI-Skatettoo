/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.reportes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.skatettoo.backend.persistence.entities.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author StivenDavid
 */
public class Generador {

    public static final long serialVersionUID = 45L;

    private List<Usuario> usu;
    private String titulo;

    public Generador(List<Usuario> usu, String titulo) {
        this.usu = usu;
        this.titulo = titulo;
    }

    public List<Usuario> getUsu() {
        return usu;
    }

    public void setUsu(List<Usuario> usu) {
        this.usu = usu;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private Object fuenteBuld = new Font(Font.FontFamily.COURIER, 30, Font.BOLD);

    public String generarPDF() throws Exception {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("img");
            path = path.substring(0, path.indexOf("\\build"));
            path = path + "\\web\\img\\";
            Document doc = new Document(PageSize.A4, 36, 36, 10, 10);
            PdfPTable tabla = new PdfPTable(3);
            PdfWriter.getInstance(doc, new FileOutputStream(path + "\\archivo\\reporte.pdf\\"));
            doc.open();
            Image img = Image.getInstance(path + "Skatetoo4.png");
            img.scaleAbsolute(40, 40);
            img.setAlignment(Element.ALIGN_LEFT);
            doc.add(img);
            doc.add((Element) getHeader(this.getTitulo()));
            doc.addAuthor("\n ");
            doc.addAuthor("\n ");
            doc.addAuthor("\n ");
            doc.addAuthor("\n ");
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1.4f, 0.8f, 0.8f});
            Object font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Reporte de tatuadores", (Font) font));
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingTop(0f);
            cell.setPaddingBottom(7f);
            cell.setBackgroundColor(new BaseColor(0, 0, 0));
            cell.setBorder(0);
            cell.setBorderWidthBottom(2f);
            tabla.addCell(cell);
            tabla.addCell("Tatuador");
            tabla.addCell("Cantidad de diseños");
            tabla.addCell("Citas realizadas");
            for(Usuario u : this.getUsu()){
                tabla.addCell(u.getNombre() + " " + u.getApellido());
                tabla.addCell(String.valueOf(u.getDisenioList().size()));
                tabla.addCell(String.valueOf(u.getCitaList1().size()));
            }
            doc.add(tabla);
            doc.close();
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"reporte.pdf\"");

            FileInputStream inputStream = new FileInputStream(new File(path + "\\archivo\\reporte.pdf\\"));
            OutputStream outputStream = externalContext.getResponseOutputStream();

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            context.responseComplete();
        } catch (Exception e) {
            throw e;
        }
        return "";
    }

    private Object getHeader(String texto) {
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(texto);
        p.add(c);
        return p;
    }
    

}
