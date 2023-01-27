package jpa;

import jakarta.persistence.*;
import org.junit.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ifpe.edu.br.projetorec.Cliente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestClienteJPQL {
    private static EntityManagerFactory emf;
    protected static Logger logger;
    private static EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);
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
    public void consultaClientePorNome(){
        logger.info("Executando consultaClientePorNome()");
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Pinoquio");
        List<Cliente> clientes = query.getResultList();

        clientes.forEach(c -> assertTrue(c.getNome().startsWith("Pinoquio")));

        assertEquals(1, clientes.size());

    }

    @Test
    public void consultarClientePorNamedQuery(){
        logger.info("Executando consultarClientePorNamedQuery()");
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.consultaClientePorNome", Cliente.class);
        query.setParameter("nome", "Raphael");
        List<Cliente> clientes = query.getResultList();

        clientes.forEach(c -> assertTrue(c.getNome().startsWith("Raphael")));

        assertEquals(1, clientes.size());
    }

    @Test
    public void dataNascimentoMAX(){
        logger.info("Executando dataNascimentoMAX()");
        Query query = em.createQuery("SELECT MAX(c.dataNascimento) FROM Cliente c");
        Date data = (Date) query.getSingleResult();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataMAX= sdf.format((Date) data);
        assertEquals("31/12/2000", dataMAX);
    }

    @Test
    public void dataNascimentoMIN(){
        logger.info("Executando dataNascimentoMIN()");
        Query query = em.createQuery("SELECT MIN(c.dataNascimento) FROM Cliente c");
        Date data = (Date) query.getSingleResult();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataMIN= sdf.format((Date) data);
        assertEquals("04/03/1997", dataMIN);
    }

}
