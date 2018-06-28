/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import swing.Inicio;



/**
 *
 * @author Urbano
 */
public class ThreadPrincipal extends Thread{
    @Override
    public void run(){
        Inicio.mostrarLabelsInformativos();
        final int FIN_BUCLE = Integer.valueOf(Inicio.numeroIteracionesTxt.getText());
        Inicio.iteracionesTotalesLabel.setText(String.valueOf(FIN_BUCLE));
        Inicio.barraProgresoGeneral.setMaximum(FIN_BUCLE);
        Inicio.consolaTextPane.setName("Historial de iteraciones");
        GregorianCalendar inicio = new GregorianCalendar();
        Inicio.consolaTextPane.setText("Iniciando proceso con " + FIN_BUCLE + " iteraciones...");
        for (int i = 0; i <= FIN_BUCLE; i++) {
            System.out.print("Iteracion " + i + " de " + FIN_BUCLE + " // " + (i * 100) / FIN_BUCLE + " % completado. ");
//            ThreadEscribir escribe = new ThreadEscribir();
//            escribe.run();
            Inicio.consolaTextPane.append("\n" + "Iteracion " + i + " de " + FIN_BUCLE + " // " + (i * 100) / FIN_BUCLE + " % completado. ");
            Inicio.consolaTextPane.setCaretPosition(Inicio.consolaTextPane.getDocument().getLength());
            Inicio.iteracionActualLabel.setText(String.valueOf(i));
            Inicio.porcentajeActualLabel.setText(String.valueOf((i * 100) / FIN_BUCLE));
            Inicio.barraProgresoGeneral.setValue(i);
            GregorianCalendar transcurso = new GregorianCalendar();
            long transcurrido = (transcurso.getTimeInMillis() - inicio.getTimeInMillis());
            System.out.println("Han transcurrido " + transcurrido + " milisegundos");
            
            //Para un buen cronometro
            //Cuando pasa un minuto, se deben volver a cero los segundos
            //Cuando pasa una hora, se deben volver a cero los minutos
            //A segundos tenemos que quitarle 59 segundos por cada minuto que haya pasado
            //Igual con horas y minutos
            
            int segundosTranscurridos = (int) (transcurrido / 1000);
            int minutosTranscurridos = (int) (transcurrido / 1000 / 60);
            int horasTranscurridas = (int) (transcurrido / 1000 / 60 / 60);
            if(minutosTranscurridos > 0) {
                segundosTranscurridos = segundosTranscurridos - (minutosTranscurridos * 60);
            }
            if(horasTranscurridas > 0){
                minutosTranscurridos = minutosTranscurridos - (horasTranscurridas * 60);
            }
            Inicio.horasLabel.setText(String.valueOf(transcurrido / 1000 / 60 / 60));
            Inicio.minutosLabel.setText(String.valueOf(minutosTranscurridos));
            Inicio.segundosLabel.setText(String.valueOf(segundosTranscurridos));
        }
        GregorianCalendar fin = new GregorianCalendar();
        long tiempo = fin.getTimeInMillis() - inicio.getTimeInMillis();
        double tiempoHoras = tiempo / 1000 / 60 / 60;
        File carpetaResultados = new File("Historial");
        if(!carpetaResultados.exists() || !carpetaResultados.isDirectory()){
            carpetaResultados = new File("Historial");
            carpetaResultados.mkdir();
        }
        String fechaHoy = inicio.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (inicio.get(GregorianCalendar.MONTH) + 1) + "-" + inicio.get(GregorianCalendar.YEAR);
        File carpeta = new File(carpetaResultados + "/" + fechaHoy);
        if(!carpeta.exists() || !carpeta.isDirectory()){
            //No existe ni como archivo ni como directorio
            carpeta = new File(carpetaResultados + "/" + fechaHoy);
            carpeta.mkdir();
        }
        File f = new File(carpetaResultados + "/" + fechaHoy + "/Resultados " + FIN_BUCLE + " iteraciones.txt");
        boolean archivoExiste = false;
        archivoExiste = f.exists();
        int contador = 2;
        while (archivoExiste) {
            f = new File(carpetaResultados + "/" + fechaHoy + "/Resultados " + FIN_BUCLE + " iteraciones" + contador + ".txt");
            archivoExiste = f.exists();
            contador++;
        }
        //Escritura
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            int diaInicio = inicio.get(GregorianCalendar.DAY_OF_MONTH);
            int mesInicio = inicio.get(GregorianCalendar.MONTH) + 1;
            int añoInicio = inicio.get(GregorianCalendar.YEAR);
            int horaInicio = inicio.get(GregorianCalendar.HOUR_OF_DAY);
            int minutoInicio = inicio.get(GregorianCalendar.MINUTE);
            int segundoInicio = inicio.get(GregorianCalendar.SECOND);

            int diaFin = fin.get(GregorianCalendar.DAY_OF_MONTH);
            int mesFin = fin.get(GregorianCalendar.MONTH) + 1;
            int añoFin = fin.get(GregorianCalendar.YEAR);
            int horaFin = fin.get(GregorianCalendar.HOUR_OF_DAY);
            int minutoFin = fin.get(GregorianCalendar.MINUTE);
            int segundoFin = fin.get(GregorianCalendar.SECOND);

            wr.write("ITERACIONES TOTALES COMPLETADAS : " + FIN_BUCLE);
            wr.append("\n");
            wr.append("\nINSTANTES DE INICIO Y FIN :::");
            wr.append("\n");
            wr.append("\n\tFECHA INICIO : " + diaInicio + "/" + mesInicio + "/" + añoInicio + " - " + horaInicio + ":" + minutoInicio + ":" + segundoInicio);//escribimos en el archivo
            wr.append("\n\tFECHA FIN    : " + diaFin + "/" + mesFin + "/" + añoFin + " - " + horaFin + ":" + minutoFin + ":" + segundoFin);
            wr.append("\n\tMILISEGUNDO DE INICIO : " + inicio.getTimeInMillis());//concatenamos en el archivo sin borrar lo existente
            wr.append("\n\tMILISEGUNDO DE FIN    : " + fin.getTimeInMillis());
            wr.append("\n");
            wr.append("\nDURACIÓN DE LA OPERACIÓN:::");
            wr.append("\n");
            wr.append("\n\tDURACIÓN EN MILISEGUNDOS : " + (fin.getTimeInMillis() - inicio.getTimeInMillis()));
            wr.append("\n\tDURACIÓN DE EN SEGUNDOS  : " + (tiempo / 1000));
            wr.append("\n\tDURACIÓN DE EN MINUTOS   : " + ((tiempo / 1000) / 60));
            wr.append("\n\tDURACIÓN DE EN HORAS     : " + tiempoHoras);
            wr.append("\n");
            wr.append("\nITERACIONES POR UNIDAD DE TIEMPO :::");
            wr.append("\n");
            wr.append("\n\tITERACIONES POR MILISEGUNDO : " + (FIN_BUCLE / (fin.getTimeInMillis() - inicio.getTimeInMillis())));
            if (((fin.getTimeInMillis() - inicio.getTimeInMillis()) / 1000) > 0) {
                wr.append("\n\tITERACIONES POR SEGUNDO     : " + (FIN_BUCLE / ((fin.getTimeInMillis() - inicio.getTimeInMillis()) / 1000)));
            }
            if ((((fin.getTimeInMillis() - inicio.getTimeInMillis()) / 1000) / 60) > 0) {
                wr.append("\n\tITERACIONES POR MINUTO      : " + (FIN_BUCLE / (((fin.getTimeInMillis() - inicio.getTimeInMillis()) / 1000) / 60)));
            }
            if (((((fin.getTimeInMillis() - inicio.getTimeInMillis()) / 1000) / 60) / 60) > 0) {
                wr.append("\n\tITERACIONES POR HORA        : " + (FIN_BUCLE / ((((fin.getTimeInMillis() - inicio.getTimeInMillis()) / 1000) / 60) / 60)));
            }
            //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
            //de no hacerlo no se escribirá nada en el archivo
            wr.close();
            bw.close();
            Inicio.empezarTestButon.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
