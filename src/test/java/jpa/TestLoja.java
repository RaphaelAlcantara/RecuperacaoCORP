package jpa;

import com.ifpe.edu.br.projetorec.Loja;
import com.ifpe.edu.br.projetorec.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestLoja {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("lanchePU");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown() {
        if (!et.getRollbackOnly()) {
            et.commit();
        }
        em.close();
    }

    @Test
    public void consultarLoja() {
        Loja loja = em.find(Loja.class, 1L);
        assertNotNull(loja);
        assertEquals("43.856.021/0001-78", loja.getCnpj());
        assertEquals("Dona Ju kit Festas", loja.getNome());
        assertEquals("donaju@contato.com", loja.getEmail());
        assertEquals("Tue Oct 31 00:00:00 BRST 2017", loja.getDataNascimento().toString());
        assertTrue(loja.getProdutos().size() == 2);
        assertTrue(loja.getVendas().size() == 1);
    }

    @Test
    public void inserirLoja() {
        Date calendar = Calendar.getInstance().getTime();
        
        Loja loja = new Loja();
        loja.setCnpj("57.800.406/0001-76");
        loja.setNome("Caldo de cana bom Jesus");
        loja.setEmail("caldoJesus@contato.com");
        loja.setDataNascimento(calendar);
        
        Query query = em.createQuery("SELECT p FROM Produto p");
        List<Produto> produtos = query.getResultList();
        for(Produto produto : produtos){
            loja.setProdutos(produto);
        }
        
        em.persist(loja);
        em.flush();
        
        Loja lojaAux = em.find(Loja.class, 3L);
        assertNotNull(lojaAux);
        assertEquals("57.800.406/0001-76", lojaAux.getCnpj());
        assertEquals("Caldo de cana bom Jesus", lojaAux.getNome());
        assertEquals("caldoJesus@contato.com", lojaAux.getEmail());
        assertEquals(calendar, lojaAux.getDataNascimento());
        assertTrue(lojaAux.getProdutos().size() == 4);
    }

}
