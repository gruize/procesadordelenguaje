package analizadorSintactico;

import interprete.instruccionesMV.And;
import interprete.instruccionesMV.Apila;
import interprete.instruccionesMV.ApilaDir;
import interprete.instruccionesMV.CastChar;
import interprete.instruccionesMV.CastFloat;
import interprete.instruccionesMV.CastInteger;
import interprete.instruccionesMV.CastNatural;
import interprete.instruccionesMV.Desapila;
import interprete.instruccionesMV.DesapilaDir;
import interprete.instruccionesMV.DesplazamientoDerechas;
import interprete.instruccionesMV.DesplazamientoIzquierda;
import interprete.instruccionesMV.Division;
import interprete.instruccionesMV.Escribir;
import interprete.instruccionesMV.Igual;
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
import interprete.instruccionesMV.Suma;
import interprete.tipos.MyBoolean;
import interprete.tipos.MyBuffer;
import interprete.tipos.StackObject;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Vector;

import analizadorLexico.Token;
import analizadorLexico.tToken;

public class Emit extends InstruccionesMaquinaPConstantes{
	Vector <InstruccionMaquinaP> codigo;
	public Emit(){
		codigo = new Vector<InstruccionMaquinaP>();
	}
	public boolean emit(byte code){
		return emit(code,null);
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
		FileOutputStream fileOutput;
		try {
			fileOutput = new FileOutputStream (fileName);
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
			for (int i = 0; i < codigo.size();i++){
				bufferedOutput.write(codigo.get(i).toBytes());
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
		case IGUAL:			
			return new Igual();
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
				if (token.getTipoToken() == tToken.cadCaracteres)
					o = new MyBoolean();
				if (token.getTipoToken() == tToken.entero)
					o = new MyBoolean();
				if (token.getTipoToken() == tToken.natural)
					o = new MyBoolean();
				if (token.getTipoToken() == tToken.real)
					o = new MyBoolean();
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
		case DESAPILA_DIR:
			if (token != null){
				Integer i = Integer.valueOf(token.getLexema());
				return new DesapilaDir(i);
			}
			else return null;
		case LEER:
			return new Leer();
		case ESCRIBIR:
			return new Escribir();
		default:
			return null;

		}
	}
	


}
