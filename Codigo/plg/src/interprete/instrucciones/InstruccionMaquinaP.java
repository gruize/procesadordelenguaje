package interprete.instruccionesMV;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public abstract class InstruccionMaquinaP {
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static byte MENOR =0X00;  
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static byte MAYOR=0X01; 
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static byte MENORIGUAL = 0X02;
	/**
	 * Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	 */
	public static byte MAYORIGUAL = 0X03;
	/**
	 * Se puede comprar todo
	 */
	public static byte IGUAL = 0X04;
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static byte SUMA = 0X05;  
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static byte RESTA = 0X06;
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static byte MULTIPLICACION = 0X07;
	/**
	 * se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	 */
	public static byte DIVISION = 0X08;
	/**
	 * solo se puede hacer entre naturales y enteros. El resultado es siempre un natural
	 */
	public static byte MODULO = 0X09;
	/**
	 * solo se puede hacer entre booleanos
	 */
	public static byte YLOGICO = 0X10; 
	/**
	 * solo se puede hacer entre booleanos
	 */
	public static byte OLOGICO = 0X11;
	/**
	 * solo se puede hacer entre booleanos
	 */
	public static byte NOLOGICO = 0X12;
	/**
	 * se puede hacer entre enteros y floats
	 */
	public static byte SIGNO = 0X13; 
	/**
	 * Solo se puede hacer entre enteros y naturales
	 */
	public static byte DESPLAZAMIENTOIZQUIERDA = 0X14;
	/**
	 * Solo se puede hacer entre enteros y naturales
	 */
	public static byte DESPLAZAMIENTODERECHA= 0X15;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static byte CASTNAT = 0X16;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static byte CASTINT=0X17;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static byte CASTCHAR= 0X18;
	/**
	 * se puede hacen entre naturales, enteros, reales y caracteres
	 */
	public static byte CASTFLOAT=0X19;
	/**
	 *Tiene un parametro que es un tipo de elemento 
	 */
	public static byte APILA= 0X20; 
	/**
	 * Tiene un parametro que es la direccion
	 */
	public static byte APILA_DIR = 0X21;
	/**
	 * NO tiene parametro
	 */
	public static byte DESAPILA = 0X22;
	/**
	 * Tiene un parametro que es una direccion
	 */
	public static byte DESAPILA_DIR= 0X23; 
	public static byte LEER=0X24;
	public static byte ESCRIBIR =0X25;
	public abstract boolean exec(Stack<StackObject> p, Memoria m);
	public abstract byte[] toBytes();
	public InstruccionMaquinaP fromBytes(byte[] bytes,int pos){
		return null;
	}
	public abstract int size();
	
	
}
