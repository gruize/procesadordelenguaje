package tablaSimbolos;

public enum tipoT {
	//1º Cuat
	tBool,
	tChar,
	tNat,
	tInt,
	tFloat,
	tError,
	tInicial,
	//2º Cuat
	puntero,
	registro,
	array,
	procedimiento,
	referencia,
	//Tipo para los punteros a null
	tNull,
	//Tipo para las declaraciones de punteros con tipo base desconocido en el momento
	pendiente
}
