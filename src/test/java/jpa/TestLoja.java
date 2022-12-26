package jpa;

import com.ifpe.edu.br.projetorec.Loja;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    }

    @Test
    public void inserirLoja() {
        Loja loja = new Loja();
        loja.setCnpj("57.800.406/0001-76");
        loja.setNome("Caldo de cana bom Jesus");
        loja.setEmail("caldoJesus@contato.com");
        loja.setDataNascimento(Calendar.getInstance().getTime());
        em.persist(loja);
        em.flush();
    }

}
