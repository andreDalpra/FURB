package test;

import static main.furb.app.Sistema.abrePrograma;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.furb.controle.UsuarioDAO;
import main.furb.entidades.Usuario;
import main.furb.enums.TipoUsuario;

public class TestUsuario {
	
	private Usuario usuario;

	@BeforeEach
	void CriandoUsu() {
		usuario = new Usuario();
		usuario.setCodusu("CLEITON");
		usuario.setSenusu(2589);
		usuario.setNomusu("cleiton rasta");
		usuario.setEmlusu("joao@email.com");
		usuario.setTipusu(TipoUsuario.ADM);
	}

	// -------------------------------------------------------------
	// TESTE DO validaSenha()
	// -------------------------------------------------------------
	@Test
	void testValidaSenhaValida() {
		usuario.setSenusu(2589);
		assertTrue(usuario.validaSenha());
	}

	@Test
	void testValidaSenhaMuitoPequena() {
		usuario.setSenusu(999);
		assertFalse(usuario.validaSenha());
	}

	@Test
	void testValidaSenhaMuitoGrande() {
		usuario.setSenusu(10000);
		assertFalse(usuario.validaSenha());
	}

	@Test
	void testValidaSenhaTodosIguais() {
		usuario.setSenusu(1111);
		assertFalse(usuario.validaSenha());
	}

	@Test
	void testValidaSenhaSequencialCrescente() {
		usuario.setSenusu(1234);
		assertFalse(usuario.validaSenha());
	}

	@Test
	void testValidaSenhaSequencialDecrescente() {
		usuario.setSenusu(4321);
		assertFalse(usuario.validaSenha());
	}

	// -------------------------------------------------------------
	// TESTE DO valida()
	// -------------------------------------------------------------
	@Test
	void testValidaUsuarioValido() {
		assertTrue(usuario.valida());
	}

	@Test
	void testValidaCodVazio() {
		usuario.setCodusu("");
		assertFalse(usuario.valida());
	}

	@Test
	void testValidaSenhaInvalida() {
		usuario.setSenusu(1111);
		assertFalse(usuario.valida());
	}

	// -------------------------------------------------------------
	// TESTE DO before_delete()
	// -------------------------------------------------------------
	@Test
	void testBeforeDeleteNormal() {
		usuario.setTipusu(TipoUsuario.NORMAL);
		assertTrue(usuario.before_delete());
	}

	@Test
	void testBeforeDeleteEstagio() {
		usuario.setTipusu(TipoUsuario.ESTAGIO);
		assertTrue(usuario.before_delete());
	}

	@Test
	void testBeforeDeleteAdmNaoPode() {
		usuario.setTipusu(TipoUsuario.ADM);
		assertFalse(usuario.before_delete());
	}

	// -------------------------------------------------------------
	// TESTE DO toCSV()
	// -------------------------------------------------------------
	@Test
	void testToCSV() {
		usuario.setSequsu(10);
		String esperado = "10;CLEITON;2589;cleiton rasta;joao@email.com;ADM";
		assertEquals(esperado, usuario.toCSV());
	}

	// -------------------------------------------------------------
	// TESTE DO fromCSV()
	// -------------------------------------------------------------
	@Test
	void testFromCSV() {
		String linha = "5;MARIA;4567;Maria José;maria@email.com;ADM";

		Usuario u = new Usuario();
		u.fromCSV(linha);

		assertEquals(5, u.getSequsu());
		assertEquals("MARIA", u.getCodusu());
		assertEquals(4567, u.getSenusu());
		assertEquals("Maria José", u.getNomusu());
		assertEquals("maria@email.com", u.getEmlusu());
		assertEquals(TipoUsuario.ADM, u.getTipusu());
	}

	// ------------------------------------------------------------
	// TESTE DO MÉTODO inserir()
	// ------------------------------------------------------------
	@Test
	public void testInserirUsuario() {
		abrePrograma();

		Usuario u = new Usuario();
		u.setSequsu(1008);
		u.setCodusu("test_usuario");
		u.setNomusu("Usuário Teste");
		u.setSenusu(2589);
		u.setEmlusu("teste@teste.com");
		u.setTipusu(TipoUsuario.ADM);

		UsuarioDAO dao = new UsuarioDAO();

		// Testando UsuarioDAO.inserir()
		boolean inseriu = dao.inserir(u);

		assertTrue(inseriu);
		assertEquals(1008, u.getSequsu());
		assertEquals("test_usuario", u.getCodusu());
		assertEquals("Usuário Teste", u.getNomusu());
		assertEquals(2589, u.getSenusu());
	}

