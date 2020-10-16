package co.edu.uniquindio.compiladores.prueba

import javafx.fxml.FXML
import javax.swing.JOptionPane

fun main( args:Array<String>){
   generarPDF()
}
/** Método que permite cargar el archuvo pdf que contiene la información base del lenguaje Helix
 *
 */
@FXML
fun generarPDF( )
{
    try {
        val p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + "\\pdf1\\Lenguaje Helix.pdf")
    } catch (evvv: Exception) {
        JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE)
    }
}