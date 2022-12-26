/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package jpa;

import com.ifpe.edu.br.projetorec.CartaoCredito;
import com.ifpe.edu.br.projetorec.Cliente;
import com.ifpe.edu.br.projetorec.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
    /*
 *
 * @author gabri
 */
public class TestCliente {
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
    public void consultarCliente() {
        Cliente cliente = em.find(Cliente.class, 1L);
        assertNotNull(cliente);
        assertEquals("12345678911", cliente.getCpf());
        assertEquals("Raphael", cliente.getNome());
        assertEquals("rapha@gmail.com", cliente.getEmail());
        assertEquals(2, cliente.getContatos().size());
        assertEquals("VISA", cliente.getCartao().getBandeira());
        assertTrue(cliente.getContatos().contains("(81) 97654-9685"));
    }
    @Test
    public void persistCliente()
    {
        Cliente cliente = new Cliente();
        cliente.setNome("Pedrim");
        cliente.setEmail("pedrim@pedrim");
        cliente.setCpf("112233");
        
        CartaoCredito cd = new CartaoCredito();
        cd.setBandeira("CU");
        Calendar it1 = Calendar.getInstance();
        it1.set(Calendar.YEAR, 2022);
        it1.set(Calendar.MONTH, Calendar.NOVEMBER);
        it1.set(Calendar.DAY_OF_MONTH, 19);
        cd.setDataExpiracao(it1.getTime());
        cliente.setDataNascimento(it1.getTime());
        cd.setNumero("123123");
        cd.setDono(cliente);
        cliente.setCartao(cd);
        Endereco en = new Endereco();
        en.setBairro("Tejipio");
        en.setCep("21313");
        en.setnCasa("432");
        en.setCidade("Recife");
        cliente.setEndereco(en);
        em.persist(cliente);
        em.flush();
        Cliente cliente2 = em.find(Cliente.class, 3L);
        assertNotNull(cliente2);
        assertEquals("Pedrim", cliente2.getNome());
    }
}