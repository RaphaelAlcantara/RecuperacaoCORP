package jpa;

import com.ifpe.edu.br.projetorec.Doce;
import com.ifpe.edu.br.projetorec.Loja;
import com.ifpe.edu.br.projetorec.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
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
        assertEquals("Tue Oct 31 00:00:00 GFT 2017", loja.getDataNascimento().toString());
    }

    @Test
    public void inserirLoja() {
        Date calendar = Calendar.getInstance().getTime();
        
        Loja loja = new Loja();
        loja.setCnpj("57.800.406/0001-76");
        loja.setNome("Caldo de cana bom Jesus");
        loja.setEmail("caldoJesus@contato.com");
        loja.setDataNascimento(calendar);
        Produto p1 = em.find(Produto.class, 1L);
        Produto p2 = em.find(Produto.class, 2L);
        Produto p3 = em.find(Produto.class, 3L);
        Produto p4 = em.find(Produto.class, 4L);
        loja.setProdutos(p1);
        loja.setProdutos(p2);
        loja.setProdutos(p3);
        loja.setProdutos(p4);        
        em.persist(loja);
        em.flush();
        
        Loja lojaAux = em.find(Loja.class, 5L);
        assertNotNull(lojaAux);
        assertEquals("57.800.406/0001-76", lojaAux.getCnpj());
        assertEquals("Caldo de cana bom Jesus", lojaAux.getNome());
        assertEquals("caldoJesus@contato.com", lojaAux.getEmail());
        assertEquals(calendar, lojaAux.getDataNascimento());
        assertTrue(lojaAux.getProdutos().size() == 4);
    }

}
