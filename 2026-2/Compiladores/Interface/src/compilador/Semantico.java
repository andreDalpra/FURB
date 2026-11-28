package compilador;

import java.util.Stack;
import java.util.ArrayList;

public class Semantico implements Constants {
    
   //registros semanticos
   private StringBuilder  codigoObjeto = new StringBuilder();
   private Stack<String>  pilhaTipos = new Stack<>();
   private Stack<String> pilhaRotulos = new Stack<>();
   private int contadorRotulos = 0;
   private String operador;
   private String identificador = "";
   private ArrayList<Simbolo> tabelaSimbolos = new ArrayList<>();

   private static class Simbolo {
      private String identificador;
      private int tamanho;
      private boolean utilizado;

      private Simbolo(String identificador, int tamanho) {
         this.identificador = identificador;
         this.tamanho = tamanho;
      }
   }

   public void executeAction(int action, Token token) throws SemanticError {
      System.out.println("Acao #"+action+", Token: "+token);

      switch (action) {
         case 101: acao101(); break;
         case 102: acao102(token); break;
         case 103: acao103(token); break;
         case 104: acao104(); break;
         case 105: acao105(token); break;
         case 106: acao106(); break;
         case 107: acao107(); break;
         case 108: acao108(); break;
         case 109: acao109(); break;
         case 110: acao110(token); break;
         case 111: acao111(); break;
         case 112: acao112(); break;
         case 113: acao113(); break;
         case 114: acao114(token); break;
         case 115: acao115(); break;
         case 116: acao116(); break;
         case 117: acao117(); break;
         case 118: acao118(); break;
         case 119: acao119(); break;
         case 120: acao120(); break;
         case 121: acao121(token); break;
         case 122: acao122(); break;
         case 123: acao123(); break;
         case 124: acao124(); break;
         case 125: acao125(); break;
         case 126: acao126(); break;
         case 127: acao127(token); break;
         case 128: acao128(token); break;
         case 129: acao129(token); break;
         case 130: acao130(token); break;
         case 131: acao131(token); break;
         case 132: acao132(); break;
         case 133: acao133(); break;
         case 134: acao134(); break;
         default:  throw new SemanticError("Acao semantica nao implementada: " + action);
      }
   }

   private void acao101() {
      codigoObjeto.append(".assembly extern mscorlib {}\n");
      codigoObjeto.append(".assembly _codigo_objeto{}\n");
      codigoObjeto.append(".module _codigo_objeto.exe\n");
      codigoObjeto.append("\n");
      codigoObjeto.append(".class public _codigo_objeto{\n");
      codigoObjeto.append(".method static public void _principal(){\n");
      codigoObjeto.append(".entrypoint\n");
}

   private void acao102(Token token) throws SemanticError {
      // Verificacao obrigatoria: apenas atribuicao no destino ou entrada contam como uso.
      for (Simbolo simbolo : tabelaSimbolos) {
         if (!simbolo.utilizado) {
            throw new SemanticError(simbolo.identificador + " identificador declarado, n\u00e3o utilizado",
                  token.getPosition());
         }
      }
      codigoObjeto.append("ret\n");
      codigoObjeto.append("}\n");
      codigoObjeto.append("}");
   }

   private void acao103(Token token) {
      identificador = token.getLexeme();
   }

   private void acao104() {
      tabelaSimbolos.add(new Simbolo(identificador, 0));
      String tipo = tipoIdentificador(identificador);
      codigoObjeto.append(".locals(" + tipo + " " + identificador + ")\n");
   }

   private boolean ehVetor(String nome) {
      for (Simbolo simbolo : tabelaSimbolos) {
         if (simbolo.identificador.equals(nome)) {
            return simbolo.tamanho > 0;
         }
      }
      return false;
   }

   private void acao105(Token token) {
      // Essa ação semantica fiz com IA, nao entendi array na linguagem ????
      int tamanho = Integer.parseInt(token.getLexeme());
      tabelaSimbolos.add(new Simbolo(identificador, tamanho));
      String tipo = tipoIdentificador(identificador);
      String classe;
      switch (tipo) {
         case "int64": classe = "System.Int64"; break;
         case "float64": classe = "System.Double"; break;
         case "string": classe = "System.String"; break;
         default: classe = "System.Boolean"; break;
      }
      codigoObjeto.append(".locals(" + tipo + "[] " + identificador + ")\n");
      codigoObjeto.append("ldc.i4 " + tamanho + "\n");
      codigoObjeto.append("newarr [mscorlib]" + classe + "\n");
      codigoObjeto.append("stloc " + identificador + "\n");
   }

