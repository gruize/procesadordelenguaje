package tablaSimbolos;

import java.util.*;

public class TS {

	private Hashtable<String, ObjTS> tabla;
	
	public TS() {
		// TODO Auto-generated constructor stub
		tabla = new Hashtable<String, ObjTS>();
	}
	
	public void añadeId(String id, String tipo, int dir) {
		//No comprobamos la existencia del id porque eso se hará en el código
		//del sintáctico
		tabla.put(id, new ObjTS(tipo, dir));
	}
	
	public boolean existeId(String id) {
		if (tabla.containsKey(id))
			return true;
		else
			return false;
	}

	public Hashtable<String, ObjTS> getTabla() {
		return tabla;
	}

	public void setTabla(Hashtable<String, ObjTS> tabla) {
		this.tabla = tabla;
	}
}
