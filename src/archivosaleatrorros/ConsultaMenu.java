/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivosaleatrorros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johan Sánchez
 */
public class ConsultaMenu {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            RandomAccessFile listaE = new RandomAccessFile("listaEstudiantes.txt", "rw");
            RandomAccessFile cursos = new RandomAccessFile("cursos.txt", "rw");
            RandomAccessFile matriculas = new RandomAccessFile("matriculas.txt", "rw");

            //Cursos predeterminados
            cursos.writeInt(101);
            cursos.writeUTF("Bases de Datos");
            cursos.writeInt(201);
            cursos.writeUTF("Multimediales");
            cursos.writeInt(301);
            cursos.writeUTF("Telematica");
            cursos.writeInt(401);
            cursos.writeUTF("Matemáticas Especiales");
            cursos.writeInt(501);
            cursos.writeUTF("Bases de Datos");
            cursos.writeInt(601);
            cursos.writeUTF("Ondas y Campos");
            cursos.writeInt(701);
            cursos.writeUTF("Ingles");
int casos =0;
do{
            System.out.println("Consulta");
            System.out.println("1. Crear persona");
            System.out.println("2. Todos los estudiantes");
            System.out.println("3. Datos de un estudiante");
            System.out.println("4. Salir");
            casos=sc.nextInt();
            switch(casos){
                case 1:
                    System.out.println("Cedula: ");
                    int cedula= sc.nextInt();
                    System.out.println("Nombre: ");
                    String nombre = sc2.nextLine();
                    
                    System.out.println("Edad: ");
                    int edad = sc.nextInt();
                    System.out.println("Codigo de la materia: ");
                    int codigo = sc.nextInt();
                    
                    escribir(cedula, nombre, edad, codigo, matriculas, listaE);
                    break;
                case 2:
                    ListaEstudiantes(listaE);
                    break;
                case 3:
                    System.out.println("Cedula: ");
                    int cedula2= sc.nextInt();
                    unaPersona(cedula2, listaE, matriculas, cursos);
                    break;
                case 4:
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("ERROR!!");
                    break;
            }
                
            
}while(casos!=4);
} catch (IOException e) {
            //Logger.getLogger(ConsultaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void escribir(int cedula, String nombre,
            int edad, int codigo, RandomAccessFile matriculas, RandomAccessFile listaE) {

        try {
            listaE.writeInt(cedula);listaE.writeUTF(nombre);
            listaE.write(edad);
            matriculas.writeInt(cedula); matriculas.writeInt(codigo);
            
        } catch (IOException ex) {
            Logger.getLogger(ConsultaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    private static void ListaEstudiantes(RandomAccessFile listaE) {
 try {
            listaE.seek(4);
            //System.out.println("Codigo        Cursos");
            System.out.println("Nombres");
            while (listaE.getFilePointer() < listaE.length()) {
                System.out.println(listaE.readUTF());
                listaE.skipBytes(8);
            }
        } catch (IOException ex) {
            Logger.getLogger(ConsultaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void unaPersona(int cedula,RandomAccessFile listaE, RandomAccessFile matriculas, RandomAccessFile cursos) {

        try {
            listaE.seek(0);
            //System.out.println("Codigo        Cursos");
            while (listaE.getFilePointer() < listaE.length()) {
                int c = listaE.readInt();
                if(c==cedula){
                    System.out.println("Nombre:    "+listaE.readUTF());
                    System.out.println("Edad:      "+listaE.readInt());
                    matriculas.seek(0);
                    while(matriculas.getFilePointer()< matriculas.length()){
                        int codigo = matriculas.readInt();
                       if(codigo==cedula){
                    System.out.println("Codigo C:  "+codigo);
                    int cc=matriculas.readInt();
                    cursos.seek(0);
                    while(cursos.getFilePointer()<cursos.length()){
                        if(cursos.readInt()==cc){
                    System.out.println("Curso:     "+cursos.readUTF());
                            break;
                        }
                        cursos.skipBytes(22);
                    }
                    break;
                       } 
                       matriculas.skipBytes(4);
                    }
        
                    break;
                }
                listaE.skipBytes(26);
            }
        } catch (Exception e) {
        }
    }

    private static void leerCursos(RandomAccessFile cursos) {
        try {
            cursos.seek(0);
            System.out.println("Codigo        Cursos");
            while (cursos.getFilePointer() < cursos.length()) {
                System.out.println(cursos.readInt() + "           " + cursos.readUTF());
            }
        } catch (IOException ex) {
            Logger.getLogger(ConsultaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
