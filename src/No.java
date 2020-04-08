
public class No<T> {
	
	Ponto coordenada;
	T dado;
	
	
	public No(Ponto coordenada, T data) {
		
		this.coordenada = coordenada;
		this.dado = data;
	}
	
	public No(int x, int y, T data) {
		
		this.coordenada = new Ponto(x, y);
		this.dado = data;
	}
	
	public No() {
	
		this.dado = null;
		this.coordenada = new Ponto(0, 0);
	}
	
	public No(T value) {
		
		this.dado = value;
		this.coordenada = new Ponto(0, 0);
	}

}
