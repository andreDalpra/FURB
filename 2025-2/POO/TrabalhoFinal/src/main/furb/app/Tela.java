package main.furb.app;

import main.furb.entidades.Usuario;

public interface Tela<T> {
	T carrega_no_objeto();

	void carrega_do_objeto(T p_obj);
	
	default void carrega_login(Usuario p_usuario) {
		
	}
}
