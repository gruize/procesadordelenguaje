package util;

public enum OperacionesMaquinaP {
	MENOR, // Se pueden comparar todos los numeros en general y ademas caracteres entre ellos
	MAYOR,
	MENORIGUAL,
	MAYORIGUAL,
	DISTINTO, // Se pueden mirar si dos boolean son iguales
	SUMA, // se pueden operar entre numeros el resultado es siempre el tipo de menos nivel de la operacion
	RESTA,
	MULTIPLICACION,
	DIVISION,
	MODULO, // solo se puede hacer entre naturales y enteros. El resultado es siempre un natural
	YLOGICO, // solo se puede hacer entre booleanos
	OLOGICO, // "
	NOLOGICO, // "
	SIGNO, // se puede hacer entre enteros y floats
	DESPLAZAMIENTOIZQUIERDA, // Solo se puede hacer entre enteros y naturales
	DESPLAZAMIENTODERECHA,// Solo se puede hacer entre enteros y naturales
	CASTNAT, // se puede hacen entre naturales, enteros, reales y caracteres
	CASTINT,// se puede hacen entre naturales, enteros, reales y caracteres
	CASTCHAR,// se puede hacen entre naturales, enteros, reales y caracteres
	CASTFLOAT,// se puede hacen entre naturales, enteros, reales y caracteres
	APILA, // Tiene un parametro que es un tipo de elemento
	APILA_DIR, // Tiene un parametro que es la direccion
	DESAPILA, // no Tiene parametro
	DESAPILA_DIR, // Tiene un parametro que es una direccion
	LEER,
	ESCRIBIR
}
