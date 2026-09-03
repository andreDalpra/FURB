package funcionalidades;

import java.util.ArrayList;
import java.util.List;

import compilador.lexico.Constants;
import compilador.lexico.LexicalError;
import compilador.lexico.Lexico;
import compilador.lexico.Token;

public class LexicoCompiler {
    private String codigo;

	public LexicoCompiler(String codigo) {
		this.codigo = codigo;
	}
	
	public void compilar() {
		Lexico lexico = new Lexico();
		lexico.setInput(codigo);
		try {
		    Token t = null;
		    while ( (t = lexico.nextToken()) != null ) {
		        System.out.println(t.getLexeme()); 
		     
		        // só escreve o lexema, necessário escrever t.getId, t.getPosition()
		    
		        // t.getId () - retorna o identificador da classe (ver Constants.java) 
		        // necessário adaptar, pois deve ser apresentada a classe por extenso
		     
		        // t.getPosition () - retorna a posição inicial do lexema no editor 
		        // necessário adaptar para mostrar a linha	

		        // esse código apresenta os tokens enquanto não ocorrer erro
		        // no entanto, os tokens devem ser apresentados SÓ se não ocorrer erro,
		        // necessário adaptar para atender o que foi solicitado		   
		    }
		}
		catch ( LexicalError e ) {  // tratamento de erros
		    System.out.println(e.getMessage() + " em " + e.getPosition());
		 
		    // e.getMessage() - retorna a mensagem de erro de SCANNER_ERRO (ver ScannerConstants.java)
		    // necessário adaptar conforme o enunciado da parte 2
		    // e.getPosition() - retorna a posição inicial do erro 
		    // necessário adaptar para mostrar a linha  
		} 
	}
}
