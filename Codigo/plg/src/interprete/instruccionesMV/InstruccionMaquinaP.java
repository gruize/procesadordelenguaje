package interprete.instruccionesMV;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public abstract class InstruccionMaquinaP {
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static final byte MENOR =0X00;  
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static final byte MAYOR=0X01; 
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static final byte MENORIGUAL = 0X02;
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static final byte MAYORIGUAL = 0X03;
	/**
	 * Se puede comprar todo
	 */
	public static final byte IGUAL = 0X04;
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static final byte SUMA = 0X05;  
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static final byte RESTA = 0X06;
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static final byte PRODUCTO = 0X07;
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static final byte DIVISION = 0X08;
	/**
	 * solo se puede hacer entre naturales y enteros. El resultado es siempre un natural
	 */
	public static final byte MODULO = 0X09;
	/**
	 * solo se puede hacer entre booleanos
	 */
	public static final byte YLOGICO = 0X10; 
	/**
	 * solo se puede hacer entre booleanos
	 */
	public static final byte OLOGICO = 0X11;
	/**
	 * solo se puede hacer entre booleanos
	 */
	public static final byte NOLOGICO = 0X12;
	/**
	 * se puede hacer entre enteros y floats
	 */
	public static final byte SIGNO = 0X13; 
	/**
	 * Solo se puede hacer entre enteros y naturales
	 */
	public static final byte DESPLAZAMIENTOIZQUIERDA = 0X14;
	/**
	 * Solo se puede hacer entre enteros y naturales
	 */
	public static final byte DESPLAZAMIENTODERECHA= 0X15;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static final byte CASTNAT = 0X16;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static final byte CASTINT=0X17;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static final byte CASTCHAR= 0X18;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static final byte CASTFLOAT=0X19;
	/**
	 *Tiene un parametro que es un tipo de elemento 
	 */
	public static final byte APILA= 0X20; 
	/**
	 * Tiene un parametro que es la direccion
	 */
	public static final byte APILA_DIR = 0X21;
	/**
	 * NO tiene parametro
	 */
	public static final byte DESAPILA = 0X22;
	/**
	 * Tiene un parametro que es una direccion
	 */
	public static final byte DESAPILA_DIR= 0X23; 
	public static final byte LEER=0X24;
	public static final byte ESCRIBIR =0X25;
	public static final byte VALOR_ABSOLUTO = 0X26;
	public static final byte STOP = 0X27;
	public abstract boolean exec(Stack<StackObject> p, Memoria m);
	public abstract byte[] toBytes();
	public InstruccionMaquinaP fromBytes(byte[] bytes,int pos){
		if (bytes.length <= pos)
			return null;
		switch (bytes[pos]) {
		case MENOR:			
			return new Menor().fromBytes(bytes, pos); 
		case MAYOR:			
			return new Mayor().fromBytes(bytes, pos);
		case MENORIGUAL:	
			return new MenorIgual().fromBytes(bytes, pos);
		case MAYORIGUAL:	
			return new MayorIgual().fromBytes(bytes, pos);
		case IGUAL:			
			return new Igual().fromBytes(bytes, pos);
		case SUMA:						
			return new Suma().fromBytes(bytes, pos);
		case RESTA:						
			return new Resta().fromBytes(bytes, pos);
		case PRODUCTO:
			return new Producto().fromBytes(bytes, pos);
		case DIVISION:
			return new Division().fromBytes(bytes, pos);
		case MODULO: 
			return new Modulo().fromBytes(bytes, pos);
		case YLOGICO:
			return new And().fromBytes(bytes, pos);
		case OLOGICO:
			return new Or().fromBytes(bytes, pos);
		case NOLOGICO:
			return new Not().fromBytes(bytes, pos);
		case SIGNO:
			return new Signo().fromBytes(bytes, pos);
		case DESPLAZAMIENTOIZQUIERDA:
			return new DesplazamientoIzquierda().fromBytes(bytes, pos);
		case DESPLAZAMIENTODERECHA:
			return new DesplazamientoDerechas().fromBytes(bytes, pos);
		case CASTNAT:
			return new CastNatural().fromBytes(bytes, pos);
		case CASTINT:
			return new CastInteger().fromBytes(bytes, pos);
		case CASTCHAR:
			return new CastChar().fromBytes(bytes, pos);
		case CASTFLOAT:
			return new CastFloat().fromBytes(bytes, pos);
		case APILA:
			return new Apila().fromBytes(bytes, pos);
		case APILA_DIR:
			return new ApilaDir().fromBytes(bytes, pos);
		case DESAPILA:
			return new Desapila().fromBytes(bytes, pos);
		case DESAPILA_DIR:
			return new DesapilaDir().fromBytes(bytes, pos);
		case LEER:
			return new Leer().fromBytes(bytes, pos);
		case ESCRIBIR:
			return new Escribir().fromBytes(bytes, pos);
		default:
			return null;

		}
	}
	public abstract int size();
	
	
}
