package it.epicode.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Entity

public class Libro extends Pubblicazione {

    @Getter
    @Setter
    @Column (name = "autore",nullable = false, length = 50)
    private String autore;

    @Getter
    @Setter
    @Column (name = "genere",nullable = false, length = 50)
    private String genere;


    @Override
    public void stampa() {
        super.stampa();
        System.out.println(this.getTitolo() + " " + this.getISBN() + " " + this.getAnnoDiPubblicazione() + " " + this.getNumeroPagine());
    }

}
