package tablaSimbolos;

import java.util.*;

//import analizadorSintactico.*;

public class TS {

	private Hashtable<String, ObjTS> tabla;
	
	public TS() {
		// TODO Auto-generated constructor stub
		tabla = new Hashtable<String, ObjTS>();
	}
	
	public TS(Hashtable<String, ObjTS> tabla) {
		// TODO Auto-generated constructor stub
		this.tabla = tabla;
	}
	
	//Se debe hacer un añade para cada clase de objeto de la tabla de símbolos
	/*public void anadeId(String id, tipoT tipo, int dir) {
		//No comprobamos la existencia del id porque eso se hara en el codigo
		//del sintactico
		tabla.put(id, new ObjTS(tipo, dir));
	}*/
	//Se debe hacer un añade para cada clase de objeto de la tabla de símbolos
	public void anadeId(String id, PropsObjTS tipo, int dir, tClase clase, int nivel) {
		//No comprobamos la existencia del id porque eso se hara en el codigo
		//del sintactico
		tabla.put(id, new ObjTS(tipo, dir, clase, nivel));
	}
	
	public boolean existeId(String id) {
		if (tabla.containsKey(id))
			return true;
		else
			return false;
	}
	
	public boolean existeId(String id, tClase clase, int nivel) {
		if (tabla.containsKey(id.toString()))
		//if (tabla.containsKey(id) && tabla.get(id).clase == clase
		//		&& tabla.get(id).getNivel().intValue() == nivel)
			return true;
		else
			return false;
	}
	
	public boolean existeTipo(String tipo) {
		if (tabla.containsKey(tipo) && tabla.get(tipo).clase == tClase.tipo )
			return true;
		else
			return false;
	}
	
	public boolean existeProc(String proc) {
		if (tabla.containsKey(proc)&& tabla.get(proc).clase == tClase.procedimiento )
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
