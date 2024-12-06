package it.epicode.entity;

import it.epicode.Periodicita;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Rivista extends Pubblicazione {

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicita")
    private Periodicita periodicita;


    @Override
    public void stampa() {
        super.stampa();
        System.out.println("Periodicità: " + this.periodicita);
    }
}
