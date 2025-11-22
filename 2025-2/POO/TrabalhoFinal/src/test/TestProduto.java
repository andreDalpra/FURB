package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.furb.banco.Banco;
import main.furb.controle.ProdutoDAO;
import main.furb.entidades.Produto;
import main.furb.enums.TipoProduto;

public class TestProduto {

    Produto produto;

    @BeforeEach
    void setup() {
     
        produto = new Produto();
        produto.setSeqpro(1);
        produto.setCodpro("PROD001");
        produto.setDespro("Produto Teste");
        produto.setPrrunt(50.0);
        produto.setQtdproduto(5);
        produto.setTipro(TipoProduto.PERIFÉRICOS);
    }

    // ------------------------------------
    // TESTE valida()
    // ------------------------------------
    @Test
    void testValidaOK() {
        assertTrue(produto.valida());
    }

    @Test
    void testValidaSemCodigo() {
        produto.setCodpro("");
        assertFalse(produto.valida());
    }

    @Test
    void testValidaSemTipo() {
        produto.setTipro(null);
        assertFalse(produto.valida());
    }

    @Test
    void testValidaPrecoZero() {
        produto.setPrrunt(0);
        assertFalse(produto.valida());
    }

    @Test
    void testValidaQtdZero() {
        produto.setQtdproduto(0);
        assertFalse(produto.valida());
    }

    // ------------------------------------
    // TESTE before_post()
    // ------------------------------------
    @Test
    void testBeforePostOK() {
        assertTrue(produto.before_post());
    }

    @Test
    void testBeforePostDuplicado() {
        // Salva o primeiro
        Banco.insert(produto, Produto.class);

        // Segundo produto com MESMO CODIGO
        Produto p2 = new Produto();
        p2.setCodpro("PROD001");
        p2.setDespro("Outro");
        p2.setQtdproduto(3);
        p2.setPrrunt(20);
        p2.setTipro(TipoProduto.ACESSÓRIOS);

        assertFalse(p2.before_post());
    }

    // ------------------------------------
    // TESTE CSV
    // ------------------------------------
    @Test
    void testToCsvAndFromCsv() {
        String csv = produto.toCSV();

        Produto copia = new Produto();
        copia.fromCSV(csv);

        assertEquals(produto.getSeqpro(), copia.getSeqpro());
        assertEquals(produto.getCodpro(), copia.getCodpro());
        assertEquals(produto.getDespro(), copia.getDespro());
        assertEquals(produto.getPrrunt(), copia.getPrrunt());
        assertEquals(produto.getQtdproduto(), copia.getQtdproduto());
        assertEquals(produto.getTipro(), copia.getTipro());
    }
    
 // ------------------------------------
 // TESTES DO DAO ProdutoDAO
 // ------------------------------------

    //TESTANDO O DAO, OBTEMPELASEQUENCE();
 @Test
 void testDaoInserirOK() {
     // Cria o DAO
     ProdutoDAO dao = new ProdutoDAO();

     // Tenta inserir o produto configurado no @BeforeEach
     boolean inseriu = dao.inserir(produto);

     // Deve retornar TRUE porque está tudo válido
     assertTrue(inseriu);

     // Busca para garantir que realmente foi salvo
     Produto buscado = dao.obtemPelaSequence(1);
     assertEquals(1, buscado.getSeqpro());
 }

 @Test
 void testDaoInserirDuplicado() {
     ProdutoDAO dao = new ProdutoDAO();

     // Primeiro insere normalmente
     dao.inserir(produto);

     // Cria outro produto com o MESMO código → deve falhar
     Produto p2 = new Produto();
     p2.setCodpro("PROD001"); // duplicado
     p2.setDespro("Outro");
     p2.setTipro(TipoProduto.ACESSÓRIOS);
     p2.setPrrunt(33);
     p2.setQtdproduto(1);

     // Inserção deve retornar FALSE
     boolean inseriu = dao.inserir(p2);

     assertFalse(inseriu);
 }

 @Test
 void testDaoAlterar() {
     ProdutoDAO dao = new ProdutoDAO();

     // Primeiro insere
     dao.inserir(produto);

     // Altera o nome do produto
     produto.setDespro("Produto Alterado");

     // Chama o método alterar()
     boolean alterou = dao.alterar(produto);

     // Tem que dar TRUE se o produto existe
     assertTrue(alterou);

     // Busca de novo para ver se alterou de verdade
   //  Produto buscado = dao.buscar("PROD001");
    // assertEquals("Produto Alterado", buscado.getDespro());
 }

 @Test
 void testDaoExcluir() {
     ProdutoDAO dao = new ProdutoDAO();

     // Insere primeiro
     dao.inserir(produto);

     // Exclui
   //  boolean excluiu = dao.excluir(produto);

     // Exclusão deve retornar TRUE
    // assertTrue(excluiu);

     // Busca novamente → deve vir null
    // Produto buscado = dao.buscar("PROD001");
     //assertEquals(null, buscado);
 }

 @Test
 void testDaoListar() {
     ProdutoDAO dao = new ProdutoDAO();

     // Insere o primeiro produto
     dao.inserir(produto);

     // Cria outro produto e insere
     Produto p2 = new Produto();
     p2.setSeqpro(2);
     p2.setCodpro("PROD002");
     p2.setDespro("Outro");
     p2.setTipro(TipoProduto.ACESSÓRIOS);
     p2.setPrrunt(99);
     p2.setQtdproduto(2);
     dao.inserir(p2);

     // Lista tudo
    // var lista = dao.listar();

     // Tem que ter exatamente os 2 produtos salvos
   //  assertEquals(2, lista.size());
 }

    
    
}