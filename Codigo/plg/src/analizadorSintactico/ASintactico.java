package analizadorSintactico;

import java.util.*;

import analizadorLexico.*;
import tablaSimbolos.*;


//OJO!!!!!!!!!!!!!!!!!
//Los getTipo() se corresponden con un atributo de los ObjTS, 
//que s�lo se usa en pruebas para las sentencias de control
public class ASintactico {
	
//	private ALexico scanner;
	private TS ts;
	//Elemento de prean�lisis
	private Token tokActual;
	private Emit emit;
	private Vector<Token> tokensIn;
	private int contTokens;
	//En principio har� un Vector de instrucciones de m�quina a pila como salida
	//del analizador sint�ctico. Se puede cambiar a salida a fichero
	private Vector<String> instMPOut;
	private boolean errorProg;
	//Atributos del 2� Cuat
	
	///////////////////////////////////////////////////////////////////
	//						LISTAS DE PENDIENTES					 //
	///////////////////////////////////////////////////////////////////
	//Lista de pendientes para punteros y el forward
	private Vector<String> listaPendientes;
	//Lista de tipos pendientes que ya han sido declarados pero todav�a
	//no se han a�adido a la TS
	private Vector<ParIdProps> tiposPenTS;
	///////////////////////////////////////////////////////////////////
	//						LISTAS DE PENDIENTES					 //
	///////////////////////////////////////////////////////////////////
	
	
	public ASintactico() {
		// TODO Auto-generated constructor stub
//		scanner = new ALexico();
		ts = new TS();
		tokActual = new Token();
		tokensIn = new Vector<Token>();
		listaPendientes = new Vector<String>();
		tiposPenTS = new Vector<ParIdProps>();
		contTokens = 0;
		instMPOut = new Vector<String>();
		errorProg = false;
		emit = new Emit();
	}
	
