package analizadorLexico;

public enum tToken {
		tipoVarBooleano, tipoVarCadCaracteres, tipoVarNatural, tipoVarEntero,tipoVarReal,
		
		booleano, caracter, natural, entero, real, 			//cadCaracteres
		
		igual, mayor, menor, mayorIgual, menorIgual, distinto,
		
		separador, puntoyComa, dosPuntos, asignacion,
		
		suma, resta, negArit, multiplicacion, division, resto,
		
		opVAbs,
		
		tokenError,
		
		parApertura, parCierre,
		
		identificador,
		
		booleanoCierto, booleanoFalso,
		negLogica, oLogica, yLogica,
		
		castChar, castNat, castInt, castFloat,
		
		despIzq, despDer,
		
		entradaTeclado, salidaPantalla,
		
		finDeFichero,
		
		ifC, thenC, elseC,
		
		whileC, doC,
		
		forC, toC,
		
		bloqueApertura, bloqueCierre,
		
		newM, disposeM,
		
		pointerT, arrayT, ofT, recordT,
		
		puntero
}
