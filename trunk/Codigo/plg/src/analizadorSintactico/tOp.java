package analizadorSintactico;

public enum tOp {
	//Operadores de OP0
	igual, mayor, menor, mayorIgual, menorIgual, distinto,
	//Operadores de OP1
	suma, resta, oLogica,
	//Operadores de OP2
	multiplicacion, division, resto, yLogica,
	//Operadores de OP4
	negArit, negLogica, opVAbs, castChar, castNat, castInt, castFloat
}
