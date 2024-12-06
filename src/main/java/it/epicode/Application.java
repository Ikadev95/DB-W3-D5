package it.epicode;

import it.epicode.dao.CatalogoDAO;
import it.epicode.dao.PubblicazioneDAO;
import it.epicode.dao.LibroDAO;
import it.epicode.entity.Catalogo;
import it.epicode.entity.Pubblicazione;
import it.epicode.entity.Libro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        // Creazione dell'EntityManagerFactory e EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();

        // Creazione delle DAO
        CatalogoDAO catalogoDAO = new CatalogoDAO(em);
        PubblicazioneDAO pubblicazioneDAO = new PubblicazioneDAO(em);
        LibroDAO libroDAO = new LibroDAO(em);

        // Creazione di un nuovo catalogo
        Catalogo catalogo = new Catalogo();
        catalogoDAO.save(catalogo);
        System.out.println("Catalogo creato e salvato con successo.");

        // Creazione di una nuova pubblicazione di tipo Libro
        Libro pubblicazione1 = new Libro();
        pubblicazione1.setISBN(3456);
        pubblicazione1.setTitolo("Storia della Programmazione");
        pubblicazione1.setAnno_di_pubblicazione(LocalDate.of(2020, 5, 10));
        pubblicazione1.setAutore("Nome Autore");
        pubblicazione1.setGenere("Programmazione");


        // Aggiunta della pubblicazione al catalogo
        em.getTransaction().begin();
        catalogo.getPubblicazioni().add(pubblicazione1);
        pubblicazione1.setCatalogo(catalogo);
        em.persist(pubblicazione1);
        em.merge(catalogo);
        em.getTransaction().commit();
        System.out.println("Pubblicazione aggiunta al catalogo: " + pubblicazione1.getTitolo());

        // Ricerca di una pubblicazione per ISBN
        Pubblicazione pubblicazioneRicercata = pubblicazioneDAO.findByIsbn(3456);  // ISBN corretto
        if (pubblicazioneRicercata != null) {
            System.out.println("Pubblicazione trovata per ISBN: " + pubblicazioneRicercata.getTitolo());
        }

        // Ricerca di una pubblicazione per anno
        Pubblicazione pubblicazionePerAnno = pubblicazioneDAO.findByYearp(LocalDate.of(2020, 5, 10));
        if (pubblicazionePerAnno != null) {
            System.out.println("Pubblicazione trovata per anno: " + pubblicazionePerAnno.getTitolo());
        }

        // Ricerca di una pubblicazione per autore
        Libro libro = libroDAO.findByAuthor("Nome Autore");
        if (libro != null) {
            System.out.println("Libro trovato per autore: " + libro.getTitolo());
        }

        // Ricerca di pubblicazioni per titolo
        List<Pubblicazione> pubblicazioniPerTitolo = pubblicazioneDAO.findBytitle("Storia");
        System.out.println("Pubblicazioni trovate per titolo: ");
        for (Pubblicazione p : pubblicazioniPerTitolo) {
            System.out.println(p.getTitolo());
        }

/*        // Rimozione di una pubblicazione dal catalogo
        em.getTransaction().begin();
        Pubblicazione pubblicazioneDaRimuovere = pubblicazioneDAO.findByIsbn(3456);
        if (pubblicazioneDaRimuovere != null) {
            catalogo.getPubblicazioni().remove(pubblicazioneDaRimuovere);
            em.merge(catalogo);
            em.remove(pubblicazioneDaRimuovere);
            em.getTransaction().commit();
            System.out.println("Pubblicazione rimossa dal catalogo: " + pubblicazioneDaRimuovere.getTitolo());
        }*/

        // Chiusura dell'EntityManager
        em.close();
        emf.close();
    }
}