	// ------------------------------------------------------------
	// TESTE DO MÉTODO obtemPelaSequence()
	// ------------------------------------------------------------
	@Test
	public void testObtemPelaSequence() {
		abrePrograma();

		// preparando registro para buscar
		Usuario u = new Usuario();
		u.setSequsu(2002);
		u.setCodusu("BUSCA_TESTE");
		u.setNomusu("Teste Buscar");
		u.setSenusu(1000);
		u.setTipusu(TipoUsuario.ADM);

		UsuarioDAO dao = new UsuarioDAO();
		dao.inserir(u); // insere antes de buscar

		// Testando UsuarioDAO.obtemPelaSequence()
		Usuario buscado = UsuarioDAO.obtemPelaSequence(2002);

		assertNotNull(buscado);
		assertEquals("BUSCA_TESTE", buscado.getCodusu());
		assertEquals(2002, buscado.getSequsu());
	}

	// ------------------------------------------------------------
	// TESTE DO MÉTODO excluir()
	// ------------------------------------------------------------
	@Test
	public void testExcluirUsuario() {
		abrePrograma();

		Usuario u = new Usuario();
		u.setSequsu(3003);
		u.setCodusu("DEL_TEST");
		u.setNomusu("Excluir Teste");
		u.setSenusu(5639);
		u.setTipusu(TipoUsuario.NORMAL);
		UsuarioDAO dao = new UsuarioDAO();
		dao.inserir(u);

		// Testando UsuarioDAO.excluir()
		boolean excluiu = dao.excluir(u);

		assertTrue(excluiu);

		// Confirma que não existe mais
		Usuario buscado = UsuarioDAO.obtemPelaSequence(3003);
		assertNull(buscado);
	}

	@Test
	public void testExcluirUsuarioEstagio() {
		abrePrograma();

		Usuario u = new Usuario();
		u.setSequsu(3004);
		u.setCodusu("DEL_TEST_ESTAGIO");
		u.setNomusu("Excluir Teste Estagio");
		u.setSenusu(5639);
		u.setTipusu(TipoUsuario.ESTAGIO);
		UsuarioDAO dao = new UsuarioDAO();
		dao.inserir(u);

		// Testando UsuarioDAO.excluir()
		boolean excluiu = dao.excluir(u);

		assertTrue(excluiu);

		// Confirma que não existe mais
		Usuario buscado = UsuarioDAO.obtemPelaSequence(3004);
		assertNull(buscado);

		// TEM Q DAR FALSE , POIS NAO É POSSIVEL EXCLUIR UM ADM..
	}
	@Test
	public void testExcluirUsuarioADM() {
		abrePrograma();

		Usuario u = new Usuario();
		u.setSequsu(3005);
		u.setCodusu("DEL_TEST_ADM");
		u.setNomusu("Excluir Teste ADM");
		u.setSenusu(5639);
		u.setTipusu(TipoUsuario.ADM);
		UsuarioDAO dao = new UsuarioDAO();
		dao.inserir(u);

		// Testando UsuarioDAO.excluir()
		boolean excluiu = dao.excluir(u);

		assertFalse(excluiu);

		// Confirma que não existe mais
		Usuario buscado = UsuarioDAO.obtemPelaSequence(3005);
		assertNotNull(buscado);
	}

	// ------------------------------------------------------------
	// TESTE DO MÉTODO validaLogin()
	// ------------------------------------------------------------
	@Test
	public void testValidaLogin() {
		abrePrograma();

		Usuario u = new Usuario();
		u.setSequsu(4004);
		u.setCodusu("LOGIN_TEST");
		u.setNomusu("Login Teste");
		u.setSenusu(1334);
		u.setTipusu(TipoUsuario.ADM);

		UsuarioDAO dao = new UsuarioDAO();
		dao.inserir(u);

		Usuario tentativa = new Usuario();
		tentativa.setCodusu("LOGIN_TEST");
		tentativa.setSenusu(1334);

		// Testando UsuarioDAO.validaLogin()

		Usuario logou = dao.validaLogin(tentativa);

		assertNotNull(logou);
	}

	// ------------------------------------------------------------
	// VALIDAÇÃO LOGIN - FALHA (senha errada)
	// ------------------------------------------------------------
	@Test
	public void testValidaLoginFalho() {
		abrePrograma();

		Usuario u = new Usuario();
		u.setSequsu(5005);
		u.setCodusu("LOGIN_FAIL");
		u.setNomusu("Teste Falha Login");
		u.setSenusu(5544);
		u.setTipusu(TipoUsuario.ADM);
		
		UsuarioDAO dao = new UsuarioDAO();
		dao.inserir(u);

		Usuario tentativa = new Usuario();
		tentativa.setCodusu("LOGIN_FAIL");
		tentativa.setSenusu(5533);

		// Testando UsuarioDAO.validaLogin()
		Usuario logou = dao.validaLogin(tentativa);
		assertNull(logou);
	}

}