   private void acao107() {
      // A referencia deve ficar abaixo do indice e do valor na pilha IL.
      if (ehVetor(identificador)) {
         codigoObjeto.append("ldloc " + identificador + "\n");
      }
   }

   private void acao134() {
      pilhaTipos.pop();
      codigoObjeto.append("conv.i4\n");
   }

   private String instrucaoElemento(String tipo, boolean armazenar) {
      String prefixo = armazenar ? "stelem." : "ldelem.";
      switch (tipo) {
         case "int64": return prefixo + "i8";
         case "float64": return prefixo + "r8";
         case "string": return prefixo + "ref";
         default: return prefixo + (armazenar ? "i1" : "u1");
      }
   }

   private void acao133() {
      pilhaTipos.pop(); // Tipo da expressao do indice.
      String tipo = pilhaTipos.peek(); // Tipo do elemento, empilhado em #127.
      codigoObjeto.append("conv.i4\n");
      codigoObjeto.append(instrucaoElemento(tipo, false) + "\n");
      if ("int64".equals(tipo)) {
         codigoObjeto.append("conv.r8\n");
      }
   }

   private String tipoIdentificador(String nome) {
      switch (nome.charAt(0)) {
         case 'i': return "int64";
         case 'f': return "float64";
         case 's': return "string";
         default: return "bool";
      }
   }

   private void acao106() {
      pilhaTipos.pop();
      String tipo = tipoIdentificador(identificador);
      if ("int64".equals(tipo)) {
         codigoObjeto.append("conv.i8\n");
      }
      if (ehVetor(identificador)) {
         codigoObjeto.append(instrucaoElemento(tipo, true) + "\n");
      } else {
         codigoObjeto.append("stloc " + identificador + "\n");
      }
      marcarUtilizado();
   }

   private void acao108() {
      String tipo = tipoIdentificador(identificador);
      codigoObjeto.append("call string [mscorlib]System.Console::ReadLine()\n");
      if ("int64".equals(tipo)) {
         codigoObjeto.append("call int64 [mscorlib]System.Int64::Parse(string)\n");
      } else if ("float64".equals(tipo)) {
         codigoObjeto.append("call float64 [mscorlib]System.Double::Parse(string)\n");
      } else if ("bool".equals(tipo)) {
         codigoObjeto.append("call bool [mscorlib]System.Boolean::Parse(string)\n");
      }
      if (ehVetor(identificador)) {
         codigoObjeto.append(instrucaoElemento(tipo, true) + "\n");
      } else {
         codigoObjeto.append("stloc " + identificador + "\n");
      }
      marcarUtilizado();
   }

   private void marcarUtilizado() {
      for (Simbolo simbolo : tabelaSimbolos) {
         if (simbolo.identificador.equals(identificador)) {
            simbolo.utilizado = true;
            return;
         }
      }
   }

   private void acao127(Token token) {
      String nome = token.getLexeme();
      String tipo = tipoIdentificador(nome);
      pilhaTipos.push(tipo);
      codigoObjeto.append("ldloc " + nome + "\n");
      if ("int64".equals(tipo) && !ehVetor(nome)) {
         codigoObjeto.append("conv.r8\n");
      }
   }

   private void acao109() {
      String tipo = pilhaTipos.pop();
      if ("int64".equals(tipo)) {
         codigoObjeto.append("conv.i8\n");
      }
      codigoObjeto.append("call void [mscorlib]System.Console::Write(" + tipo + ")\n");
    }
   
   private String novoRotulo() {
      return "R" + (++contadorRotulos);
   }

   private void acao110(Token token) {
      pilhaTipos.pop();
      String rotulo = novoRotulo();
      pilhaRotulos.push(rotulo);
      String desvio = "isTrueDo".equals(token.getLexeme()) ? "brfalse" : "brtrue";
      codigoObjeto.append(desvio + " " + rotulo + "\n");
   }

   private void acao111() {
      codigoObjeto.append(pilhaRotulos.pop() + ":\n");
   }

