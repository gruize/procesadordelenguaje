package analizadorSintactico;

import interprete.instruccionesMV.InstruccionesMaquinaPConstantes;

public class opUtils {
	public static byte getOperationCode(tOp op){
		switch (op) {
		case igual :
			return InstruccionesMaquinaPConstantes.DISTINTO;
		case mayor :
			return InstruccionesMaquinaPConstantes.MAYOR;
		case menor :
			return InstruccionesMaquinaPConstantes.MENOR;
		case mayorIgual :
			return InstruccionesMaquinaPConstantes.MAYORIGUAL;
		case menorIgual :
			return InstruccionesMaquinaPConstantes.MENORIGUAL;
		case distinto :
			return InstruccionesMaquinaPConstantes.DISTINTO;
		//Operadores de OP1
		case suma :
			return InstruccionesMaquinaPConstantes.SUMA;
		case resta :
			return InstruccionesMaquinaPConstantes.RESTA;
		case oLogica :
			return InstruccionesMaquinaPConstantes.OLOGICO;
		//Operadores de OP2
		case multiplicacion :
			return InstruccionesMaquinaPConstantes.PRODUCTO;
		case division :
			return InstruccionesMaquinaPConstantes.DIVISION;
		case resto :
			return InstruccionesMaquinaPConstantes.MODULO;
		case yLogica :
			return InstruccionesMaquinaPConstantes.YLOGICO;
		//Operadores de OP4
		case negArit :
			return InstruccionesMaquinaPConstantes.SIGNO;
		case negLogica :
			return InstruccionesMaquinaPConstantes.NOLOGICO;
		case opVAbs :
			return InstruccionesMaquinaPConstantes.VALOR_ABSOLUTO;
		case castChar :
			return InstruccionesMaquinaPConstantes.CASTCHAR;
		case castNat :
			return InstruccionesMaquinaPConstantes.CASTNAT;
		case castInt :
			return InstruccionesMaquinaPConstantes.CASTINT;
		case castFloat :		
			return InstruccionesMaquinaPConstantes.CASTFLOAT;
		}
		return -1;
	}
}
