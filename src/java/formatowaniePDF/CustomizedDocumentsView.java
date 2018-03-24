/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatowaniePDF;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
public class CustomizedDocumentsView implements Serializable {

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();

        // wczytanie obecnej daty
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter);

        pdf.getPageNumber();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        // dodawanie elementow do pdfa
        Paragraph para = new Paragraph();
        para.add(new Chunk("Raport wygenerowany dnia: " + formattedDateTime));
        para.add(Chunk.NEWLINE);
        para.add(Chunk.NEWLINE);

        para.add(new Chunk("RAPORT ZAMOWIEN: "));
        para.add(Chunk.NEWLINE);
        para.add(Chunk.NEWLINE);
        para.add(Chunk.NEWLINE);
        pdf.add(para);

    }

}