	//M�todos de acceso y modificaci�n de la lista de tipos pendientes
	public int existeIdTiposPen(String id) {
		ParIdProps e;
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && (i < tiposPenTS.size())) {
			e = tiposPenTS.get(i);
			encontrado = e.getId().equals(id);
			if (!encontrado)
				i++;
		}
		if (i == tiposPenTS.size())
			return -1;
		else
			return i;
	}
	//Acceder al tama�o de los tipos en la lista de pendientes
	/*public int getTamTiposPen(String id) {

	}*/
	
	/*public Token token() {
		return tokActual;
	}*/
	
	public void emite(String inst) {
		instMPOut.add(inst);
	}
	
	public Token consume(){
//		if (esTokenId(tokActual) || esTokenTipo(tokActual)) {
			Token tokDevolver = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
			return tokDevolver;
//		}
//		else {
//			System.out.println("Error: Se esperaba un identificador o un tipo. ");
////					" o un tipo'" + '\n' +)
////					tokenEsperado.toString() + "'.");
////�Esto ser�a una buena forma de abortar la ejecuci�n?
//			errorProg = true;
//			emite("stop");
//			return new Token();
//		}
	}
	
	public Token consumeId(){
		if (esTokenId(tokActual)) {
			Token tokDevolver = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
			return tokDevolver;
		}
		else {
			System.out.println("Error: Se esperaba un token de tipo 'identificador'.\n" +
					"Token en prean�lisis: " + tokActual.getTipoToken() + "\n");
////�Esto ser�a una buena forma de abortar la ejecuci�n?
			errorProg = true;
			emite("stop");
			emit.emit(Emit.STOP);

			return new Token(tToken.tokenError, "Se esperaba un token de tipo 'identificador'.");
		}
	}
	
	public Token consumeTipo(){
		if (esTokenTipo(tokActual)) {
			Token tokDevolver = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
			return tokDevolver;
		}
		else {
			System.out.println("Error: Se esperaba un token de tipo 'tipoDeVariable'.\n" +
					"Token en prean�lisis: " + tokActual.getTipoToken() + "\n");
////�Esto ser�a una buena forma de abortar la ejecuci�n?
			errorProg = true;
			emite("stop");
			emit.emit(Emit.STOP);

			return new Token(tToken.tokenError, "Se esperaba un token de tipo 'tipoDeVariable'.");
		}
	}
	
	public boolean esTokenTipo(Token t) {
		if (t.getTipoToken() == tToken.tipoVarBooleano ||
				t.getTipoToken() == tToken.tipoVarCaracter ||
				t.getTipoToken() == tToken.tipoVarNatural ||
				t.getTipoToken() == tToken.tipoVarEntero ||
				t.getTipoToken() == tToken.tipoVarReal)
			return true;
		else
			return false;
	}
	
	public boolean esTokenId(Token t) {
		if (t.getTipoToken() == tToken.identificador)
			return true;
		else
			return false;
	}
	
	public void consume(tToken tokenEsperado){
		Token tokConsumido = tokensIn.get(contTokens);
		if (tokConsumido.getTipoToken() == tokenEsperado) {
//Todav�a no se si poner esto alante o atras del if. Como viene en la memoria
//creo que ser� alante
//			tokActual = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
		}
		else {
			System.out.println("Error: Se esperaba token de tipo '" + 
					tokenEsperado.toString() + "'." + "\n");
//�Esto ser�a una buena forma de abortar la ejecuci�n?
			errorProg = true;
			emite("stop");
			emit.emit(Emit.STOP);

		}
	}
	
	public void vaciaCod() {
		instMPOut.clear();
	}
	
	public tipoT dametipoT(tToken tipo) {
		switch (tipo) {
		case tipoVarBooleano:
			return tipoT.tBool;
		case tipoVarCaracter:
			return tipoT.tChar;
		case tipoVarNatural:
			return tipoT.tNat;
		case tipoVarEntero:
			return tipoT.tInt;
		case tipoVarReal:
			return tipoT.tFloat;
		default:
			return tipoT.tError;
		}
	}
	
	//Se usa para copiar nuevas tablas de s�mbolos pertenecientes a los procedimientos
	@SuppressWarnings("unchecked")
	public TS creaTS() {
		return new TS((Hashtable<String, ObjTS>) ts.getTabla().clone());
	}
	
	//Hay que hacer un m�todo que obtenga el array de tokens de entrada
	//y que inicialice el tokenActual, con el primer elemento del array
	//teniendo que cuenta que hay que incrementar el contador de tokens
	//despu�s de asignar el token actual
	public void parse() {
		//Variables para recorrer la tabla de s�mbolos
		String id = new String();
		Enumeration<String> e;
//		tokActual = tokensIn.firstElement();
//		contTokens++;
		
		System.out.println("***********************************************************************");
		System.out.println("*                        AN�LISIS SINT�CTICO                          *");
		System.out.println("***********************************************************************");
		System.out.println();
		
		programa();
		
		System.out.println();
		System.out.println();
		
		if (errorProg) {
			System.out.println("An�lisis fallido.");
		}
		else {
			System.out.println("Tabla de S�mbolos");
			System.out.println("-----------------");
			System.out.println();
			//Mostramos la informaci�n de la tabla de s�mbolos		
			e = ts.getTabla().keys();
			if (e.hasMoreElements()) {
				while (e.hasMoreElements()) {
					id = e.nextElement();
					/*if (ts.getTabla().get(id).getPropiedadesTipo().getT() == tipoT.tChar)
						System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getPropiedadesTipo().getT().toString() + "\tDirecci�n: " +
								ts.getTabla().get(id).getDirM());
					else*/
					//System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getPropiedadesTipo().getT().toString() + "\t\tDirecci�n: " +
					if (id.length() < 4)
						System.out.println("Id: " + id + "\t\t\t" + "Clase: " + ts.getTabla().get(id).getClase() + "\t\tTipo: " + ts.getTabla().get(id).getPropiedadesTipo().getT().toString() + "\t\tDirecci�n: " +
								ts.getTabla().get(id).getDirM());
					if ((id.length() >= 4) && (id.length() < 12))
						System.out.println("Id: " + id + "\t\t" + "Clase: " + ts.getTabla().get(id).getClase() + "\t\tTipo: " + ts.getTabla().get(id).getPropiedadesTipo().getT().toString() + "\t\tDirecci�n: " +
								ts.getTabla().get(id).getDirM());
					if ((id.length() >= 12))
						System.out.println("Id: " + id + "\t" + "Clase: " + ts.getTabla().get(id).getClase() + "\t\tTipo: " + ts.getTabla().get(id).getPropiedadesTipo().getT().toString() + "\t\tDirecci�n: " +
								ts.getTabla().get(id).getDirM());
				}
			}
			else
				System.out.println("Tabla de s�mbolos vac�a.");
			System.out.println();
			System.out.println();
			System.out.println("El an�lisis ha sido satisfactorio.");
			System.out.println();
			System.out.println("Instrucciones para la m�quina a pila generadas");
			System.out.println("----------------------------------------------");
			for (int i = 0; i < instMPOut.size(); i++)
				System.out.println(i +":" + "\t" + instMPOut.get(i));
			System.out.println();
			System.out.println();
			System.out.println("***********************************************************************");
			System.out.println("///////////////////////////////INTERPRETE//////////////////////////////");
			System.out.println("***********************************************************************");
			System.out.println();
			System.out.println();
		}
	}

	/*public void programaFin() { //PROGRAMA_Fin ::= PROGRAMA finDeFichero
		programa();
		consume(tToken.finDeFichero);
		System.out.println("Programa correcto.");
	}*/
	
	public void programa(){ //PROGRAMA ::= DECS & SENTS
		boolean errorDec, errorSent;
		errorProg = errorDec = errorSent = false;
		//////////////////////////////////////////////////////////////////////////
		if (tokActual.getTipoToken() == tToken.separador) {
		//////////////////////////////////////////////////////////////////////////
			//Si no hay declaraciones, devolvemos sin error, y sin a�adir nada a la TS
			consume(tToken.separador);
			//Lanzamos sents() con la etiqueta a 0, ya que es la primera vez que se llama
			errorSent = sents(0).getBooleanVal();
			//La linea de abajo habr�a que cambiarla por esta:
			//errorProg = errorDec || errorSent;
			errorProg = errorProg || errorSent;
			emite("stop");
			emit.emit(Emit.STOP);
		}
		else {
			errorDec = decs(0, 0);
			consume(tToken.separador);
			//Lanzamos sents() con la etiqueta a 0, ya que es la primera vez que se llama
			errorSent = sents(0).getBooleanVal();
			//La linea de abajo habr�a que cambiarla por esta:
			//errorProg = errorDec || errorSent;
			errorProg = errorProg || errorDec || errorSent;
			emite("stop");
			emit.emit(Emit.STOP);
		}
	}
	
	public boolean decs(int nivel, int tam){
		//Declaraci�n de las variables necesarias
		ParBooleanInt errorDec1_dir = new ParBooleanInt();
		ParIdProps id_tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		id_tipo = dec(nivel);
		if (tokActual.getTipoToken() == tToken.puntoyComa) {
			if (id_tipo.getProps() != null)
				errorDec1_dir = rdecs1(nivel, tam + id_tipo.getProps().getTam());
			else
				errorDec1_dir = new ParBooleanInt(true, -1);
		}	
		else
			if (id_tipo.getProps() != null)
				errorDec1_dir = rdecs2(tam + id_tipo.getProps().getTam());
			else
				errorDec1_dir = new ParBooleanInt(true, -1);
		if (errorDec1_dir.getBooleanVal() || ts.existeId(id_tipo.getId())) {
			if (ts.existeId(id_tipo.getId(), tClase.variable, new Integer(0))) {
				System.out.print("Error: El identificador '" + id_tipo.getId() + "' fue declarado previamente" + "\n");
				return true;
			}
			else
				return true;
		}
		else {
			ts.anadeId(id_tipo.getId(), id_tipo.props, tam, id_tipo.getClase(), id_tipo.getNivel());
			// faltan dos emits
			return false;
		}
	}
	
	public ParBooleanInt rdecs1(int nivel, int tam) {
		ParIdProps id_tipo;
		ParBooleanInt errorDec1_dir1 = new ParBooleanInt();
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.puntoyComa);
		id_tipo = dec(nivel);
		if (tokActual.getTipoToken() == tToken.puntoyComa) {
			if (id_tipo.getProps() != null)
				errorDec1_dir1 = rdecs1(nivel, tam + id_tipo.getProps().getTam());
			else 
				errorDec1_dir1 = new ParBooleanInt(true, -1);
		}
		else
			if (id_tipo.getProps() != null)
				errorDec1_dir1 = rdecs2(tam + id_tipo.getProps().getTam());
			else
				errorDec1_dir1 = new ParBooleanInt(true, -1);
		if (errorDec1_dir1.getBooleanVal() || ts.existeId(id_tipo.getId(), tClase.variable, new Integer(0))) {
			if (ts.existeId(id_tipo.getId(), tClase.variable, new Integer(0))) {
				System.out.print("Error: El identificador '" + id_tipo.getId() + "' fue declarado previamente" + "\n");
				return new ParBooleanInt(true, -1);
			}
			else
				return new ParBooleanInt(true, -1);
		}
		else {
			ts.anadeId(id_tipo.getId(), id_tipo.props, tam, id_tipo.getClase(), id_tipo.getNivel());
			return new ParBooleanInt(false, errorDec1_dir1.getIntVal());
		}
	}
	
	//Devolvemos el par: (error = false, dir = 0)
	public ParBooleanInt rdecs2(int tam) {
		//La tabla se crea en el constructor, sino la crear�amos aqu�
		if (errorProg)
			return new ParBooleanInt(true, -1);
		else
			return new ParBooleanInt(false, tam);
	}
	
	public ParIdProps dec(int nivel) {
		//Declaraci�n de las variables necesarias
		
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador) {
			return decVar(nivel);
		}
		if (tokActual.getTipoToken() == tToken.decTipo) {
			return decTipo(nivel);
		}
		if (tokActual.getTipoToken() == tToken.procedure) {
			return decProc(nivel);
		}
		//A�adimos control de errores
		errorProg = true;
		System.out.println("Error: Se esperaba una de las siguientes declaraciones:\n" +
				"	- Variable" + "\n" +
				"	- Tipo	=> 'tipo'" + "\n" +
				"	- Procedimiento	=> 'procedure'" + "\n" +
				"Token en an�lisis: " + tokActual.getTipoToken() + "\n");
		return new ParIdProps(tClase.claseError, 0);
	}
	
	public ParIdProps decVar(int nivel) {
		ParIdProps parOut = new ParIdProps(tClase.variable, nivel);
		parOut.setId(consumeId().getLexema());
		consume(tToken.dosPuntos);
		parOut.setProps(tipoIden(parOut.getId()));
		return parOut;
	}
	
	//Tambi�n vale para los tBase
	//Si devolvemos las propiedades a null es que ha habido errores
	public PropsObjTS tipoIden(String id) {
		//Primero los tipos b�sicos
		if (tokActual.getTipoToken() == tToken.tipoVarEntero) {
			consume(tToken.tipoVarEntero);
			return new Entero();
		}
		if (tokActual.getTipoToken() == tToken.tipoVarNatural) {
			consume(tToken.tipoVarNatural);
			return new Natural();
		}
		if (tokActual.getTipoToken() == tToken.tipoVarReal) {
			consume(tToken.tipoVarReal);
			return new Real();
		}
		if (tokActual.getTipoToken() == tToken.tipoVarBooleano) {
			consume(tToken.tipoVarBooleano);
			return new Booleano();
		}
		if (tokActual.getTipoToken() == tToken.tipoVarCaracter) {
			consume(tToken.tipoVarCaracter);
			return new Caracter();
		}
		//Referencias a otros tipos
		if (tokActual.getTipoToken() == tToken.identificador) {
			//Comprobamos si esxiste el tipo en la tabla de s�mbolos
			//if (ts.existeTipo(tokActual.getLexema())) {
			int i;
			i = existeIdTiposPen(tokActual.getLexema());
			if (i != -1) {
				//tamRef = ts.getTabla().get(tokActual.getLexema()).getPropiedadesTipo().getTam();
				consume(tToken.identificador);
				//return new Referencia(tokActual.getLexema(), tamRef);
				return new Referencia(id, tiposPenTS.get(i).getProps().getTam());
			}
			//A�adimos control de errores
			errorProg = true;
			System.out.print("El siguiente tipo no fue declarado previamente: '" + tokActual.getLexema() + "'.\n");
			return null;
		}
		//Arrays
		if (tokActual.getTipoToken() == tToken.arrayT) {
			//Variables necesarias para rellenar las propiedades del array
			int nElems;
			PropsObjTS tBase;
			//Consumimos el token del array
			consume(tToken.arrayT);
			//Consumimos el primer corchete
			consume(tToken.corApertura);
			//Tenemos el n� d eelementos
			if (tokActual.getTipoToken() != tToken.natural) {
				//A�adimos control de errores
				errorProg = true;
				System.out.print("Se esperaba un natural en la definici�n de elementos del array '" + id + "'." + "\n" +
						"Token encontrado en su lugar: " + tokActual.getLexema());
				return null;
			}
			else{
				nElems = Integer.valueOf(tokActual.getLexema()).intValue();
				//Consumimos el token del n� de elementos, el token de corchete de cierre, y el token del of
				consume(tToken.natural);
				consume(tToken.corCierre);
				consume(tToken.ofT);
				//Vamos con el tipo base
				tBase = tipoIden(id);
				//Devolvemos las propiedades del array
				return new Array(nElems, tBase);
			}
		}
		//Punteros
		if (tokActual.getTipoToken() == tToken.pointerT) {
			//Consumimos el token 'pointer'
			consume(tToken.pointerT);
			//Devolvemos propiedades para nuevo puntero con su tipo base asociado
			if (tokActual.getTipoToken() == tToken.nullM)
				return new Puntero(null);
			else
				return new Puntero(tipoIden(id));
		}
		//Registros
		if (tokActual.getTipoToken() == tToken.recordT) {
			//Variables necesarias para rellenar las propiedades del registro
			Registro reg = new Registro();
			//Consumimos el token 'record' y la llave de apertura
			consume(tToken.recordT);
			consume(tToken.llaveApertura);
			
			reg = campos(reg);
			
			if (errorProg) {
				System.out.print("Hubo error en la declaraci�n de los campos del registro '" + id + "'." + "\n" +
						"Token encontrado en su lugar: " + tokActual.getLexema());
				return null;
			}
			
			consume(tToken.llaveCierre);
			return reg;
		}
		errorProg = true;
		System.out.print("Hubo error en la declaraci�n de los campos del registro '" + id + "'." + "\n" +
				"Token encontrado en su lugar: " + tokActual.getLexema());
		return null;
	}
	//Si devolvemos el registro a null qes que ha habido errores
	public Registro campos(Registro reg) {
		//Declaraci�n de las variables necesarias
		ParRegistroTam reg2 = new ParRegistroTam(null, 0);
		Campo campo;
		boolean aux;
		//Cuerpo asociado a la funcionalidad de los no terminales
		campo = campo();
		aux = tokActual.getTipoToken() == tToken.puntoyComa;
		if (aux)
			reg2 = rcampos1(reg, 0);
		if (errorProg) {
			return null;
		}
		else {
			if (aux) {
				reg2.getReg().a�adeCampo(campo);
				// faltan dos emits
				reg2.getReg().setTam(reg2.getTam() + campo.getDesp());
				return reg2.getReg();
			}
			else {
				reg.a�adeCampo(campo);
				// faltan dos emits
				return reg;
			}
		}
	}
	
	public ParRegistroTam rcampos1(Registro reg, int tam) {
		//Declaraci�n de las variables necesarias
		ParRegistroTam reg2 = new ParRegistroTam(null, 0);
		Campo campo;
		boolean aux;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.puntoyComa);
		campo = campo();
		aux = tokActual.getTipoToken() == tToken.puntoyComa;
		if (aux)
			reg2 = rcampos1(reg, tam + campo.getDesp());
		if (errorProg) {
			return null;
		}
		else {
			if (aux) {
				reg2.getReg().a�adeCampo(campo);
				// faltan dos emits
				reg2.getReg().setTam(tam + campo.getDesp());
				return reg2;
			}
			else {
				reg.a�adeCampo(campo);
				reg.setTam(tam + campo.getDesp());
				// faltan dos emits
				return new ParRegistroTam(reg, reg.getTam());
			}
		}
	}
	
	public void rcampos2() {
		//La tabla se crea en el constructor, sino la crear�amos aqu�
//		if (errorProg)
//			return true;
//		else
//			return false;
	}
	
	public Campo campo() {
		Campo campo = new Campo();
		campo.setId(consumeId().getLexema());
		consume(tToken.dosPuntos);
		campo.setTipo(tipoIden(campo.getId()));
		campo.setDesp(campo.getTipo().getTam());
		return campo;
	}
	
	public ParIdProps decTipo(int nivel) {
		ParIdProps parOut = new ParIdProps(tClase.tipo, nivel);
		//Consumimos el token 'tipo'
		consume(tToken.decTipo);
		parOut.setId(consumeId().getLexema());
		consume(tToken.igual);
		parOut.setProps(tipoIden(parOut.getId()));
		if (!errorProg)
			tiposPenTS.add(parOut);
		return parOut;
	}
	
	public ParIdProps decProc(int nivel) {
//		ParString parOut = new ParString();
//		parOut.setIden(consumeId().getLexema());
//		consume(tToken.dosPuntos);
//		parOut.setTipo(dametipoT(consumeTipo().getTipoToken()));
//		return parOut;
		
		//return new ParIdProps();
		return null;
	}
	
	public ParBooleanInt sents(int etiqIn) {
		//Declaraci�n de las variables necesarias
		//boolean errorSent1, errorSent2 = false;
		ParBooleanInt errorEtiq1, errorEtiq2;
		//Cuerpo asociado a la funcionalidad de los no terminales
		errorEtiq1 = sent(etiqIn);
		if (tokActual.getTipoToken() == tToken.puntoyComa)
			errorEtiq2 = rsents1(errorEtiq1.getIntVal());
		else
			errorEtiq2 = rsents2(errorEtiq1.getIntVal());
		//return errorSent1 || errorSent2;
		return new ParBooleanInt(errorEtiq1.getBooleanVal() || errorEtiq2.getBooleanVal(),
				errorEtiq2.getIntVal());
	}
	
	public ParBooleanInt rsents1(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParBooleanInt errorEtiq1, errorEtiq2;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.puntoyComa);
		errorEtiq1 = sent(etiqIn);
		if (tokActual.getTipoToken() == tToken.puntoyComa)
			errorEtiq2 = rsents1(errorEtiq1.getIntVal());
		else
			errorEtiq2 = rsents2(errorEtiq1.getIntVal());
		return new ParBooleanInt(errorEtiq1.getBooleanVal() || errorEtiq2.getBooleanVal(),
				errorEtiq2.getIntVal());
	}
	
	public ParBooleanInt rsents2(int etiqIn) {
		//Declaraci�n de las variables necesarias
		
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (errorProg || !(tokActual.getTipoToken() == tToken.finDeFichero
				|| tokActual.getTipoToken() == tToken.llaveCierre))
			return new ParBooleanInt(true, etiqIn);
		else
			return new ParBooleanInt(false, etiqIn);
	}
	
	public ParBooleanInt sent(int etiqIn) {
		//Declaraci�n de las variables necesarias
		
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.entradaTeclado) {
			return sread(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.salidaPantalla) {
			return swrite(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.llaveApertura) {
			return sbloque(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.ifC) {
			return sif(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.whileC) {
			return swhile(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.forC) {
			return sfor(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.identificador) {
			return sasign(etiqIn);
		}
		//A�adimos control de errores
		errorProg = true;
		System.out.println("Error: Se esperaba una de las siguientes instrucciones:\n" +
				"	- Asignaci�n			=>  ':='\n" +
				"	- Entrada por teclado		=>  'in()'\n" +
				"	- Salida por pantalla		=>  'out()'\n" +
				"	- if-then-else\n" +
				"	- while-do\n" +
				"	- for-to-do\n" +
				"Token en an�lisis: " + tokActual.getTipoToken() + "\n");
		return new ParBooleanInt(true, etiqIn);
//		consume(tToken.puntoyComa);
	}
	
	public ParBooleanInt swrite(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.salidaPantalla);
		//Ahora en el token actual tenemos el parentesis de apertura
		//si el an�lisis va bien, y luego la expresi�n correspondiente
		if (tokActual.getTipoToken() == tToken.parApertura) {
			//Consumimos el parentesis de apertura y vamos con la expresi�n
			consume(tToken.parApertura);
			tipoEtiq = exp(etiqIn);
			///////////////////////////////////////////////////////////////
			if (tipoEtiq.getT() == tipoT.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la expresi�n de la instrucci�n de salida por pantalla." + "\n");
				return new ParBooleanInt(true, etiqIn);
			}
			else {
				emite("escribir");
				emit.emit(Emit.ESCRIBIR);

				consume(tToken.parCierre);
				return new ParBooleanInt(false, tipoEtiq.getEtiq() + 1);
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba par�ntesis de apertura despu�s de operaci�n de entrada" + "\n" +
					"por teclado.\n");
			return new ParBooleanInt(true, etiqIn);
		}
	}
	
	public ParBooleanInt sread(int etiqIn) {
		//Declaraci�n de las variables necesarias
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.entradaTeclado);
		//Ahora en el token actual tenemos el parentesis de apertura
		//si el an�lisis va bien, y luego el identificador correspondiente
		if (tokActual.getTipoToken() == tToken.parApertura) {
			consume(tToken.parApertura);
			if (tokActual.getTipoToken() == tToken.identificador &&
					ts.existeId(tokActual.getLexema(), tClase.variable, new Integer(0))) {
				lexIden = tokActual.getLexema();
				emite("leer");
				emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				emit.emit(Emit.LEER);
				//emit.emit(emit.desapilaCode(ts.getTabla().get(lexIden).getT()), 
				//		new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));

				consume(tToken.identificador);
				consume(tToken.parCierre);
				//LEER de la MV s�lo se emite en una instrucci�n
				return new ParBooleanInt(false, etiqIn + 2);
			}
			else {
				errorProg = true;
				vaciaCod();
				System.out.println("Error: El par�metro de la operaci�n 'in' debe ser un identificador, o" + "\n" +
						"el identificador no existe en la tabla de s�mbolos.\n");
				return new ParBooleanInt(true, etiqIn);
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba par�ntesis de apertura despu�s de operaci�n de entrada" + "\n" +
					"por teclado.\n");
			return new ParBooleanInt(true, etiqIn);
		}
	}
	
	public ParBooleanInt sasign(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema())) {
				//ts.existeId(tokActual.getLexema(), tClase.variable) {
			lexIden = tokActual.getLexema();
			consume(tToken.identificador);
			//Una vez parseado el identificador vamos con el simbolo de la asignaci�n
			consume(tToken.asignacion);
			//LLamada a epx()
			tipoEtiq = exp(etiqIn);
			/////////////////////////////////////////////////////////////////////////
			//if (tipoEtiq.getT() == tipoT.tError || !esCompatibleAsig(ts.getTabla().get(lexIden).getPropiedadesTipo().getT(), tipoEtiq.getT())) {
			if (tipoEtiq.getT() == tipoT.tError || !esCompatibleAsig(ts.getTabla().get(lexIden).getPropiedadesTipo().getT(), tipoEtiq.getT())) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la asignaci�n: La expresi�n es err�nea, o los tipos de la" + "\n" +
						"asignaci�n son incompatibles." + "\n");
				return new ParBooleanInt(true, etiqIn);
			}
			else {
				emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				//emit.emit(emit.desapilaCode(ts.getTabla().get(lexIden).getPropiedadesTipo().getT()), 
				//		new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));
				return new ParBooleanInt(false, tipoEtiq.getEtiq() + 1);
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: El identificador " + tokActual.getLexema() + " no fue declarado previamente." + "\n");
			return new ParBooleanInt(true, etiqIn);
		}
	}
	
	public boolean esCompatibleAsig(tipoT tipoId, tipoT tipoExp) {
		if (tipoId == tipoExp)
			return true;
		if (tipoId == tipoT.tFloat && esTipoNum(tipoExp))
			return true;
		if (tipoId == tipoT.tInt && tipoExp == tipoT.tNat)
			return true;
		return false;
	}
	
	public boolean esTipoNum(tipoT tipo) {
		if (tipo == tipoT.tNat || tipo == tipoT.tInt ||
				tipo == tipoT.tFloat)
			return true;
		else
			return false;
	}
	
	public boolean esTipoNatInt(tipoT tipo) {
		if (tipo == tipoT.tNat || tipo == tipoT.tInt)
			return true;
		else
			return false;
	}
	
	public ParBooleanInt sbloque(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParBooleanInt errorEtiq;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token 'llave de apertura'
		consume(tToken.llaveApertura);
		//Llamada a sents()
		errorEtiq = sents(etiqIn);
		if (errorEtiq.getBooleanVal()) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en el bloque de instrucciones." + "\n");
			return new ParBooleanInt(true, 0);
		}
		else {
			//Consumimos el token 'llave de cierre'
			consume(tToken.llaveCierre);
			return errorEtiq;
		}
	}
	
	public ParBooleanInt sif(int etiqIn) {
		//Declaraci�n de las variables necesarias
		//tSintetiz tipo; //Ahora necesitamos guardar tipo y etiqueta
		ParTipoEtiq tipoEtiq = new ParTipoEtiq();
		ParBooleanInt errorEtiq1, errorEtiq2;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token del while
		consume(tToken.ifC);
		//LLamada a la epx()
		tipoEtiq = exp(etiqIn);
		if (tipoEtiq.getT() != tipoT.tBool) { 
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la instrucci�n 'if': El tipo de la expresi�n a evaluar no es 'boolean'." +"\n");
			return new ParBooleanInt(true, 0);
		}
		else {
			//Consumimos el token del 'then'
			consume(tToken.thenC);
			//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
			//Instrucci�n a parchear
			emite("ir-f(?)");
			//Llamada a la sentencia que conforma el cuerpo del if
			errorEtiq1 = sent(tipoEtiq.getEtiq() + 1);
			if (errorEtiq1.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucci�n 'if'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Llamada a pelse
				errorEtiq2 = pelse(errorEtiq1.getIntVal());
				if (errorEtiq2.getBooleanVal()) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en el cuerpo de la instrucci�n 'else'." + "\n");
					return new ParBooleanInt(true, 0);
				}
				else {
					//Podemos parchear el anterior 'ir-f()' del c�digo a pila
					//pero hay que saber si nos viene un else o no, ya que se
					//debe calcular una posici�n m�s o no
					if (errorEtiq1.getIntVal() == errorEtiq2.getIntVal()) {
						parchea(tipoEtiq.getEtiq(), errorEtiq1.getIntVal());
						return errorEtiq2;
					}
					else {
						parchea(tipoEtiq.getEtiq(), errorEtiq1.getIntVal() + 1);
						return errorEtiq2;
					}
				}
			}
		}
	}
	
	public ParBooleanInt pelse(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParBooleanInt errorEtiq;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Si no aparece el token 'else', consideramos que es s�lo un if-then
		if (tokActual.getTipoToken() != tToken.elseC) {
			return new ParBooleanInt(false, etiqIn);
		}
		else {
			//Consumimos el token del 'else'
			consume(tToken.elseC);
			emite("ir-a(?)");
			//Llamada a la sentencia que conforma el cuerpo del else
			errorEtiq = sent(etiqIn + 1);
			if (errorEtiq.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucci�n 'else'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Ya podemos parchear el 'ir-a', porque ya tenemos la direcci�n
				parchea(etiqIn, errorEtiq.getIntVal());
				//Devolvemos la nueva etiqueta
				return errorEtiq;
			}
		}
	}
	
	//sif sin optimizar. Genera un ir-a que puede ser innecesario en caso
	//de que no haya else
	/*public ParBooleanInt sif(int etiqIn) {
		//Declaraci�n de las variables necesarias
		//tSintetiz tipo; //Ahora necesitamos guardar tipo y etiqueta
		ParTipoEtiq tipoEtiq = new ParTipoEtiq();
		ParBooleanInt errorEtiq1, errorEtiq2;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token del while
		consume(tToken.ifC);
		//LLamada a la epx()
		tipoEtiq = exp(etiqIn);
		if (tipoEtiq.getT() != tipoT.tBool) { 
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la instrucci�n 'if': El tipo de la expresi�n a evaluar no es 'boolean'." +"\n");
			return new ParBooleanInt(true, 0);
		}
		else {
			//Consumimos el token del 'then'
			consume(tToken.thenC);
			//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
			//Instrucci�n a parchear
			emite("ir-f(?)");
			//Llamada a la sentencia que conforma el cuerpo del if
			errorEtiq1 = sent(tipoEtiq.getEtiq() + 1);
			if (errorEtiq1.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucci�n 'if'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
				emite("ir-a(?)");
				//Como ya tenemos la etiqueta de salida de 'sent()', 
				//podemos parchear el anterior 'ir-f()' del c�digo a pila
				parchea(tipoEtiq.getEtiq(), errorEtiq1.getIntVal() + 1);
				//Llamada a pelse
				errorEtiq2 = pelse(errorEtiq1.getIntVal() + 1);
				if (errorEtiq2.getBooleanVal()) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en el cuerpo de la instrucci�n 'else'." + "\n");
					return new ParBooleanInt(true, 0);
				}
				else {
					//Como ya tenemos la etiqueta de salida de 'else()', 
					//podemos parchear el anterior 'ir-a()' del c�digo a pila
					parchea(errorEtiq1.getIntVal(), errorEtiq2.getIntVal());
					return errorEtiq2;
				}
			}
		}
	}*/
	
	/*public ParBooleanInt pelse(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParBooleanInt errorEtiq;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Si no aparece el token 'else', consideramos que es s�lo un if-then
		if (tokActual.getTipoToken() != tToken.elseC) {
			return new ParBooleanInt(false, etiqIn);
		}
		else {
			//Consumimos el token del 'else'
			consume(tToken.elseC);
			//Llamada a la sentencia que conforma el cuerpo del else
			errorEtiq = sent(etiqIn);
			if (errorEtiq.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucci�n 'else'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Devolvemos la nueva etiqueta
				return errorEtiq;
			}
		}
	}*/
	
	public ParBooleanInt swhile(int etiqIn) {
		//Declaraci�n de las variables necesarias
		//tSintetiz tipo; //Ahora necesitamos guardar tipo y etiqueta
		ParTipoEtiq tipoEtiq;
		ParBooleanInt errorEtiq;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token del while
		consume(tToken.whileC);
		//LLamada a la epx()
		tipoEtiq = exp(etiqIn);
		if (tipoEtiq.getT() != tipoT.tBool) { 
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la instrucci�n 'while': El tipo de la expresi�n a evaluar no es 'boolean'." +"\n");
			return new ParBooleanInt(true, 0);
		}
		else {
			//Consumimos el token del 'do'
			consume(tToken.doC);
			//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
			//Instrucci�n a parchear
			emite("ir-f(?)");
			//Llamada a la sentencia que conforma el cuerpo del while
			errorEtiq = sent(tipoEtiq.getEtiq() + 1);
			if (errorEtiq.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucci�n 'while'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
				emite("ir-a(" + etiqIn + ")");
				//Como ya tenemos la etiqueta de salida de 'sent()', 
				//podemos parchear el anterior 'ir-f()' del c�digo a pila
				parchea(tipoEtiq.getEtiq(), errorEtiq.getIntVal() + 1);
				return new ParBooleanInt(false, errorEtiq.getIntVal() + 1);
			}
		}
	}
	
	public ParBooleanInt sfor(int etiqIn) {
		//Declaraci�n de las variables necesarias
		//tSintetiz tipo; //Ahora necesitamos guardar tipo y etiqueta, 1 variable para cada expresi�n
		ParTipoEtiq tipoEtiq1, tipoEtiq2;
		ParBooleanInt errorEtiq = new ParBooleanInt();
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token del for
		consume(tToken.forC);
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema(), tClase.variable, new Integer(0))) {
			lexIden = tokActual.getLexema();
			consume(tToken.identificador);
			//Una vez parseado el identificador vamos con el simbolo '='
			consume(tToken.igual);
			//LLamada a la 1� epx()
			tipoEtiq1 = exp(etiqIn);
			/////////////////////////////////////////////////////////////////////////
			//Falta cambiar el getTipo
			if (!(tipoEtiq1.getT() == tipoT.tNat || tipoEtiq1.getT() == tipoT.tInt) 
					|| !esCompatibleAsig(ts.getTabla().get(lexIden).getPropiedadesTipo().getT(), tipoEtiq1.getT())) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la instrucci�n 'for': La 1� expresi�n es err�nea, o el tipo de la" + "\n" +
						"misma es incompatible con el del identificador." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
				emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				//Instrucciones para la m�quina virtual con 'emit'
				//emit.emit(emit.desapilaCode(ts.getTabla().get(lexIden).getPropiedadesTipo().getT()), 
				//		new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));
				emite("apila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				//Consumimos el token 'to'
				consume(tToken.toC);
				//LLamada a la 2� epx()
				tipoEtiq2 = exp(tipoEtiq1.getEtiq() + 2);
				/////////////////////////////////////////////////////////////////////////
				//Falta cambiar el getTipo
				if (!(tipoEtiq2.getT() == tipoT.tNat || tipoEtiq2.getT() == tipoT.tInt) 
						|| !esCompatibleAsig(ts.getTabla().get(lexIden).getPropiedadesTipo().getT(), tipoEtiq2.getT())) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en la instrucci�n 'for': La 2� expresi�n es err�nea, o el tipo de la" + "\n" +
							"misma es incompatible con el del identificador." + "\n");
					return new ParBooleanInt(true, 0);
				}
				else {
					//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
					//Se realiza la comparaci�n sel contador del 'for'
					//Faltan las instrucciones para la m�quina virtual con 'emit'
					emite("menorIgual");
					//Instrucci�n a parchear
					emite("ir-f(?)");
					//Consumimos el token del 'do'
					consume(tToken.doC);
					//Llamada a la sentencia que conforma el cuerpo del for
					errorEtiq = sent(tipoEtiq2.getEtiq() + 2);
					if (errorEtiq.getBooleanVal()) {
						errorProg = true;
						vaciaCod();
						System.out.println("Error en el cuerpo de la instrucci�n 'for'." + "\n");
						return new ParBooleanInt(true, 0);
					}
					else {
						//Como ya tenemos la etiqueta de salida de 'sent()', 
						//podemos parchear el anterior 'ir-f()' del c�digo a pila
						parchea(tipoEtiq2.getEtiq() + 1, errorEtiq.getIntVal() + 5);
						//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
						//Hay que incrementar el contador
						emite("apila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
						emite("apila(1)");
						emite("suma");
						emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
						emite("ir-a(" + (tipoEtiq1.getEtiq() + 1)+ ")");
						return new ParBooleanInt(false, errorEtiq.getIntVal() + 5);
					}
				}
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se espera identificador declarado despu�s de 'for'." + "\n" +
					"Token en prean�lisis: " + tokActual.getLexema() + " Tipo de token: " + tokActual.getTipoToken() + "\n");
			return new ParBooleanInt(true, 0);
		}
	}
	
	public void parchea (int nLinea, int etiq) {
		//Se hace en principio para el vector de Strings de instrucciones apila
		String linAParchear;
		linAParchear = instMPOut.get(nLinea);
		if (linAParchear.equals("ir-a(?)")) {
			linAParchear = "ir-a(" + etiq + ")";
			instMPOut.setElementAt(linAParchear, nLinea);
			return;
		}
		if (linAParchear.equals("ir-f(?)")) {
			linAParchear = "ir-f(" + etiq + ")";
			instMPOut.setElementAt(linAParchear, nLinea);
			return;
		}
		if (linAParchear.equals("ir-v(?)")) {
			linAParchear = "ir-v(" + etiq + ")";
			instMPOut.setElementAt(linAParchear, nLinea);
			return;
		}
		System.out.print("Error: Se intent� parchear la siguiente instrucci�n: " + linAParchear + "." + "\n");
	}
	
	public ParTipoEtiq mem(int etiqIn, tipoT tipo) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		String lexIden;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema(), tClase.variable, new Integer(0))) {
			//Obtenemos el nombre del identificador
			lexIden = tokActual.getLexema();
			//Lo consumimos
			consume(tToken.identificador);
			//Hacemos el emite correspondiente
			emite("apila(" + ts.getTabla().get(lexIden).getDirM() + ")");
			//Vamos con lo siguiente que puede venir tras un identificador
			tipoEtiq = rmem(etiqIn, tipo);
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba identificador, o si lo es no fue declarado previamente." + "\n");
			return new ParTipoEtiq(tipoT.tError, etiqIn);
		}
		
		/*if (tipoEtiq.getT() == tipoT.array || tipoEtiq.getT() == tipoT.registro) {
			
		}*/
		
		return tipoEtiq;
	}
	
	public ParTipoEtiq rmem(int etiqIn, tipoT tipo) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		
		if (tokActual.getTipoToken() == tToken.puntero) {
			
		}
		if (tokActual.getTipoToken() == tToken.corApertura) {
			
		}
		if (tokActual.getTipoToken() == tToken.punto) {
			
		}
		
		return tipoEtiq;
	}
	
	public ParTipoEtiq exp(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2, tipoEtiqH;
		//tipoT tipoH = tipoT.tError;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx1()
		tipoEtiq1 = exp1(etiqIn);
		tipoEtiqH = tipoEtiq1;
		if (tipoEtiq1.getT() == tipoT.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (EXP1).\n");
			return tipoEtiq1;
		}
		else {
			if (esTokenOp0(tokActual.getTipoToken())) {
				tipoEtiq2 = rexp1(tipoEtiqH);
				if (tipoEtiq2.getT() == tipoT.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP_1).\n");
					return tipoEtiq2;
				}
				else
					return new ParTipoEtiq(tipoT.tBool, tipoEtiq2.getEtiq());
			}
			else {
				// Hemos llegado al fin de la instrucci�n
				return tipoEtiqH;
			}
		}
	}
	
	public ParTipoEtiq rexp1(ParTipoEtiq tipoEtiqH) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		tipoT tipo;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op0();
		tipoEtiq = exp1(tipoEtiqH.getEtiq());
		tipo = dameTipo(tipoEtiqH.getT(), tipoEtiq.getT(), op);
		if (tipo == tipoT.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error de tipos en operaci�n de igualdad: '"+ op.toString() +"'.\n");
			return new ParTipoEtiq(tipoT.tError, tipoEtiq.getEtiq());
		}
		else {
			// TODO
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
			return new ParTipoEtiq(tipo, tipoEtiq.getEtiq() + 1);
		}	
	}
	
	public ParTipoEtiq exp1(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2, tipoEtiqH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx2()
		tipoEtiq1 = exp2(etiqIn);
		tipoEtiqH = tipoEtiq1;
		if (esTokenOp1(tokActual.getTipoToken())) {
			tipoEtiq2 = rexp11(tipoEtiqH);
			if (tipoEtiq1.getT() == tipoT.tError || tipoEtiq2.getT() == tipoT.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP1_1).\n");
				return new ParTipoEtiq(tipoT.tError, tipoEtiq2.getEtiq());
			}
			else
				return tipoEtiq2;
		}
		else {
			// Hemos llegado al fin de la instrucci�n
			return tipoEtiq1;
		}
	}
	
	//Definici�n con el cortocircuito para 'or'
	public ParTipoEtiq rexp11(ParTipoEtiq tipoEtiqH) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2;
		tipoT tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op1();
		//Si no es una oLogica, actuamos como siempre
		if (op != tOp.oLogica) {
			tipoEtiq1 = exp2(tipoEtiqH.getEtiq());
			tipoH1 = dameTipo(tipoEtiqH.getT(), tipoEtiq1.getT(), op);
			if (tipoH1 != tipoT.tError) {
				emite(op.toString());
				emit.emit(opUtils.getOperationCode(op));
			}
			if (esTokenOp1(tokActual.getTipoToken())) {
				tipoEtiq2 = rexp11(new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1));
				if (tipoEtiq2.getT() == tipoT.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
					return new ParTipoEtiq(tipoT.tError, tipoEtiq2.getEtiq());
				}
				else {
					return tipoEtiq2;
				}
			}
			else {
				// Hemos llegado al fin de la instrucci�n
				if (tipoH1 == tipoT.tError)
					return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq());
				else
					return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1);
			}
		}
		//Si es una oLogica, procedemos insertando las instrucciones a pila
		//para el cortocircuito
		else {
			emite("copia");
			emite("ir-v(?)");
			emite("desapila");
			tipoEtiq1 = exp2(tipoEtiqH.getEtiq() + 3);
			tipoH1 = dameTipo(tipoEtiqH.getT(), tipoEtiq1.getT(), op);
			if (esTokenOp1(tokActual.getTipoToken())) {
				tipoEtiq2 = rexp11(new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq()));
				if (tipoEtiq2.getT() == tipoT.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
					return new ParTipoEtiq(tipoT.tError, tipoEtiq2.getEtiq());
				}
				else {
					//Parcheamos con la etiqueta del final de las expresiones
					//reconocidas
					parchea(tipoEtiqH.getEtiq() + 1, tipoEtiq2.getEtiq());
					return tipoEtiq2;
				}
			}
			else {
				//Hemos llegado al fin de la instrucci�n
				//Parcheamos con la etiqueta del final de exp2()
				parchea(tipoEtiqH.getEtiq() + 1, tipoEtiq1.getEtiq());
				return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq());
			}
		}
	}
	
	//Definici�n sin el cortocircuito
	/*public ParTipoEtiq rexp11(ParTipoEtiq tipoEtiqH) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2;
		tipoT tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op1();
		tipoEtiq1 = exp2(tipoEtiqH.getEtiq());
		tipoH1 = dameTipo(tipoEtiqH.getT(), tipoEtiq1.getT(), op);
		if (tipoH1 != tipoT.tError) {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
		}
		if (esTokenOp1(tokActual.getTipoToken())) {
			tipoEtiq2 = rexp11(new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1));
			if (tipoEtiq2.getT() == tipoT.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
				return new ParTipoEtiq(tipoT.tError, tipoEtiq2.getEtiq());
			}
			else {
				return tipoEtiq2;
			}
		}
		else {
			// Hemos llegado al fin de la instrucci�n
			if (tipoH1 == tipoT.tError)
				return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq());
			else
				return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1);
		}
	}*/
	
	public ParTipoEtiq exp2(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2, tipoEtiqH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx3()
		tipoEtiq1 = exp3(etiqIn);
		tipoEtiqH = tipoEtiq1;
		if (esTokenOp2(tokActual.getTipoToken())) {
			tipoEtiq2 = rexp21(tipoEtiqH);
			if (tipoEtiq1.getT() == tipoT.tError || tipoEtiq2.getT() == tipoT.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP2_1).\n");
				return new ParTipoEtiq(tipoT.tError, tipoEtiq2.getEtiq());
			}
			else
				return tipoEtiq2;
		}
		else {
			// Hemos llegado al fin de la instrucci�n
			return tipoEtiq1;
		}
	}
	
	//Definici�n con el cortocircuito para 'and'
	public ParTipoEtiq rexp21(ParTipoEtiq tipoEtiqH) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2;
		tipoT tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op2();
		//Si no es una yLogica, actuamos como siempre
		if (op != tOp.yLogica) {
			tipoEtiq1 = exp3(tipoEtiqH.getEtiq());
			tipoH1 = dameTipo(tipoEtiqH.getT(), tipoEtiq1.getT(),op);
			if (tipoH1 != tipoT.tError) {
				emite(op.toString());
				emit.emit(opUtils.getOperationCode(op));
			}
			if (esTokenOp2(tokActual.getTipoToken())) {
				tipoEtiq2 = rexp21(new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1));
				if (tipoEtiq2.getT() == tipoT.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
					return tipoEtiq2;
				}
				else {
					return tipoEtiq2;
				}
			}
			else {
				// Hemos llegado al fin de la instrucci�n
				if (tipoH1 == tipoT.tError)
					return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq());
				else
					return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1);
			}
		}
		//Si es una yLogica, procedemos insertando las instrucciones a pila
		//para el cortocircuito
		else {
			emite("ir-f(?)");
			tipoEtiq1 = exp3(tipoEtiqH.getEtiq() + 1);
			tipoH1 = dameTipo(tipoEtiqH.getT(), tipoEtiq1.getT(),op);
			if (esTokenOp2(tokActual.getTipoToken())) {
				tipoEtiq2 = rexp21(new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq()));
				if (tipoEtiq2.getT() == tipoT.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
					return tipoEtiq2;
				}
				else {
					emite("ir-a(" + (tipoEtiq2.getEtiq() + 2)  + ")");
					//emite("apila(0)");
					emite("apila(false)");
					parchea(tipoEtiqH.getEtiq(), tipoEtiq2.getEtiq() + 1);
					//return tipoEtiq2;
					return new ParTipoEtiq(tipoEtiq2.getT(), tipoEtiq2.getEtiq() + 2);
				}
			}
			else {
				//Hemos llegado al fin de la instrucci�n
				//Parcheamos con la etiqueta del final de exp3()
				emite("ir-a(" + (tipoEtiq1.getEtiq() + 2)  + ")");
				//emite("apila(0)");
				emite("apila(false)");
				parchea(tipoEtiqH.getEtiq(), tipoEtiq1.getEtiq() + 1);
				return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 2);
			}
		}
	}
	
	//Definici�n sin el cortocircuito
	/*public ParTipoEtiq rexp21(ParTipoEtiq tipoEtiqH) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2;
		tipoT tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op2();
		tipoEtiq1 = exp3(tipoEtiqH.getEtiq());
		tipoH1 = dameTipo(tipoEtiqH.getT(), tipoEtiq1.getT(),op);
		if (tipoH1 != tipoT.tError) {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
		}
		if (esTokenOp2(tokActual.getTipoToken())) {
			tipoEtiq2 = rexp21(new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1));
			if (tipoEtiq2.getT() == tipoT.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
				return tipoEtiq2;
			}
			else {
				return tipoEtiq2;
			}
		}
		else {
			// Hemos llegado al fin de la instrucci�n
			if (tipoH1 == tipoT.tError)
				return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq());
			else
				return new ParTipoEtiq(tipoH1, tipoEtiq1.getEtiq() + 1);
		}
	}*/
	
	public ParTipoEtiq exp3(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq1, tipoEtiq2, tipoEtiqH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.opVAbs)
			tipoEtiq1 = exp42(etiqIn);
		else
			if (esTokenOp41(tokActual.getTipoToken()))
				tipoEtiq1 = exp41(etiqIn);
			else
				tipoEtiq1 = exp43(etiqIn);
