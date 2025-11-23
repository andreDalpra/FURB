/* TRABALHO FINAL DE POO
 * PROFESSOR : ANDRÉ FELIPE BURGUER
 * 
 * ALUNOS : ANDRÉ LUIZ E JOÃO VITOR MICK
 * 
 * O PROJETO É UM SISTEMA DE MOVIMENTAÇÃO DE ESTOQUE, ONDE É POSSÍVEL 
 * CRIAR USUÁRIO, LOGAR, CADASTRAR PRODUTO, MOVIMENTAR O ESTOQUE DO 
 * PRODUTO, ENTRE OUTRAS FUNCIONALIDADES.
 */

package main.furb.app;

import java.awt.EventQueue;

import ui.Login;
import static main.furb.app.Sistema.abrePrograma;

/**
 * DOCUMENTACAO VIA CHAT GPT      <<<<<<=================
 * 
 * 
 * 
 * 
 * Classe principal responsável por iniciar a aplicação.
 * <p>
 * O método main() é o ponto de entrada do sistema. Ele realiza a 
 * inicialização necessária para o funcionamento do programa e exibe a 
 * tela de login.
 * </p>
 *
 * <p>
 * A inicialização da interface gráfica é executada dentro da 
 * EventQueue para garantir que componentes Swing sejam 
 * manipulados na thread correta.
 * </p>
 *
 * @author 
 *     André Luiz e João Vitor Mick
  
 * @version 1.0
 * 
 */
public class Main {

    /**
     * Método principal da aplicação. Inicializa o sistema e exibe a tela de login.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Inicializa dados essenciais do sistema
                    abrePrograma();

                    // Abre a tela de login
                    Login frame = new Login();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
