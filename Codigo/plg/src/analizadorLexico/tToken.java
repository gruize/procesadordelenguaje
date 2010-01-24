package analizadorLexico;

public enum tToken {
		tipoVarBooleano, tipoVarCadCaracteres, tipoVarNatural, tipoVarEntero,tipoVarReal,
		
		booleano, cadCaracteres, natural, entero, real,
		
		igual, mayor, menor, mayorIgual, menorIgual, distinto,
		
		separador, puntoyComa, dosPuntos, asignacion,
		
		suma, resta, negArit, multiplicacion, division, resto,
		
		tokenError,
		
		parApertura, parCierre,
		
		identificador,
		
		booleanoCierto, booleanoFalso,
		negLogica, oLogica, yLogica,
		
		castChar, castNat, castInt, castFloat,
		
		despIzq, despDer,
		
		entradaTeclado, salidaPantalla,
		
		finDeFichero
}
