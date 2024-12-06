package it.epicode;

import com.github.javafaker.Faker;
import it.epicode.dao.*;
import it.epicode.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Application {

    public static void aggiungiPubblicazioneAlCatalogo(EntityManager em, Catalogo catalogo, Pubblicazione pubblicazione) {
        em.getTransaction().begin();
        catalogo.getPubblicazioni().add(pubblicazione);
        pubblicazione.setCatalogo(catalogo);
        em.persist(pubblicazione);
        em.merge(catalogo);
        em.getTransaction().commit();
        System.out.println("Pubblicazione aggiunta al catalogo: " + pubblicazione.getTitolo());
    }

    public static void ricercaPubblicazionePerIsbn(PubblicazioneDAO pubblicazioneDAO, int isbn) {
        Pubblicazione pubblicazione = pubblicazioneDAO.findByIsbn(isbn);
        if (pubblicazione != null) {
            System.out.println("Pubblicazione trovata per ISBN: " + pubblicazione.getTitolo());
        }
    }

    public static void ricercaPubblicazionePerAnno(PubblicazioneDAO pubblicazioneDAO, LocalDate anno) {
        Pubblicazione pubblicazione = pubblicazioneDAO.findByYearp(anno);
        if (pubblicazione != null) {
            System.out.println("Pubblicazione trovata per anno: " + pubblicazione.getTitolo());
        }
    }

    public static void ricercaLibroPerAutore(LibroDAO libroDAO, String autore) {
        Libro libro = libroDAO.findByAuthor(autore);
        if (libro != null) {
            System.out.println("Libro trovato per autore: " + libro.getTitolo());
        }
    }

    public static void ricercaPubblicazioniPerTitolo(PubblicazioneDAO pubblicazioneDAO, String titolo) {
        List<Pubblicazione> pubblicazioni = pubblicazioneDAO.findBytitle(titolo);
        System.out.println("Pubblicazioni trovate per titolo: ");
        for (Pubblicazione p : pubblicazioni) {
            System.out.println(p.getTitolo());
        }
    }

    public static void rimuoviPubblicazioneDalCatalogo(EntityManager em, Catalogo catalogo, PubblicazioneDAO pubblicazioneDAO, int isbn) {
        em.getTransaction().begin();
        Pubblicazione pubblicazioneDaRimuovere = pubblicazioneDAO.findByIsbn(isbn);
        if (pubblicazioneDaRimuovere != null) {
            catalogo.getPubblicazioni().remove(pubblicazioneDaRimuovere);
            em.merge(catalogo);
            em.remove(pubblicazioneDaRimuovere);
            em.getTransaction().commit();
            System.out.println("Pubblicazione rimossa dal catalogo: " + pubblicazioneDaRimuovere.getTitolo());
        }
    }

    public static void cercaPrestitiPerTessera(UtenteDAO UtenteDAO, int tessera) {
        List<Prestito> prestiti = UtenteDAO.getPrestitiByTessera(tessera);
        System.out.println("Prestiti trovati per tessera: ");

        if(prestiti == null){
            System.out.println("non ci sono prestiti su questa tessera");
        }
        else {
            for (Prestito p : prestiti) {
                System.out.println("Utente: " + p.getUtente().getNome());
                System.out.println("Titolo pubblicazione in prestito: " + p.getPubblicazione().getTitolo());
            }
        }
    }

    public static void cercaPrestitiScaduti(PrestitoDAO prestitoDAO) {
        List<Prestito> prestiti = prestitoDAO.getExpired();
        for (Prestito prestito : prestiti) {
            System.out.println("Prestito scaduto: " + prestito);
        }
        if(prestiti.isEmpty()){
            System.out.println("non ci sono presiti scaduti");
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();

        Faker faker = new Faker(new Locale("it"));

        CatalogoDAO catalogoDAO = new CatalogoDAO(em);
        PubblicazioneDAO pubblicazioneDAO = new PubblicazioneDAO(em);
        LibroDAO libroDAO = new LibroDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        Catalogo catalogo = new Catalogo();
        catalogoDAO.save(catalogo);
        System.out.println("Catalogo creato e salvato con successo.");


        List<Libro> libriCreati = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Libro libro = new Libro();
            libro.setISBN(faker.number().numberBetween(1000, 9999));
            libro.setTitolo(faker.book().title());
            libro.setAnno_di_pubblicazione(LocalDate.of(faker.number().numberBetween(2019, 2024), faker.number().numberBetween(1, 12), faker.number().numberBetween(1, 28)));
            libro.setAutore(faker.book().author());
            libro.setGenere(faker.book().genre());
            aggiungiPubblicazioneAlCatalogo(em, catalogo, libro);
            libriCreati.add(libro);
        }


        Libro pubblicazione1 = new Libro();
        pubblicazione1.setISBN(3456);
        pubblicazione1.setTitolo("Storia della Programmazione");
        pubblicazione1.setAnno_di_pubblicazione(LocalDate.of(2020, 5, 10));
        pubblicazione1.setAutore("Nome Autore");
        pubblicazione1.setGenere("Programmazione");


        libriCreati.add(pubblicazione1);
        aggiungiPubblicazioneAlCatalogo(em, catalogo, pubblicazione1);


        List<Long> idUtenti = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Utente utente = new Utente();
            utente.setNome(faker.name().fullName());
            utente.setCognome(faker.name().lastName());
            utente.setDataDiNascita(LocalDate.of(faker.number().numberBetween(1970, 2002), faker.number().numberBetween(1, 12), faker.number().numberBetween(1, 28)));
            utente.setNumero_di_tessera(faker.number().numberBetween(1, 100));
            utenteDAO.save(utente);
            idUtenti.add(utente.getId());
        }


        for (int i = 0; i < 7; i++) {
            Prestito prestito = new Prestito();
            Long idUtente = idUtenti.get(faker.number().numberBetween(0, idUtenti.size() - 1));
            Utente utente = utenteDAO.findById(idUtente);
            if (utente == null) continue;


            Libro libro = libriCreati.get(faker.number().numberBetween(0, libriCreati.size() - 1));
            LocalDate dataInizioPrestito = LocalDate.of(
                    faker.number().numberBetween(2022, 2024),
                    faker.number().numberBetween(1, 12),
                    faker.number().numberBetween(1, 28)
            );
            prestito.setUtente(utente);
            prestito.setPubblicazione(libro);
            prestito.setDataInizioPrestito(dataInizioPrestito);
            prestito.setData_restituzione_prevista(dataInizioPrestito.plusDays(30));

            if (faker.bool().bool()) {
                prestito.setData_restituzione_effettiva(dataInizioPrestito.plusDays(faker.number().numberBetween(20, 40)));
            }
            prestitoDAO.save(prestito);
            System.out.println("Prestito creato: " + prestito);
        }

        Long idUtente = idUtenti.get(faker.number().numberBetween(0, idUtenti.size() - 1));
        Utente utente = utenteDAO.findById(idUtente);
        ricercaPubblicazionePerIsbn(pubblicazioneDAO, 3456);
        ricercaPubblicazionePerAnno(pubblicazioneDAO, LocalDate.of(2020, 5, 10));
        ricercaLibroPerAutore(libroDAO, "Nome Autore");
        ricercaPubblicazioniPerTitolo(pubblicazioneDAO, "Storia");
        rimuoviPubblicazioneDalCatalogo(em, catalogo, pubblicazioneDAO, 3456);
        cercaPrestitiPerTessera(utenteDAO,utente.getNumero_di_tessera());
        cercaPrestitiScaduti(prestitoDAO);

        em.close();
        emf.close();
    }
}
