package interprete.instruccionesMV;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public abstract class InstruccionMaquinaP extends InstruccionesMaquinaPConstantes{

	public static final int MAX_SIZE = 10;
	public abstract int exec(Stack<StackObject> p, Memoria m, Integer counter);
	public abstract byte[] toBytes();
	public static InstruccionMaquinaP fromBytes(byte[] bytes,int pos){
		if (bytes.length <= pos)
			return null;
		switch (bytes[pos]) {
		case MENOR:			
			return Menor.fromBytes(bytes, pos); 
		case MAYOR:			
			return Mayor.fromBytes(bytes, pos);
		case MENORIGUAL:	
			return MenorIgual.fromBytes(bytes, pos);
		case MAYORIGUAL:	
			return MayorIgual.fromBytes(bytes, pos);
		case DISTINTO:			
			return Distinto.fromBytes(bytes, pos);
		case SUMA:						
			return Suma.fromBytes(bytes, pos);
		case RESTA:						
			return Resta.fromBytes(bytes, pos);
		case PRODUCTO:
			return Producto.fromBytes(bytes, pos);
		case DIVISION:
			return Division.fromBytes(bytes, pos);
		case MODULO: 
			return Modulo.fromBytes(bytes, pos);
		case YLOGICO:
			return And.fromBytes(bytes, pos);
		case OLOGICO:
			return Or.fromBytes(bytes, pos);
		case NOLOGICO:
			return Not.fromBytes(bytes, pos);
		case SIGNO:
			return Signo.fromBytes(bytes, pos);
		case DESPLAZAMIENTOIZQUIERDA:
			return DesplazamientoIzquierda.fromBytes(bytes, pos);
		case DESPLAZAMIENTODERECHA:
			return DesplazamientoDerechas.fromBytes(bytes, pos);
		case CASTNAT:
			return CastNatural.fromBytes(bytes, pos);
		case CASTINT:
			return CastInteger.fromBytes(bytes, pos);
		case CASTCHAR:
			return CastChar.fromBytes(bytes, pos);
		case CASTFLOAT:
			return CastFloat.fromBytes(bytes, pos);
		case APILA:
			return Apila.fromBytes(bytes, pos);
		case APILA_DIR:
			return ApilaDir.fromBytes(bytes, pos);
		case DESAPILA:
			return Desapila.fromBytes(bytes, pos);
		case DESAPILA_DIR_BOOLEAN:
			return DesapilaDirBoolean.fromBytes(bytes, pos);
		case DESAPILA_DIR_NATURAL:
			return DesapilaDirNatural.fromBytes(bytes, pos);
		case DESAPILA_DIR_INTEGER:
			return DesapilaDirEntero.fromBytes(bytes, pos);
		case DESAPILA_DIR_FLOAT:
			return DesapilaDirFloat.fromBytes(bytes, pos);
		case DESAPILA_DIR_CHAR:
			return DesapilaDirChar.fromBytes(bytes, pos);
		case LEER:
			return Leer.fromBytes(bytes, pos);
		case ESCRIBIR:
			return Escribir.fromBytes(bytes, pos);
		case VALOR_ABSOLUTO:
			return ValorAbsoluto.fromBytes(bytes, pos);
		case STOP: 
			return Stop.fromBytes(bytes, pos);
		default:
			return null;

		}
	}
	public abstract int size();
	
	
}
