/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpa;

import com.ifpe.edu.br.projetorec.Cliente;
import com.ifpe.edu.br.projetorec.Loja;
import com.ifpe.edu.br.projetorec.Produto;
import com.ifpe.edu.br.projetorec.TipoPagamento;
import com.ifpe.edu.br.projetorec.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author angel
 */
public class TestVenda {
    
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
    public void consultarVenda(){
        Venda venda = em.find(Venda.class, 1L);
        assertNotNull(venda);
        assertEquals("Raphael", venda.getCliente().getNome());
        assertEquals("Dona Ju kit Festas", venda.getLoja().getNome());
        assertEquals(4.0, venda.getValor(), 0);
        assertEquals("CARTAO", venda.getPag().toString());
        assertTrue(venda.getProdutos().size() == 2);
    }
    
    @Test
    public void inserirVenda(){
        Venda venda = new Venda();
        
        Cliente cliente = em.find(Cliente.class, 1L);
        Loja loja = em.find(Loja.class, 1L);
        
        venda.setCliente(cliente);
        venda.setLoja(loja);
        venda.setValor(6.0);
        venda.setPag(TipoPagamento.PIX);
        Produto p1 = em.find(Produto.class, 1L);
        Produto p2 = em.find(Produto.class, 2L);
        Produto p3 = em.find(Produto.class, 3L);
        Produto p4 = em.find(Produto.class, 4L);
        venda.setProdutos(p1);
        venda.setProdutos(p2);
        venda.setProdutos(p3);
        venda.setProdutos(p4);
        
        em.persist(venda);
        em.flush();
        
        Venda vendaAux = em.find(Venda.class, 8L);
        assertNotNull(vendaAux);
        assertEquals("Raphael", vendaAux.getCliente().getNome());
        assertEquals("Dona Ju kit Festas", vendaAux.getLoja().getNome());
        assertEquals(6.0, vendaAux.getValor(), 0);
        assertEquals("PIX", vendaAux.getPag().toString());
        assertTrue(vendaAux.getProdutos().size() == 4);
    }
    
    @Test
    public void updateSomMerge()
    {
        Venda venda = em.find(Venda.class, 4L);
        venda.setValor(50.0);
        venda.getCliente().setNome("Pedrinho");
        venda.setPag(TipoPagamento.DINHEIRO);
        em.persist(venda);
        em.flush();
        Venda vendaAux = em.find(Venda.class, 4L);
        assertNotNull(vendaAux);
        assertEquals("Pedrinho", vendaAux.getCliente().getNome());
        assertEquals(50.0, vendaAux.getValor(), 0);
        assertEquals("DINHEIRO", vendaAux.getPag().toString());
        //assertTrue(vendaAux.getProdutos().size() == 4);        
    }
}
