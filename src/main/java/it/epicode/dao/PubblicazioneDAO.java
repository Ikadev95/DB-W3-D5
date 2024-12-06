package it.epicode.dao;

import it.epicode.entity.Pubblicazione;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class PubblicazioneDAO {
    private EntityManager em;

    public void save(Pubblicazione oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Pubblicazione findByIsbn(int isbn){
        return em.createNamedQuery("get_by_isbn", Pubblicazione.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    public Pubblicazione findByYearp(LocalDate annoDiPubblicazione) {
        return em.createNamedQuery("get_by_yearp", Pubblicazione.class)
                .setParameter("anno_di_pubblicazione", annoDiPubblicazione)
                .getSingleResult();
    }

    public List<Pubblicazione> findBytitle(String title){
        return em.createNamedQuery("get_by_title", Pubblicazione.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    public Pubblicazione findById(Long id) {
        return em.find(Pubblicazione.class, id);
    }

    public List<Pubblicazione> findAll() {
        return em.createNamedQuery("Trova_tutto_Pubblicazione", Pubblicazione.class).getResultList();
    }

    public void update(Pubblicazione oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Pubblicazione oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }
}
