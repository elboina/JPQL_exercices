/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.test;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.Assert;
import org.junit.Test;
import streaming.entity.Film;

/**
 *
 * @author formation
 */
public class JPQLTest {

    @Test
    public void req1() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT f FROM Film f WHERE f.id=4");

        Film film = (Film) query.getSingleResult();
        Assert.assertEquals("Fargo", film.getTitre());
    }

    @Test
    public void req2() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM Film f");

        long nbFilm = (long) query.getSingleResult();
        Assert.assertEquals(4, nbFilm);

    }

    @Test
    public void req3() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT MIN(f.annee) FROM Film f ");

        int anneeMin = (int) query.getSingleResult();
        Assert.assertEquals(1968, anneeMin);

    }

    @Test
    public void req4() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery(""
                + "SELECT COUNT(l) "
                + "FROM Lien l "
                + "JOIN l.film f "
                + "WHERE f.titre='Big Lebowski'");

        long n = (long) query.getSingleResult();
        Assert.assertEquals(0, n);

    }

    @Test
    public void req5() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f)"
                + " FROM Film f "
                + "JOIN f.realisateurs r "
                + "WHERE r.nom ='Polanski'");

        long n = (long) query.getSingleResult();
        Assert.assertEquals(2, n);

    }

    @Test
    public void req6() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f)"
                + " FROM Film f "
                + "JOIN f.acteurs a "
                + "WHERE a.nom ='Polanski'");

        long n = (long) query.getSingleResult();
        Assert.assertEquals(1, n);

    }

    @Test
    public void req7() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) "
                + "FROM Film f "
                + "JOIN f.acteurs a "
                + "JOIN f.realisateurs r"
                + " WHERE a.nom = 'Polanski' "
                + "AND r.nom= 'Polanski'"
        );
        long n = (long) query.getSingleResult();
        Assert.assertEquals(1, n);

    }

    @Test
    public void req8() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT f.titre "
                + "FROM Film f "
                + "JOIN f.genre g "
                + "JOIN f.realisateurs r "
                + "WHERE g.nom = 'Horreur' "
                + "AND r.nom = 'Polanski'");
        String n = (String) query.getSingleResult();
        System.out.println(n);

    }

    @Test
    public void req9() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f)"
                + " FROM Film f "
                + "JOIN f.realisateurs r "
                + "WHERE r.nom ='Coen'"
                + "AND r.prenom = 'Joel'");

        long n = (long) query.getSingleResult();
        Assert.assertEquals(2, n);

    }

    @Test
    public void req10() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.realisateurs r WHERE r.nom = 'Coen' AND r.prenom = 'Joel' "
                + "INTERSECT SELECT COUNT(f) FROM Film f JOIN f.realisateurs r WHERE r.nom = 'Coen' AND r.prenom ='Ethan'");

        long n = (long) query.getSingleResult();
        Assert.assertEquals(2, n);
    }

    @Test
    public void req11() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.realisateurs r WHERE r.nom = 'Coen' AND r.prenom = 'Joel'"
                + " INTERSECT SELECT COUNT(f) "
                + "FROM Film f "
                + "JOIN f.realisateurs r "
                + "WHERE r.nom = 'Coen'"
                + " AND r.prenom ='Ethan' "
                + "INTERSECT SELECT COUNT(f) FROM Film f JOIN f.acteurs a WHERE a.nom = 'Buscemi' AND a.prenom = 'Steve'");

        long n = (long) query.getSingleResult();
        Assert.assertEquals(2, n);
    }

    @Test
    public void req12() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.realisateurs r JOIN f.genre g WHERE r.nom = 'Coen' AND r.prenom = 'Joel' AND g.nom = 'Policier'"
                + " INTERSECT SELECT COUNT(f) "
                + "FROM Film f "
                + "JOIN f.realisateurs r JOIN f.genre g"
                + " WHERE r.nom = 'Coen'"
                + " AND r.prenom ='Ethan' AND g.nom = 'Policier'"
                + "INTERSECT SELECT COUNT(f) FROM Film f JOIN f.acteurs a JOIN f.genre g WHERE a.nom = 'Buscemi' AND a.prenom = 'Steve' AND g.nom = 'Policier'"
        );

        long n = (long) query.getSingleResult();
        Assert.assertEquals(1, n);
    }

    @Test
    public void req13() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(s.saisons) FROM Serie s WHERE s.titre = 'Dexter'");
        long n = (long) query.getSingleResult();
        Assert.assertEquals(8, n);

    }

    @Test
    public void req14() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(s.episodes) FROM Saison s "
                + "JOIN s.serie se "
                + "WHERE se.titre = 'Dexter' "
                + "AND s.numSaison = 8");
        long n = (long) query.getSingleResult();
        Assert.assertEquals(12, n);

    }

    @Test
    public void req15() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(s.episodes) FROM Saison s "
                + "JOIN s.serie se "
                + "WHERE se.titre = 'Dexter' ");
        long n = (long) query.getSingleResult();
        Assert.assertEquals(96, n);

    }

    @Test
    public void req16() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(l) "
                + "FROM Lien l "
                + "JOIN l.film f "
                + "JOIN f.genre g "
                + "JOIN f.pays p "
                + "WHERE g.nom = 'Policier' "
                + "AND p.nom = 'USA'");
        long n = (long) query.getSingleResult();
        Assert.assertEquals(3, n);

    }

    @Test
    public void req17() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(l) FROM Lien l JOIN l.film f JOIN f.genre g JOIN f.realisateurs r WHERE g.nom = 'Horreur' AND r.nom = 'Polanski' ");
        long n = (long) query.getSingleResult();
        Assert.assertEquals(1, n);

    }

    @Test
    public void req18() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT f.titre FROM Film f  JOIN f.genre g WHERE g.nom ='Horreur' EXCEPT SELECT f.titre FROM Film f JOIN f.acteurs a WHERE a.nom = 'Polanski' ");
        List<String> titre = query.getResultList();
        System.out.println(titre);
    }

    @Test
    public void req19() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT f.titre FROM Film f INTERSECT SELECT f.titre FROM Film f JOIN f.acteurs a WHERE a.nom = 'Polanski'");
        List<String> titre = query.getResultList();
        System.out.println(titre);

    }

    @Test
    public void req20() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery(" SELECT f.titre FROM Film f JOIN f.acteurs a WHERE a.nom = 'Polanski'"
                + " UNION SELECT f.titre FROM Film f JOIN f.genre g WHERE g.nom = 'Horreur' ");
        List<String> titre = query.getResultList();
        System.out.println(titre);

    }

    @Test
    public void req21() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT g.nom, COUNT(f) FROM Film f JOIN f.genre g GROUP BY g");
        
         List<Object[]> list = query.getResultList();
         
         for(Object[] o:list)
         {
         System.out.println(o[0] + " " + o[1]);
         }
         

    }
    
    @Test
    public void req22a()
    {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT r.nom, COUNT(f) FROM Film f JOIN f.realisateurs r GROUP BY  r.nom");
        List<Object[]> list = query.getResultList();
        
        for(Object[] o:list)
        {
        System.out.println(o[0] + " " + o[1]);
        }
    
    }
    
     @Test
    public void req22b()
    {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT DISTINCT r.nom, COUNT(f) FROM Film f JOIN f.realisateurs r GROUP BY r");
        List<Object[]> list = query.getResultList();
        
        for(Object[] o:list)
        {
        System.out.println(o[0] + " " + o[1]);
        }
    
    }
    
    @Test 
        public void req23()
    {
         EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT r.nom, COUNT(f) FROM Film f JOIN f.realisateurs r JOIN r.filmsRealises rf GROUP BY r HAVING COUNT(rf) >=2");
        List<Object[]> list = query.getResultList();
        
        for(Object[] o:list)
        {
        System.out.println(o[0] + " " + o[1]);
        }
    
    }
}
