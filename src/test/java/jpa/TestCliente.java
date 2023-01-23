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
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        assertEquals("4073020000000002", cliente.getCartao().getNumero());
        assertEquals("Sun Dec 12 00:00:00 GFT 1999", cliente.getDataNascimento().toString());
        assertTrue(cliente.getVendas().size() == 1);
        assertTrue(cliente.getContatos().size() == 2);
    }
    
    @Test
    public void persistCliente()
    {
        Cliente cliente = new Cliente();
        cliente.setNome("Pedrim");
        cliente.setEmail("pedrim@pedrim");
        cliente.setCpf("112233");
        
        CartaoCredito cd = new CartaoCredito();
        cd.setBandeira("ELO");
        
        Calendar cexp = Calendar.getInstance();
        cexp.set(Calendar.YEAR, 2022);
        cexp.set(Calendar.MONTH, Calendar.NOVEMBER);
        cexp.set(Calendar.DAY_OF_MONTH, 19);
        
        cd.setDataExpiracao(cexp.getTime());
        cd.setNumero("123123");
        cd.setDono(cliente);
        cliente.setCartao(cd);
        
        cliente.setDataNascimento(cexp.getTime());
        
        Endereco en = new Endereco();
        en.setBairro("Tejipio");
        en.setCep("21313");
        en.setnCasa("432");
        en.setCidade("Recife");
        cliente.setEndereco(en);
        
        cliente.addContato("(81) 01122-3344");
        cliente.addContato("(81) 05566-7788");
        
        em.persist(cliente);
        em.flush();
        
        Cliente clienteAux = em.find(Cliente.class, 3L);
        assertNotNull(clienteAux);
        assertEquals("Pedrim", clienteAux.getNome());
        assertEquals("pedrim@pedrim", clienteAux.getEmail());
        assertEquals("112233", clienteAux.getCpf());
        assertEquals(cexp.getTime(), clienteAux.getDataNascimento());
        assertEquals("123123", clienteAux.getCartao().getNumero());
        assertEquals("Tejipio", clienteAux.getEndereco().getBairro());
        assertTrue(clienteAux.getContatos().size() == 2);
    }
    
}