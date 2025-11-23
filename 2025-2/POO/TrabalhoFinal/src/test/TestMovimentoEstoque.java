package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static main.furb.app.Sistema.abrePrograma;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.furb.controle.MovimentoDAO;
import main.furb.controle.ProdutoDAO;
import main.furb.controle.UsuarioDAO;
import main.furb.entidades.MovimentoEstoque;
import main.furb.entidades.Produto;
import main.furb.entidades.Usuario;
import main.furb.enums.TipoMovimento;
import main.furb.enums.TipoProduto;
import main.furb.enums.TipoUsuario;

public class TestMovimentoEstoque {

    private Produto produto;
    private Usuario usuario;

    @BeforeEach
    void setup() {

    	abrePrograma();
        // Criar produto
        produto = new Produto();
        produto.setSeqpro(47);
        produto.setCodpro("PROD_TEST_MOVIMENTO");
        produto.setDespro("Produto Teste Movimento");
        produto.setPrrunt(20);
        produto.setQtdproduto(10);
        produto.setTipro(TipoProduto.PERIFÉRICOS);
        ProdutoDAO daoP = new ProdutoDAO();
        daoP.inserir(produto);

        // Criar usuário
        usuario = new Usuario();
        usuario.setSequsu(44);
        usuario.setCodusu("USER_TEST_MOVIMENTO");
        usuario.setNomusu("Usuário Teste Movimento");
        usuario.setSenusu(123);
        usuario.setTipusu(TipoUsuario.ADM);
        UsuarioDAO daoU = new UsuarioDAO();
        daoU.inserir(usuario);
    }

    // ----------------------------------------------------------
    // TESTES DO MÉTODO VALIDA()
    // ----------------------------------------------------------

    @Test
    void testValidaProdutoNulo() {
        MovimentoEstoque m = new MovimentoEstoque();

        m.setSequsu(usuario);
        m.setQtdmov(5);
        m.setVlrunt(10);
        m.setTipmov(TipoMovimento.ENTRADA);

        assertFalse(m.valida());
    }

    @Test
    void testValidaUsuarioNulo() {
        MovimentoEstoque m = new MovimentoEstoque();

        m.setSeqpro(produto);
        m.setQtdmov(5);
        m.setVlrunt(10);
        m.setTipmov(TipoMovimento.ENTRADA);

        assertFalse(m.valida());
    }

    @Test
    void testValidaQuantidadeInvalida() {
        MovimentoEstoque m = new MovimentoEstoque();

        m.setSeqpro(produto);
        m.setSequsu(usuario);
        m.setQtdmov(0);
        m.setVlrunt(10);
        m.setTipmov(TipoMovimento.ENTRADA);

        assertFalse(m.valida());
    }

    @Test
    void testValidaValorUnitarioInvalido() {
        MovimentoEstoque m = new MovimentoEstoque();

        m.setSeqpro(produto);
        m.setSequsu(usuario);
        m.setQtdmov(3);
        m.setVlrunt(0);
        m.setTipmov(TipoMovimento.ENTRADA);

        assertFalse(m.valida());
    }

    @Test
    void testValidaSaidaComEstoqueInsuficiente() {
        MovimentoEstoque m = new MovimentoEstoque();

        m.setSeqpro(produto);
        m.setSequsu(usuario);
        m.setQtdmov(999); // maior que estoque
        m.setVlrunt(10);
        m.setTipmov(TipoMovimento.SAIDA);

        assertFalse(m.valida());
    }


    // ----------------------------------------------------------
    // TESTES DO DAO - INSERÇÃO
    // ----------------------------------------------------------

    @Test
    void testInserirMovimentoEntradaAumentaEstoque() {
        MovimentoDAO dao = new MovimentoDAO();

        MovimentoEstoque mov = new MovimentoEstoque();
        mov.setSeqpro(produto);
        mov.setSequsu(usuario);
        mov.setQtdmov(5); // entrada
        mov.setVlrunt(20);
        mov.setVlrtot(100);
        mov.setTipmov(TipoMovimento.ENTRADA);
        mov.setDatmov(LocalDate.now());

        boolean ok = dao.inserir(mov);
        assertTrue(ok);

        Produto atualizado = ProdutoDAO.obtemPelaSequence(produto.getSeqpro());
        assertEquals(15, atualizado.getQtdproduto()); // 10 + 5
    }

    @Test
    void testInserirMovimentoSaidaReduzEstoque() {
        MovimentoDAO dao = new MovimentoDAO();

        MovimentoEstoque mov = new MovimentoEstoque();
        mov.setSeqpro(produto);
        mov.setSequsu(usuario);
        mov.setQtdmov(3); 
        mov.setVlrunt(20);
        mov.setVlrtot(60);
        mov.setTipmov(TipoMovimento.SAIDA);
        mov.setDatmov(LocalDate.now());

        boolean ok = dao.inserir(mov);
        assertTrue(ok);

        Produto atualizado = ProdutoDAO.obtemPelaSequence(produto.getSeqpro());
        assertEquals(7, atualizado.getQtdproduto()); // 10 - 3
    }

    @Test
    void testInserirSaidaMaiorQueEstoqueNaoDeixaNegativo() {
        MovimentoDAO dao = new MovimentoDAO();

        MovimentoEstoque mov = new MovimentoEstoque();
        mov.setSeqpro(produto);
        mov.setSequsu(usuario);
        mov.setQtdmov(999); 
        mov.setVlrunt(20);
        mov.setVlrtot(999 * 20);
        mov.setTipmov(TipoMovimento.SAIDA);
        mov.setDatmov(LocalDate.now());

        // valida() deve impedir
        assertFalse(mov.valida());
    }

    @Test
    void testInserirCalculaValorTotal() {
        MovimentoDAO dao = new MovimentoDAO();

        MovimentoEstoque mov = new MovimentoEstoque();
        mov.setSeqpro(produto);
        mov.setSequsu(usuario);
        mov.setQtdmov(2);
        mov.setVlrunt(50);
        mov.setVlrtot(100); // manual
        mov.setTipmov(TipoMovimento.ENTRADA);
        mov.setDatmov(LocalDate.now());

        boolean ok = dao.inserir(mov);
        assertTrue(ok);

        MovimentoEstoque buscado = MovimentoDAO.obtemPelaSequence(mov.getSeqmov());
        assertEquals(100, buscado.getVlrtot());
    }

    // ----------------------------------------------------------
    // TESTE OBTER POR SEQ
    // ----------------------------------------------------------

    @Test
    void testObtemPelaSequence() {
        MovimentoDAO dao = new MovimentoDAO();

        MovimentoEstoque mov = new MovimentoEstoque();
        mov.setSeqpro(produto);
        mov.setSequsu(usuario);
        mov.setQtdmov(2);
        mov.setVlrunt(10);
        mov.setVlrtot(20);
        mov.setTipmov(TipoMovimento.ENTRADA);
        mov.setDatmov(LocalDate.now());

        dao.inserir(mov);

        MovimentoEstoque buscado = MovimentoDAO.obtemPelaSequence(mov.getSeqmov());
        assertNotNull(buscado);
        assertEquals(mov.getSeqmov(), buscado.getSeqmov());
    }

  
}

