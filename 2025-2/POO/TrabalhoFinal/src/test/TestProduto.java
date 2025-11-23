package test;

import static main.furb.app.Sistema.abrePrograma;
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
		produto.setSeqpro(169);
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

		Produto p3 = new Produto();
		p3.setCodpro("PROD003");
		p3.setDespro("Outro");
		p3.setQtdproduto(4);
		p3.setPrrunt(26);
		p3.setTipro(TipoProduto.ACESSÓRIOS);

		assertTrue(p3.before_post());
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
	@Test
	void testDaoInserirOK() {
		ProdutoDAO dao = new ProdutoDAO();

		Produto p = new Produto();
		p.setCodpro("PROD_TEST_OK");
		p.setDespro("Produto Teste OK");
		p.setPrrunt(50.0);
		p.setQtdproduto(5);
		p.setTipro(TipoProduto.PERIFÉRICOS);

		boolean inseriu = dao.inserir(p);
		assertTrue(inseriu);

		Produto buscado = ProdutoDAO.obtemPelaSequence(p.getSeqpro());
		assertEquals(p.getSeqpro(), buscado.getSeqpro());
	}

	@Test
	void testDaoInserirDuplicado() {
		ProdutoDAO dao = new ProdutoDAO();

		Produto p1 = new Produto();
		p1.setCodpro("PROD_DUP");
		p1.setDespro("Produto Original");
		p1.setPrrunt(40.0);
		p1.setQtdproduto(3);
		p1.setTipro(TipoProduto.ACESSÓRIOS);

		dao.inserir(p1);

		Produto p2 = new Produto();
		p2.setCodpro("PROD_DUP"); // mesmo código → duplicado
		p2.setDespro("Produto Duplicado");
		p2.setPrrunt(30.0);
		p2.setQtdproduto(2);
		p2.setTipro(TipoProduto.ACESSÓRIOS);

		boolean inseriu = dao.inserir(p2);
		assertFalse(inseriu);
	}

	@Test
	void testDaoAlterar() {
		ProdutoDAO dao = new ProdutoDAO();

		Produto p = new Produto();
		p.setSeqpro(1);
		p.setCodpro("PROD_ALT");
		p.setDespro("Produto Original");
		p.setPrrunt(60.0);
		p.setQtdproduto(4);
		p.setTipro(TipoProduto.PERIFÉRICOS);

		dao.inserir(p);

		p.setDespro("Produto Alterado");
		boolean alterou = dao.alterar(p);
		assertTrue(alterou);

		Produto buscado = ProdutoDAO.obtemPelaSequence(p.getSeqpro());
		assertEquals("Produto Alterado", buscado.getDespro());
	}

	@Test
	void testDaoExcluir() {
		abrePrograma();
		ProdutoDAO dao = new ProdutoDAO();

		Produto p = new Produto();
		p.setCodpro("PROD_DEL");
		p.setDespro("Produto a Excluir");
		p.setPrrunt(20.0);
		p.setQtdproduto(2);
		p.setTipro(TipoProduto.ACESSÓRIOS);

		dao.inserir(p);

		boolean excluiu = dao.excluir(p);
		assertTrue(excluiu);

		Produto buscado = ProdutoDAO.obtemPelaSequence(p.getSeqpro());
		assertEquals(null, buscado);
	}

	@Test
	void testDaoListar() {
		ProdutoDAO dao = new ProdutoDAO();

		Produto p1 = new Produto();
		p1.setCodpro("PROD_LIST1");
		p1.setDespro("Produto 1");
		p1.setPrrunt(10.0);
		p1.setQtdproduto(1);
		p1.setTipro(TipoProduto.PERIFÉRICOS);
		dao.inserir(p1);

		Produto p2 = new Produto();
		p2.setCodpro("PROD_LIST2");
		p2.setDespro("Produto 2");
		p2.setPrrunt(15.0);
		p2.setQtdproduto(2);
		p2.setTipro(TipoProduto.ACESSÓRIOS);
		dao.inserir(p2);

		var lista = Banco.listar(Produto.class);

		boolean contemP1 = lista.stream().anyMatch(p -> p.getSeqpro() == p1.getSeqpro());
		boolean contemP2 = lista.stream().anyMatch(p -> p.getSeqpro() == p2.getSeqpro());

		assertTrue(contemP1);
		assertTrue(contemP2);
	}
}