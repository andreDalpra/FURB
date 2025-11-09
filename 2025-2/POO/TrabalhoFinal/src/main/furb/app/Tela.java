package main.furb.app;

public interface Tela<T> {
	T carrega_no_objeto();

	void carrega_do_objeto(T p_obj);
}
