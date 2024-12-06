package it.epicode.dao;

import it.epicode.entity.Catalogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CatalogoDAO {
    private EntityManager em;

    public void save(Catalogo oggetto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(oggetto);
        transaction.commit();
    }

    public Catalogo findById(Long id) {
        return em.find(Catalogo.class, id);
    }

    public List<Catalogo> findAll() {
        return em.createNamedQuery("Trova_tutto_Archivio", Catalogo.class).getResultList();
    }

    public void update(Catalogo oggetto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(oggetto);
        transaction.commit();
    }

    public void delete(Catalogo oggetto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(oggetto);
        transaction.commit();
    }
}
