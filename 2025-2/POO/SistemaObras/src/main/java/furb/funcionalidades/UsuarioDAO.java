package main.java.furb.funcionalidades;

import static main.java.furb.mensagem.Mensagem.*;

import main.java.furb.banco.Banco;
import main.java.furb.entidades.Usuario;

public class UsuarioDAO {

    public boolean inserir(Usuario p_usu) {
        if (!p_usu.valida()) {
            mostrarMensagem();
            return false;
        }

        Banco.salvar(p_usu, Usuario.class);
        montaMensagem(10, new String[]{p_usu.getCodusu()});
        mostrarMensagem();
        return true;
    }

    public void listar() {
        var usuarios = Banco.listar(Usuario.class);
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            usuarios.forEach(u -> System.out.println(u.getSequsu() + " - " + u.getCodusu()));
        }
    }

    public boolean deletar(int p_sequsu) {
        boolean removido = Banco.excluir(Usuario.class, u -> ((Usuario) u).getSequsu() == p_sequsu);

        if (removido) {
            montaMensagem(11, new String[]{String.valueOf(p_sequsu)});
        } else {
            montaMensagem(12, new String[]{String.valueOf(p_sequsu)});
        }
        mostrarMensagem();

        return removido;
    }
}
