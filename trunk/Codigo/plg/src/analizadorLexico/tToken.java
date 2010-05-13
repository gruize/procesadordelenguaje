package analizadorLexico;

public enum tToken {
		tipoVarBooleano, tipoVarCadCaracteres, tipoVarNatural, tipoVarEntero,tipoVarReal,
		
		booleano, caracter, natural, entero, real, 			//cadCaracteres
		
		igual, mayor, menor, mayorIgual, menorIgual, distinto,
		
		separador, punto, puntoyComa, dosPuntos, asignacion,
		
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
		
		llaveApertura, llaveCierre,
		
		newM, disposeM, nullM,
		
		pointerT, arrayT, ofT, recordT,
		
		puntero,
		
		corApertura, corCierre,
		
		var, forward, procedure, decTipo
}
