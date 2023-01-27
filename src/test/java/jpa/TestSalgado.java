/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpa;

import com.ifpe.edu.br.projetorec.Cliente;
import com.ifpe.edu.br.projetorec.Loja;
import com.ifpe.edu.br.projetorec.Salgado;
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
public class TestSalgado {
    
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
    public void consultarSalgado() {
        Salgado salgado = em.find(Salgado.class, 1L);
        assertNotNull(salgado);
        assertEquals("Coxinha", salgado.getNome());
        assertEquals(3.0, salgado.getPreco(), 0);
        assertEquals("Salgado", salgado.getTipo());
        assertEquals("Coxinha de Frango com Catupiry", salgado.getDescricao());
        assertTrue(salgado.getLojas().size() == 1);
    }
    
    @Test
    public void inserirSalgado() {
        Date dataFabricacao = Calendar.getInstance().getTime();
        
        Salgado salgado = new Salgado();
        salgado.setNome("Pastel de queijo");
        salgado.setTipo("Pastel");
        salgado.setPreco(5.00);
        salgado.setDataFrabricacao(dataFabricacao);
        salgado.setDescricao("Pastel de queijo com verdura");
        
        Loja l1 = em.find(Loja.class, 1L);
        Loja l2 = em.find(Loja.class, 2L);
        
        salgado.setLojas(l1);
        salgado.setLojas(l2);
        Venda v1 = new Venda();
        v1.setLoja(l1);
        Cliente c1 = em.find(Cliente.class, 1L);
        v1.setCliente(c1);
        v1.setPag(TipoPagamento.PIX);
        v1.setProdutos(salgado);
        v1.setValor(10.0);
        salgado.setVendas(v1);
        em.persist(salgado);
        em.flush();
        
        Salgado salgadoAux = em.find(Salgado.class, 14L);
        assertNotNull(salgadoAux);
        assertEquals("Pastel de queijo", salgadoAux.getNome());
        assertEquals("Pastel", salgadoAux.getTipo());
        assertEquals(5.0, salgadoAux.getPreco(), 0);
        assertEquals(dataFabricacao, salgadoAux.getDataFrabricacao());
        assertEquals("Pastel de queijo com verdura", salgadoAux.getDescricao());
        assertTrue(salgadoAux.getVendas().size()==1);
        assertTrue("Pastel de queijo".equals(salgadoAux.getVendas().get(0).getProdutos().get(0).getNome()));
        assertTrue(salgadoAux.getVendas().get(0).getValor()==10.0);
        assertTrue(salgadoAux.getLojas().size() == 2);
    }
    
    @Test
    public void updateSemMerge()
    {
        Salgado salgado = em.find(Salgado.class, 2L);
        assertNotNull(salgado);
        salgado.setDescricao("salgado meio doce");
        salgado.setNome("coxinha com nozes");
        salgado.setPreco(4.5);
        em.persist(salgado);
        em.flush();
        Salgado auxSalgado = em.find(Salgado.class, 2L);
        assertNotNull(auxSalgado);
        assertEquals("salgado meio doce", auxSalgado.getDescricao());
        assertEquals("coxinha com nozes", auxSalgado.getNome());
    }
    
    @Test
    public void updateComMerge()
    {
        Salgado salgado = em.find(Salgado.class, 3L);
        em.clear();
        assertNotNull(salgado);
        salgado.setDescricao("salgadinho cheetos");
        salgado.setNome("Salgadinho de milho");
        salgado.setPreco(12.5);
        em.merge(salgado);
        em.flush();
        Salgado auxSalgado = em.find(Salgado.class, 3L);
        assertNotNull(auxSalgado);
        assertEquals("salgadinho cheetos", auxSalgado.getDescricao());
        assertEquals("Salgadinho de milho", auxSalgado.getNome());
    }
    @Test
    public void deleteSalgado()
    {
        Salgado salgado = em.find(Salgado.class, 4L);
        assertNotNull(salgado);
        em.remove(salgado);
        em.flush();
        Query querySalgado = em.createQuery("select s from Salgado s where s.id=4");
        List<Salgado> auxSalgado = querySalgado.getResultList();
        assertTrue(auxSalgado.isEmpty());
    }
    
}
