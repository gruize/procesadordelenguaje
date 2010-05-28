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
	DESAPILA_IND,
	LEER,
	ESCRIBIR,
	APILA_IND, // Sin parametro
	IR_A,// 1 parámetro dirección destino
	IR_F,// 1 parámetro dirección destino si salto
	IR_V,// 1 parámetro dirección destino si salto
	IR_IND,//Sin parametro
	MUEVE,//1 parametro tamaño a desplazar
	COPIA, // sin parámetro, se duplica la cima de la pila
	NEW,
	DEL,
	DESAPILA_IND_FLOAT,
	DESAPILA_INT_NATURAL,
	DESAPILA_INT_ENTERO,
	DESAPILA_INT_CHAR,
	DESAPILA_INT_BOOLEAN
	
}
