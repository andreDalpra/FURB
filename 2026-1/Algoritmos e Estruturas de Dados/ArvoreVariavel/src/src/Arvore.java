package src;

public class Arvore<T> {
	
    private NoArvore<T> raiz;

    public Arvore() {
  		this.raiz = null;
  	  }
      
    public boolean pertence(T info) {
        if  (estaVazia()) {
    	    return false;
    	}
    	  
    	return pertence(raiz, info);
    }
      
    private boolean pertence(NoArvore<T> no, T info) {
        if  (no.getInfo() == info){
    	    return true;
    	}
    	else {
    		NoArvore<T> p = null;
    		p = no.getPrimeiro();
    		while (p != null) {
    		    if  (pertence(p, info)){
    			    return true;
    			}
    			p = p.getProximo();
    		}
    		return false;
    	}
    }
    
    public int contaNos() {
        if (estaVazia()) {
            return 0;
        }

        return contaNos(raiz);
    }

    private int contaNos(NoArvore<T> no) {
        if (no == null) {
            return 0;
        }

        int tamanho = 1;

        NoArvore<T> p = no.getPrimeiro();

        while (p != null) {
            tamanho += contaNos(p);
            p = p.getProximo();
        }
        return tamanho;
    }
      
    
    public boolean estaVazia() {
    	if  (raiz == null) {
    	    return true; 
        }
    	return false;
    }

    
	@Override
	public String toString() {
		if (estaVazia()) {
			return "";
		}
		return obterRepresentacaoTextual(raiz);
	}
	
	public String obterRepresentacaoTextual(NoArvore<T> no) {
		NoArvore<T> p;
		String s = "<";
		
		s = s + no.getInfo();
		p = no.getPrimeiro();
		while (p != null) {
			s = s + obterRepresentacaoTextual(p);
			p = p.getProximo();
		}
		s = s + ">";
		
		return s;
	}

	public NoArvore<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(NoArvore<T> raiz) {
		this.raiz = raiz;
    }
}
