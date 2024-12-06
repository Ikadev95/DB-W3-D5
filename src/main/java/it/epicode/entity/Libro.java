package it.epicode.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Entity

@NamedQuery(name = "get_by_author", query = "SELECT p FROM Pubblicazione p WHERE p.autore = :autore")
public class Libro extends Pubblicazione {

    @Getter
    @Setter
    @Column (name = "autore", length = 50)
    private String autore;

    @Getter
    @Setter
    @Column (name = "genere", length = 50)
    private String genere;


    @Override
    public void stampa() {
        super.stampa();
        System.out.println(this.getTitolo() + " " + this.getISBN() + " " + this.getAnno_di_pubblicazione() + " " + this.getNumeroPagine());
    }

}
