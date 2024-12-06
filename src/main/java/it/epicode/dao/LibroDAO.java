package it.epicode.dao;

import it.epicode.entity.Libro;
import it.epicode.entity.Pubblicazione;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LibroDAO {
    private EntityManager em;

    public void save(Libro oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Libro findByAuthor (String autore){
        return em.createNamedQuery("get_by_author", Libro.class)
                .setParameter("autore", autore)
                .getSingleResult();
    }

    public Libro findById(Long id) {
        return em.find(Libro.class, id);
    }

    public List<Libro> findAll() {
        return em.createNamedQuery("Trova_tutto_Libro", Libro.class).getResultList();
    }
    
    public void update(Libro oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Libro oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }


}
