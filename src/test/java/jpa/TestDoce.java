/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpa;

import com.ifpe.edu.br.projetorec.Cliente;
import com.ifpe.edu.br.projetorec.Doce;
import com.ifpe.edu.br.projetorec.Loja;
import com.ifpe.edu.br.projetorec.TipoPagamento;
import com.ifpe.edu.br.projetorec.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.Calendar;
import java.util.Date;
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
public class TestDoce {
    
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
    public void consultarDoce() {
        Doce doce = em.find(Doce.class, 6L);
        assertNotNull(doce);
        assertEquals("Brigadeiro", doce.getNome());
        assertEquals(1.0, doce.getPreco(), 0);
        assertEquals("Doce", doce.getTipo());
        assertEquals("Brigadeiro grande", doce.getDescricao());
        assertTrue(doce.getLojas().size() == 1);
    }
    
    @Test
    public void inserirDoce() {
        Date dataFabricacao = Calendar.getInstance().getTime();
        
        Doce doce = new Doce();
        doce.setNome("Bolo de rolo");
        doce.setTipo("Bolo");
        doce.setPreco(5.00);
        doce.setDataFrabricacao(dataFabricacao);
        doce.setDescricao("Bolo de rolo de goiabada");
        
        Loja j1 = em.find(Loja.class, 1L);
        Loja j2 = em.find(Loja.class, 2L);
        doce.setLojas(j1);
        doce.setLojas(j2);
        Venda v1 = new Venda();
        v1.setLoja(j1);
        Cliente c1 = em.find(Cliente.class, 1L);
        v1.setCliente(c1);
        v1.setPag(TipoPagamento.CARTAO);
        v1.setProdutos(doce);
        v1.setValor(15.0); 
        doce.setVendas(v1);
        em.persist(doce);
        em.flush();
        
        Doce doceAux = em.find(Doce.class, 14L);
        assertNotNull(doceAux);
        assertEquals("Bolo de rolo", doceAux.getNome());
        assertEquals("Bolo", doceAux.getTipo());
        assertEquals(5.0, doceAux.getPreco(), 0);
        assertEquals(dataFabricacao, doceAux.getDataFrabricacao());
        assertEquals("Bolo de rolo de goiabada", doceAux.getDescricao());
        assertTrue(doceAux.getLojas().size() == 2);
        assertTrue(doceAux.getVendas().get(0).getValor()==15.0);
        assertTrue("Bolo de rolo".equals(doceAux.getVendas().get(0).getProdutos().get(0).getNome()));
    }


    
}
