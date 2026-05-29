package main;

import java.security.Identity;

public class MapaDispersao<T> {
    private ListaEncadeada<NoMapa<T>> info[];
    
	@SuppressWarnings("unchecked")
	public MapaDispersao(ListaEncadeada<NoMapa<T>>[] info, int tamanho) {
		
		this.info = (ListaEncadeada<NoMapa<T>> []) new ListaEncadeada[tamanho];
	}
	
	private int calculaHash(int chave) {
		int tamanho = info.length ;
		
		return chave % tamanho;
	}
	
	public void inserir(int chave, int valor) {
		int idx = calculaHash(chave);
		
		if  (info[idx] == null) {
			info[idx] = new ListaEncadeada<NoMapa<T>>();
		}
		
	    NoMapa no = new NoMapa<Integer>(chave, valor);
		
		info[idx].inserir(no);
		
	}
	
	public T buscar(int chave) {
		int idx = calculaHash(chave);
		
		if  (info[idx] != null) {
			
			NoMapa NoMapa = new NoMapa(chave, null);
			T no = info[idx].buscar(no);
			
		}
	}
	
}
