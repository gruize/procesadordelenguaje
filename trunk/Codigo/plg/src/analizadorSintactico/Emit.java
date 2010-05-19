package analizadorSintactico;


import interprete.instruccionesMV.And;
import interprete.instruccionesMV.Apila;
import interprete.instruccionesMV.ApilaDir;
import interprete.instruccionesMV.CastChar;
import interprete.instruccionesMV.CastFloat;
import interprete.instruccionesMV.CastInteger;
import interprete.instruccionesMV.CastNatural;
import interprete.instruccionesMV.Desapila;
import interprete.instruccionesMV.DesapilaDirBoolean;
import interprete.instruccionesMV.DesapilaDirChar;
import interprete.instruccionesMV.DesapilaDirEntero;
import interprete.instruccionesMV.DesapilaDirFloat;
import interprete.instruccionesMV.DesapilaDirNatural;
import interprete.instruccionesMV.DesplazamientoDerechas;
import interprete.instruccionesMV.DesplazamientoIzquierda;
import interprete.instruccionesMV.Division;
import interprete.instruccionesMV.Escribir;
import interprete.instruccionesMV.Distinto;
import interprete.instruccionesMV.InstruccionMaquinaP;
import interprete.instruccionesMV.InstruccionesMaquinaPConstantes;
import interprete.instruccionesMV.Leer;
import interprete.instruccionesMV.Mayor;
import interprete.instruccionesMV.MayorIgual;
import interprete.instruccionesMV.Menor;
import interprete.instruccionesMV.MenorIgual;
import interprete.instruccionesMV.Modulo;
import interprete.instruccionesMV.Not;
import interprete.instruccionesMV.Or;
import interprete.instruccionesMV.Producto;
import interprete.instruccionesMV.Resta;
import interprete.instruccionesMV.Signo;
import interprete.instruccionesMV.Stop;
import interprete.instruccionesMV.Suma;
import interprete.instruccionesMV.ValorAbsoluto;
import interprete.tipos.MyBoolean;
import interprete.tipos.MyBuffer;
import interprete.tipos.MyChar;
import interprete.tipos.MyFloat;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Vector;

import analizadorLexico.Token;
import analizadorLexico.tToken;
import util.Globales;
import tablaSimbolos.*;

public class Emit extends InstruccionesMaquinaPConstantes{
	Vector <InstruccionMaquinaP> codigo;
	public Emit(){
		codigo = new Vector<InstruccionMaquinaP>();
	}
	public boolean emit(byte code){
		return emit(code,null);
	}
	public byte desapilaCode(tipoT tipo){
		switch (tipo) {
		case tBool:
			return DESAPILA_DIR_BOOLEAN;
		case tNat:
			return DESAPILA_DIR_NATURAL;
		case tInt:
			return DESAPILA_DIR_INTEGER;
		case tFloat:
			return DESAPILA_DIR_FLOAT;
		case tChar:
			return DESAPILA_DIR_CHAR;

			
			



		default:
			return -1;
		}
	}
	public boolean emit(byte code, Token token){
		InstruccionMaquinaP i = factory(code, token);
		if (i == null){
			System.err.println("Error de traduccion.");
			return false;
		}
		codigo.add(i);
		return true;
	}
	public void write(String fileName){
		
		try {
//			FileOutputStream fileOutput;
			FileOutputStream fileOutput = new FileOutputStream (fileName);
//			BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
			for (int i = 0; i < codigo.size();i++){
				byte[] code = codigo.get(i).toBytes();
				if (Globales.debug)
					System.out.println(code);
				fileOutput.write(code,0,code.length);
			}
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public InstruccionMaquinaP factory(byte code, Token token){
		switch (code) {
		case MENOR:			
			return new Menor(); 
		case MAYOR:			
			return new Mayor();
		case MENORIGUAL:	
			return new MenorIgual();
		case MAYORIGUAL:	
			return new MayorIgual();
		case DISTINTO:			
			return new Distinto();
		case SUMA:						
			return new Suma();
		case RESTA:						
			return new Resta();
		case PRODUCTO:
			return new Producto();
		case DIVISION:
			return new Division();
		case MODULO: 
			return new Modulo();
		case YLOGICO:
			return new And();
		case OLOGICO:
			return new Or();
		case NOLOGICO:
			return new Not();
		case SIGNO:
			return new Signo();
		case DESPLAZAMIENTOIZQUIERDA:
			return new DesplazamientoIzquierda();
		case DESPLAZAMIENTODERECHA:
			return new DesplazamientoDerechas();
		case CASTNAT:
			return new CastNatural();
		case CASTINT:
			return new CastInteger();
		case CASTCHAR:
			return new CastChar();
		case CASTFLOAT:
			return new CastFloat();
		case APILA:
			if (token != null){
				MyBuffer bf = new MyBuffer();
				bf.setValue(token.getLexema());
				StackObject o = null;
				if (token.getTipoToken() == tToken.booleano)
					o = new MyBoolean();
				if (token.getTipoToken() == tToken.caracter)
					o = new MyChar();
				if (token.getTipoToken() == tToken.entero)
					o = new MyInteger();
				if (token.getTipoToken() == tToken.natural)
					o = new MyNatural();
				if (token.getTipoToken() == tToken.real)
					o = new MyFloat();
				if (o.fromBuffer(bf)){
					return new Apila(o);
				}
					
				return null;				
			}
			else return null;
			
		case APILA_DIR:
			if (token != null){
				Integer i = Integer.valueOf(token.getLexema());
				return new ApilaDir(i);
			}
			else return null;
			
		case DESAPILA:
			return new Desapila();
		case DESAPILA_DIR_BOOLEAN:
			if (token != null){
				Integer i = Integer.valueOf(token.getLexema());
				return new DesapilaDirBoolean(i);
			}
			else return null;
		case DESAPILA_DIR_INTEGER:
			if (token != null){
				Integer i = Integer.valueOf(token.getLexema());
				return new DesapilaDirEntero(i);
			}
			else return null;
		case DESAPILA_DIR_NATURAL:
			if (token != null){
				Integer i = Integer.valueOf(token.getLexema());
				return new DesapilaDirNatural(i);
			}
			else return null;
		case DESAPILA_DIR_FLOAT:
			if (token != null){
				Integer i = Integer.valueOf(token.getLexema());
				return new DesapilaDirFloat(i);
			}
			else return null;
		case DESAPILA_DIR_CHAR:
			if (token != null){
				Integer i = Integer.valueOf(token.getLexema());
				return new DesapilaDirChar(i);
			}
			else return null;
		case LEER:
			return new Leer();
		case ESCRIBIR:
			return new Escribir();
		case VALOR_ABSOLUTO :
			return new ValorAbsoluto();
		case STOP:
			return new Stop();
		default:
			return null;

		}
	}
	


}
