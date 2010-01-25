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
	MODULO,
	YLOGICO,
	OLOGICO,
	NOLOGICO,
	SIGNO,
	DESPLAZAMIENTOIZQUIERDA,
	DESPLAZAMIENTODERECHA,
	CASTNAT,
	CASTINT,
	CASTCHAR,
	CASTFLOAT,
	APILA,
	APILA_DIR,
	DESAPILA,
	DESAPILA_DIR,
	LEER,
	ESCRIBIR
}
