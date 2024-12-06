package it.epicode;


import it.epicode.dao.PubblicazioneDAO;
import it.epicode.entity.Catalogo;
import it.epicode.entity.Pubblicazione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.logging.Logger;

public class Application {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    EntityManager em = emf.createEntityManager();
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        Catalogo catalogo = new Catalogo();



    }


    public void aggiungiACatalogo(Pubblicazione p , Catalogo c){
        em.getTransaction().begin();
        c.getPubblicazioni().add(p);
        p.setCatalogo(c);
        em.persist(c);
        em.persist(p);
        em.getTransaction().commit();
        System.out.println("aggiunto al catalogo");
    }

   public void rimuoviDaCatalogo(int isbn, Catalogo c){
        em.getTransaction().begin();
       PubblicazioneDAO PubbDao = new PubblicazioneDAO(em);
       Pubblicazione p = PubbDao.findByIsbn(isbn);
       if(p != null){
           c.getPubblicazioni().remove(p);
           em.merge(c);
           em.merge(p);
           em.getTransaction().commit();
           System.out.println("rimosso dal catalogo");
       }
    }

    public Pubblicazione ricercaPerIsbn (int isbn){
        PubblicazioneDAO PubbDao = new PubblicazioneDAO(em);
        return PubbDao.findByIsbn(isbn);
    }

    public Pubblicazione ricercaPerAnnoPubblicazione (LocalDate annoP){
        PubblicazioneDAO PubbDao = new PubblicazioneDAO(em);
        return PubbDao.findByYearp(annoP);
    }

}