//Antes
//		else
//			tipo1 = exp43();
		tipoEtiqH = tipoEtiq1;
		if (tipoEtiqH.getT() == tipoT.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (EXP4_1), (EXP4_2) o (EXP4_3).\n");
			return tipoEtiqH;
		}
		else {
			//Aqu� es donde ven�a esta signaci�n => 'tipoH = tipo1;' Se opta por poner arriba
			if (esTokenOp3(tokActual.getTipoToken())) {
				tipoEtiq2 = rexp31(tipoEtiqH);
				if (tipoEtiq2.getT() == tipoT.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP3_1).\n");
					return tipoEtiq2;
				}
				else
					return new ParTipoEtiq(tipoT.tNat, tipoEtiq2.getEtiq());
			}
			else {
				// Hemos llegado al fin de la instrucci�n
				return tipoEtiq1;
			}
		}
	}
	
	public ParTipoEtiq rexp31(ParTipoEtiq tipoEtiqH) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		tipoT tipo;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		op = op3();
		tipoEtiq = exp3(tipoEtiqH.getEtiq());
		tipo = dameTipo(tipoEtiq.getT(), tipoEtiqH.getT(), op);
		if (tipo == tipoT.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
			return new ParTipoEtiq(tipoT.tError, tipoEtiq.getEtiq());
		}
		else {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
			return new ParTipoEtiq(tipo, tipoEtiq.getEtiq() + 1);
		}
	}
	
	public ParTipoEtiq exp41(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		tipoT tipo;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		op = op41();
		tipoEtiq = term(etiqIn);
		tipo = dameTipo(tipoEtiq.getT(), op);
		if (tipo == tipoT.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
			return new ParTipoEtiq(tipoT.tError, tipoEtiq.getEtiq());
		}
		else {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
			return new ParTipoEtiq(tipo, tipoEtiq.getEtiq() + 1);
		}
	}
	
	public ParTipoEtiq exp42(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		tipoT tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.opVAbs);
		//tipo1 = term();
		tipoEtiq = exp(etiqIn);
		tipo = dameTipo(tipoEtiq.getT(), tOp.opVAbs);
		if (tipo == tipoT.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operaci�n: '"+ tOp.opVAbs.toString() +"'.\n");
			return new ParTipoEtiq(tipoT.tError, tipoEtiq.getEtiq());
		}
		else {
			emite(tToken.opVAbs.toString());
			emit.emit(opUtils.getOperationCode(tOp.opVAbs));
			consume(tToken.opVAbs);
			return new ParTipoEtiq(tipo, tipoEtiq.getEtiq() + 1);
		}
	}
	
	public ParTipoEtiq exp43(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		//Cuerpo asociado a la funcionalidad de los no terminales
		tipoEtiq = term(etiqIn);
		return tipoEtiq;
	}
	
	public ParTipoEtiq term(int etiqIn) {
		//Declaraci�n de las variables necesarias
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.booleanoCierto)
			return term1True(etiqIn);
		if (tokActual.getTipoToken() == tToken.booleanoFalso)
			return term1False(etiqIn);
		if (tokActual.getTipoToken() == tToken.caracter)
			return term2(etiqIn);
		if (tokActual.getTipoToken() == tToken.natural)
			return term3(etiqIn);
		if (tokActual.getTipoToken() == tToken.entero)
			return term4(etiqIn);
		if (tokActual.getTipoToken() == tToken.real)
			return term5(etiqIn);
		//Ahora debemos hacer referencia a la memoria a la hora de encontrarnos con
		//un identificador
		if (tokActual.getTipoToken() == tToken.identificador) {
			//OBTENEMOS EL TIPO DEL IDENTIFICADOR A PARTIR DE LA TS//
			return mem(etiqIn, ts.getTabla().get(tokActual.getLexema()).getPropiedadesTipo().getT());
			//return term6(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.parApertura)
			return term7(etiqIn);
		return new ParTipoEtiq(tipoT.tError, etiqIn);
	}
	
	public ParTipoEtiq term1True(int etiqIn) {
		emit.emit(Emit.APILA, new Token(tToken.booleano,"true"));
		emite("apila(" + true + ")");
		consume(tToken.booleanoCierto);
		return new ParTipoEtiq(tipoT.tBool, etiqIn + 1);
	}
	
	public ParTipoEtiq term1False(int etiqIn) {
		emite("apila(" + false + ")");
		emit.emit(Emit.APILA, new Token(tToken.booleano,"false"));
		consume(tToken.booleanoFalso);
		return new ParTipoEtiq(tipoT.tBool, etiqIn + 1);
	}
	
	public ParTipoEtiq term2(int etiqIn) {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.caracter,tokActual.getLexema()));
		consume(tToken.caracter);
		return new ParTipoEtiq(tipoT.tChar, etiqIn + 1);
	}
	
	public ParTipoEtiq term3(int etiqIn) {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.natural,tokActual.getLexema()));

		consume(tToken.natural);
		return new ParTipoEtiq(tipoT.tNat, etiqIn + 1);
	}
	
	public ParTipoEtiq term4(int etiqIn) {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.entero,tokActual.getLexema()));

		consume(tToken.entero);
		return new ParTipoEtiq(tipoT.tInt, etiqIn + 1);
	}
	
	public ParTipoEtiq term5(int etiqIn) {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.real,tokActual.getLexema()));
		consume(tToken.real);
		return new ParTipoEtiq(tipoT.tFloat, etiqIn + 1);
	}
	
	public ParTipoEtiq term6(int etiqIn) {
		//Declaraci�n de las variables necesarias
		String lexIden = new String();
		tipoT tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema(), tClase.variable, new Integer(0))) {
			lexIden = tokActual.getLexema();
			//Ya tenemos todo lo necesario acerca del token, pues lo consumimos
			consume(tToken.identificador);
			//OBTENEMOS EL TIPO DEL IDENTIFICADOR DE LA TS//
			//tipo = ts.getTabla().get(lexIden).getPropiedadesTipo().getT();
			tipo = ts.getTabla().get(lexIden).getPropiedadesTipo().getT();
			emite("apila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
			emit.emit(Emit.APILA_DIR, new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));
			return new ParTipoEtiq(tipo, etiqIn + 1);
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba identificador, o si lo es no fue declarado previamente." + "\n");
			return new ParTipoEtiq(tipoT.tError, etiqIn);
		}
	}
	
	public ParTipoEtiq term7(int etiqIn) {
		//Declaraci�n de las variables necesarias
		ParTipoEtiq tipoEtiq;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.parApertura);
		tipoEtiq = exp(etiqIn);
		consume(tToken.parCierre);
		return tipoEtiq;
	}
	
	public tipoT dameTipo(tipoT tipoEXPIzq, tipoT tipoEXPDer, tOp op) {
		if (esOp0(op)) {
			if (tipoEXPIzq == tipoEXPDer || (esTipoNum(tipoEXPIzq) && esTipoNum(tipoEXPDer)))
				return tipoT.tBool;
			else
				return tipoT.tError;
		}
		if (esOp1(op)) {
			switch (op) {
			case oLogica:
				if (tipoEXPIzq == tipoT.tBool && tipoEXPIzq == tipoEXPDer)
					return tipoT.tBool;
				else
					return tipoT.tError;
			default:
				if (esTipoNum(tipoEXPIzq) && esTipoNum(tipoEXPDer))
					return dameTipoDom(tipoEXPIzq, tipoEXPDer);
			}
		}
		if (esOp2(op)) {
			switch (op) {
			case yLogica:
				if (tipoEXPIzq == tipoT.tBool && tipoEXPIzq == tipoEXPDer)
					return tipoT.tBool;
				else
					return tipoT.tError;
			default:
				if (esTipoNum(tipoEXPIzq) && esTipoNum(tipoEXPDer))
					return dameTipoDom(tipoEXPIzq, tipoEXPDer);
			}
		}
		
		if (esOp3(op)) {
			if (tipoEXPIzq == tipoT.tNat && tipoEXPDer == tipoT.tNat )
				return tipoT.tNat;
		}
		
		return tipoT.tError;
		
//		switch (op) {
//		case menor:
//			if (tipoEXPIzq == tipoEXPDer)
//			return tipoT.tBool;
//		case menorIgual:
//			return tipoT.tChar;
//		case mayor:
//			return tipoT.tNat;
//		case mayorIgual:
//			return tipoT.tInt;
//		case igual:
//			return tipoT.tFloat;
//		case distinto:
//			return tipoT.tFloat;
//		default:
//			return tipoT.tError;
//		}
	}
	
	public tipoT dameTipo(tipoT tipoEXP, tOp op4) {
		switch (op4) {
		case negArit:
			if (esTipoNum(tipoEXP) && tipoEXP != tipoT.tNat)
				return tipoEXP;
			if (tipoEXP == tipoT.tNat)
				return tipoT.tInt;
			else
				return tipoT.tError;
		case negLogica:
			if (tipoEXP == tipoT.tBool)
				return tipoEXP;
			else
				return tipoT.tError;
		case opVAbs:
			if (esTipoNum(tipoEXP) && !(tipoEXP == tipoT.tInt))
				return tipoEXP;
			if (tipoEXP == tipoT.tInt)
				return tipoT.tNat;
			return tipoT.tError;
		case castChar:
			if (tipoEXP == tipoT.tNat || tipoEXP == tipoT.tChar)
				return tipoT.tChar;
			return tipoT.tError;
		case castNat:
			if (tipoEXP == tipoT.tNat || tipoEXP == tipoT.tChar)
				return tipoT.tNat;
			return tipoT.tError;
		case castInt:
			if (esTipoNum(tipoEXP) || tipoEXP == tipoT.tChar)
				return tipoT.tInt;
			return tipoT.tError;
		case castFloat:
			if (esTipoNum(tipoEXP) || tipoEXP == tipoT.tChar)
				return tipoT.tFloat;
			return tipoT.tError;
		default:
			return tipoT.tError;
		}
	}
	
	public tipoT dameTipoDom(tipoT tipo1, tipoT tipo2) {
		if (tipo1 == tipoT.tFloat && esTipoNum(tipo2) || tipo2 == tipoT.tFloat && esTipoNum(tipo1))
			return tipoT.tFloat;
		if (tipo1 == tipoT.tInt && esTipoNatInt(tipo2) || tipo2 == tipoT.tInt && esTipoNatInt(tipo1))
			return tipoT.tInt;
		return tipoT.tNat;
	}
	
	public boolean esOp0(tOp op) {
		if (op == tOp.menor || op == tOp.menorIgual || op == tOp.mayor ||
				op == tOp.mayorIgual || op == tOp.igual || op == tOp.distinto)
			return true;
		else
			return false;
	}
	
	public boolean esTokenOp0(tToken tokOp) {
		if (tokOp == tToken.menor || tokOp == tToken.menorIgual || tokOp == tToken.mayor ||
				tokOp == tToken.mayorIgual || tokOp == tToken.igual || tokOp == tToken.distinto)
			return true;
		else
			return false;
	}
	
	public boolean esOp1(tOp op) {
		if (op == tOp.suma || op == tOp.resta || op == tOp.oLogica)
			return true;
		else
			return false;
	}
	
	public boolean esTokenOp1(tToken tokOp) {
		if (tokOp == tToken.suma || tokOp == tToken.resta || tokOp == tToken.oLogica)
			return true;
		else
			return false;
	}
	
	public boolean esOp2(tOp op) {
		if (op == tOp.multiplicacion || op == tOp.division || op == tOp.resto ||
				op == tOp.yLogica)
			return true;
		else
			return false;
	}
	
	public boolean esTokenOp2(tToken tokOp) {
		if (tokOp == tToken.multiplicacion || tokOp == tToken.division || tokOp == tToken.resto ||
				tokOp == tToken.yLogica)
			return true;
		else
			return false;
	}
	
	public boolean esOp3(tOp op) {
		if (op == tOp.despIzq || op == tOp.despDer)
			return true;
		else
			return false;
	}
	
	public boolean esTokenOp3(tToken tokOp) {
		if (tokOp == tToken.despIzq || tokOp == tToken.despDer)
			return true;
		else
			return false;
	}
	
	public boolean esTokenOp41(tToken tokOp) {
		if (tokOp == tToken.negArit || tokOp == tToken.negLogica || tokOp == tToken.castChar ||
				tokOp == tToken.castFloat || tokOp == tToken.castInt || tokOp == tToken.castNat)
			return true;
		else
			return false;
	}
	
	public tOp op0() {
		switch (tokActual.getTipoToken()) {
		case menor:
			consume(tToken.menor);
			return tOp.menor;
		case menorIgual:
			consume(tToken.menorIgual);
			return tOp.menorIgual;
		case mayor:
			consume(tToken.mayor);
			return tOp.mayor;
		case mayorIgual:
			consume(tToken.mayorIgual);
			return tOp.mayorIgual;
		case igual:
			consume(tToken.igual);
			return tOp.igual;
		case distinto:
			consume(tToken.distinto);
			return tOp.distinto;	
		default:
			return tOp.error;
		}
	}
	
	public tOp op1() {
		switch (tokActual.getTipoToken()) {
		case suma:
			consume(tToken.suma);
			return tOp.suma;
		case resta:
			consume(tToken.resta);
			return tOp.resta;
		case oLogica:
			consume(tToken.oLogica);
			return tOp.oLogica;
		default:
			return tOp.error;
		}
	}
	
	public tOp op2() {
		switch (tokActual.getTipoToken()) {
		case multiplicacion:
			consume(tToken.multiplicacion);
			return tOp.multiplicacion;
		case division:
			consume(tToken.division);
			return tOp.division;
		case resto:
			consume(tToken.resto);
			return tOp.resto;
		case yLogica:
			consume(tToken.yLogica);
			return tOp.yLogica;
		default:
			return tOp.error;
		}
	}
	
	public tOp op3() {
		switch (tokActual.getTipoToken()) {
		case despIzq:
			consume(tToken.despIzq);
			return tOp.despIzq;
		case despDer:
			consume(tToken.despDer);
			return tOp.despDer;
		default:
			return tOp.error;
		}
	}
	
	public tOp op41() {
		switch (tokActual.getTipoToken()) {
		case negArit:
			consume(tToken.negArit);
			return tOp.negArit;
		case negLogica:
			consume(tToken.negLogica);
			return tOp.negLogica;
		case castChar:
			consume(tToken.castChar);
			return tOp.castChar;
		case castNat:
			consume(tToken.castNat);
			return tOp.castNat;
		case castInt:
			consume(tToken.castInt);
			return tOp.castInt;
		case castFloat:
			consume(tToken.castFloat);
			return tOp.castFloat;
		default:
			return tOp.error;
		}
	}
	
//	public void rexp2() {
//		
//	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombreFichero = "prueba11.txt";
		
		ALexico scanner = new ALexico();
		ASintactico parser = new ASintactico();
		if (scanner.scanFichero(nombreFichero)) {
			parser.tokensIn = scanner.dameTokens();
			parser.tokActual = parser.tokensIn.get(parser.contTokens);
			parser.parse();
			parser.emit.write("programa.bin");
		}
	}
}