   private void acao112() {
      String rotuloAlternativo = pilhaRotulos.pop();
      String rotuloFim = novoRotulo();
      codigoObjeto.append("br " + rotuloFim + "\n");
      codigoObjeto.append(rotuloAlternativo + ":\n");
      pilhaRotulos.push(rotuloFim);
   }

   private void acao113() {
      String rotuloInicio = novoRotulo();
      pilhaRotulos.push(rotuloInicio);
      codigoObjeto.append(rotuloInicio + ":\n");
   }

   private void acao114(Token token) {
      pilhaTipos.pop();
      String rotuloFim = novoRotulo();
      pilhaRotulos.push(rotuloFim);
      String desvio = "isTrueDo".equals(token.getLexeme()) ? "brfalse" : "brtrue";
      codigoObjeto.append(desvio + " " + rotuloFim + "\n");
   }

   private void acao115() {
      String rotuloFim = pilhaRotulos.pop();
      String rotuloInicio = pilhaRotulos.pop();
      codigoObjeto.append("br " + rotuloInicio + "\n");
      codigoObjeto.append(rotuloFim + ":\n");
   }

   private void acao116(){
	   String tipo1 = pilhaTipos.pop();
	   String tipo2 = pilhaTipos.pop();
	   
	   pilhaTipos.push("bool");
	   codigoObjeto.append("and\n");
   }
   
   private void acao117(){
	   String tipo1 = pilhaTipos.pop();
	   String tipo2 = pilhaTipos.pop();
	   
	   pilhaTipos.push("bool");
	   codigoObjeto.append("or\n");
   }

   private void acao118() {
      pilhaTipos.push("bool");
      codigoObjeto.append("ldc.i4.1\n");
   }
   
   private void acao119() {
	   pilhaTipos.push("bool");
	   codigoObjeto.append("ldc.i4.0\n");
   }

   private void acao120() {
      String tipo = pilhaTipos.pop();
      pilhaTipos.push("bool");
      codigoObjeto.append("ldc.i4.1\n");
      codigoObjeto.append("xor\n");
   }

   private void acao121(Token token) {
	    operador = token.getLexeme();	   
   }
   
   private void acao122() {
      String tipo2 = pilhaTipos.pop();
      String tipo1 = pilhaTipos.pop();

	  pilhaTipos.push("bool");
	  
	  if  ("=".equals(operador)) {
		codigoObjeto.append("ceq\n");  
	  }
	  else if("<>".equals(operador)) {
		  codigoObjeto.append("ceq\n");
		  codigoObjeto.append("ldc.i4.1\n");
		  codigoObjeto.append("xor\n");
	  }
	  else if ("<".equals(operador)) {
		  codigoObjeto.append("clt\n");
	  }
	  else if (">".equals(operador)) {
		  codigoObjeto.append("cgt\n");
	  }
   }
   
   private void acao123() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }
      codigoObjeto.append("add\n");
   }

   private void acao124() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }
      codigoObjeto.append("sub\n");
   }

   private void acao125() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }
      codigoObjeto.append("mul\n");
   }

   private void acao126() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      pilhaTipos.push("float64");
      codigoObjeto.append("div\n");
   }

   private void acao128(Token token) {
     pilhaTipos.push("int64");
     codigoObjeto.append("ldc.i8 " + token.getLexeme() + "\n");
     codigoObjeto.append("conv.r8\n");
   }

   private void acao129(Token token) {
      pilhaTipos.push("float64");
      codigoObjeto.append("ldc.r8 " + token.getLexeme().replace(',', '.') + "\n");
   }

   private void acao130(Token token) {
      pilhaTipos.push("string");
      codigoObjeto.append("ldstr " + token.getLexeme() + "\n");
   }
 
   private void acao131(Token token) throws SemanticError {
      // Verificacao semantica especifica da equipe: compatibilidade do operando do + unario.
      String tipo = pilhaTipos.peek();
      if (!"int64".equals(tipo) && !"float64".equals(tipo)) {
         throw new SemanticError("tipo incompativel em operador aritmetico unario +",
               token.getPosition());
      }
      // O + unario preserva o tipo e o valor do operando.
   }     

   private void acao132() {
      String tipo = pilhaTipos.pop();
      if ("int64".equals(tipo)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }	
      codigoObjeto.append("ldc.i8 -1\n");
      codigoObjeto.append("conv.r8\n");
      codigoObjeto.append("mul\n");
   }
	
   public String getCodigoObjeto() {
      return codigoObjeto.toString();
   }
}
