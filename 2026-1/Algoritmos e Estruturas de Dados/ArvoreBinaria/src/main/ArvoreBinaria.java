package main;

public class ArvoreBinaria<T> {
	
    private NoArvoreBinaria<T> raiz;

	public ArvoreBinaria() {
		this.raiz = null;
	}
    
	public boolean estaVazia() {
		if (raiz == null) {
			return true;
		}
		return false;
	}
	
	public boolean pertence(T info) {
		
		return pertence(raiz, info);
	}
	
	@SuppressWarnings("unused")
	private boolean pertence(NoArvoreBinaria<T> no, T info) {
	    if (no == null) {
	        return false;
	    } else {
	        return (no.getInfo().equals(info)        
	             || pertence(no.getEsquerda(), info) 
	             || pertence(no.getDireita(), info));
	    }
	}
	
	//USADO PARA LER A ARVORE
	/*
        1
       / \
      2   3
     /   / \
    4   5   6

     Pré-ordem:
     1 → 2 → 4 → 3 → 5 → 6
     
     - Raiz: 1
     - SAE (subárvore à esquerda): 2 → 4
     - SAD (subárvore à direita): 3 → 5 → 6
     
     - CHAMADO DE FORMA RECURSIVA SEMPRE A ESQUERDA DEPOIS A DIREITA METODO PERTENCE É A MESMA COISA
*/
	
	private String arvorePre(NoArvoreBinaria<T> no) {
		if  (estaVazia()){
			return "ARVORE VAZIA";
		}
		
		if  (no == null) {
			return "";
		}
		
		return  "<" + no.getInfo()
             + " <" + arvorePre(no.getEsquerda()) + ">"
             + " <" + arvorePre(no.getDireita())  + ">"
             +  ">";
	}
	
	public int contaNos(NoArvoreBinaria<T> no) {
		int tamanho = 0;
		
		if (no == null) {
			//NAO TEM NADA RETORNA 0
			return 0;
		}
		
		tamanho ++;
		tamanho += contaNos(no.getEsquerda());
		tamanho += contaNos(no.getDireita());
		
		return tamanho;
	}
	
	public int contaNos() {
		return contaNos(raiz); 
	}

	@Override
	public String toString() {
		return arvorePre(raiz);
	}

	public NoArvoreBinaria<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(NoArvoreBinaria<T> raiz) {
		this.raiz = raiz;
	}
	
}
