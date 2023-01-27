package jpa;

import com.ifpe.edu.br.projetorec.Loja;
import com.ifpe.edu.br.projetorec.Produto;
import jakarta.persistence.*;
import org.junit.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class TestProdutoJPQL {
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
    public void QuantidadeProdutoSalgado(){
        logger.info("Executando QuantidadeProdutoSalgado()");
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.tipo = :tipo", Produto.class);
        query.setParameter("tipo", "Salgado");
        List<Produto> produtos = query.getResultList();

        produtos.forEach(p -> assertTrue(p.getTipo().startsWith("Salgado")));

                assertEquals(5, produtos.size());
    }

    @Test
    public void QuantidadeProdutosSalgadoLimitado(){
        logger.info("Executando QuantidadeProdutosSalgadoLimitado()");
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.tipo = :tipo", Produto.class);
        query.setParameter("tipo", "Salgado");
        query.setMaxResults(2); //Possui 5 cadastrados, mas limita para 2
        List<Produto> produtos = query.getResultList();

        produtos.forEach(p -> assertTrue(p.getTipo().startsWith("Salgado")));

                assertEquals(2, produtos.size());
    }

    @Test
    public void QuantidadeProdutosSalgadoOrdenado(){
        logger.info("Executando QuantidadeProdutosSalgadoOrdenado()");
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.tipo = :tipo ORDER BY p.nome", Produto.class);
        query.setParameter("tipo", "Salgado");
        List<Produto> produtos = query.getResultList();

        produtos.forEach(p -> assertTrue(p.getTipo().startsWith("Salgado")));

                assertEquals(5, produtos.size());
    }

    @Test
    public void QuantidadeProdutosNaLoja(){
        logger.info("Executando QuantidadeProdutosNaLoja()");
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.lojas IS NOT EMPTY", Produto.class);
        List<Produto> produtos = query.getResultList();

        produtos.forEach(p -> assertTrue(p.getLojas().size() > 0));

                assertEquals(13, produtos.size());
    }

    @Test
    public void QuantidadeProdutosDocesPorLoja(){
        logger.info("Executando QuantidadeProdutosDocesPorLoja()");
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.tipo = :tipo AND p.lojas IS NOT EMPTY", Produto.class);
        query.setParameter("tipo", "Doce");
        List<Produto> produtos = query.getResultList();

        produtos.forEach(p -> assertTrue(p.getTipo().startsWith("Doce")));

                assertEquals(8, produtos.size());
    }

    //Count
    @Test
    public void QuantidadeProdutosSalgados(){
        logger.info("Executando QuantidadeProdutosSalgados()");
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Produto p WHERE p.tipo = :tipo", Long.class);
        query.setParameter("tipo", "Salgado");
        Long quantidade = query.getSingleResult();

        assertEquals(5, quantidade.intValue());
    }

    //Sum
    @Test
    public void ValorTotalProdutosSalgados(){
        logger.info("Executando ValorTotalProdutosSalgados()");
        TypedQuery<Double> query = em.createQuery("SELECT SUM(p.preco) FROM Produto p WHERE p.tipo = :tipo", Double.class);
        query.setParameter("tipo", "Salgado");
        Double valorTotal = query.getSingleResult();

        assertEquals(13.0, valorTotal, 0.0001);
    }

    //Avg
    @Test
    public void ValorMedioProdutosSalgados(){
        logger.info("Executando ValorMedioProdutosSalgados()");
        TypedQuery<Double> query = em.createQuery("SELECT AVG(p.preco) FROM Produto p WHERE p.tipo = :tipo", Double.class);
        query.setParameter("tipo", "Salgado");
        Double valorMedio = query.getSingleResult();

        assertEquals(2.6, valorMedio, 0.0001);
    }

    @Test
    //group by
    public void ValorTotalProdutosPorTipo(){
        logger.info("Executando ValorTotalProdutosPorTipo()");
        TypedQuery<Object[]> query = em.createQuery("SELECT p.tipo, SUM(p.preco) FROM Produto p GROUP BY p.tipo", Object[].class);
        List<Object[]> resultado = query.getResultList();

        resultado.forEach(r -> logger.info(r[0] + " - " + r[1]));

                assertEquals(2, resultado.size());
    }

    //having
    @Test
    public void ValorTotalProdutosPorTipoComFiltro(){
        logger.info("Executando ValorTotalProdutosPorTipoComFiltro()");
        TypedQuery<Object[]> query = em.createQuery("SELECT p.tipo, SUM(p.preco) FROM Produto p GROUP BY p.tipo HAVING SUM(p.preco) > 10", Object[].class);
        List<Object[]> resultado = query.getResultList();

        resultado.forEach(r -> logger.info(r[0] + " - " + r[1]));

                assertEquals(2, resultado.size());
    }

    //inner join
    @Test
    public void ProdutosPorLoja(){
        logger.info("Executando ProdutosPorLoja()");
        TypedQuery<Object[]> query = em.createQuery("SELECT l.nome, p.nome FROM Loja l INNER JOIN l.produtos p", Object[].class);
        List<Object[]> resultado = query.getResultList();

        resultado.forEach(r -> logger.info(r[0] + " - " + r[1]));

                assertEquals(13, resultado.size());
    }

    //left join
    @Test
    public void ProdutosPorLojaComLeftJoin(){
        logger.info("Executando ProdutosPorLojaComLeftJoin()");
        TypedQuery<Object[]> query = em.createQuery("SELECT l.nome, p.nome FROM Loja l LEFT JOIN l.produtos p", Object[].class);
        List<Object[]> resultado = query.getResultList();

        resultado.forEach(r -> logger.info(r[0] + " - " + r[1]));

                assertEquals(15, resultado.size());
    }

   //join fetch
    @Test
    public void ProdutosPorLojaComJoinFetch(){
        logger.info("Executando ProdutosPorLojaComJoinFetch()");
        TypedQuery<Loja> query = em.createQuery("SELECT DISTINCT l FROM Loja l JOIN FETCH l.produtos", Loja.class);
        List<Loja> lojas = query.getResultList();

        lojas.forEach(l -> logger.info(l.getNome() + " - " + l.getProdutos().size()));

                assertEquals(2, lojas.size());
    }

}
