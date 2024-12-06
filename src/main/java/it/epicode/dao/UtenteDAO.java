package it.epicode.dao;

import it.epicode.entity.Prestito;
import it.epicode.entity.Pubblicazione;
import it.epicode.entity.Utente;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UtenteDAO {
    private EntityManager em;

    public void save(Utente oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Utente findById(Long id) {
        return em.find(Utente.class, id);
    }

    public List<Utente> findAll() {
        return em.createNamedQuery("Trova_tutto_Utente", Utente.class).getResultList();
    }

    public void update(Utente oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Utente oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }

    public List<Prestito> getPrestitiByTessera(int numero_di_tessera) {
        List<Utente> utenti = em.createNamedQuery("get_by_card", Utente.class)
                .setParameter("numero_di_tessera", numero_di_tessera)
                .getResultList();

        if (utenti.isEmpty()) {
            return new ArrayList<>();
        }
        Utente utente = utenti.getFirst();
        return utente.getPrestiti();
    }



}
