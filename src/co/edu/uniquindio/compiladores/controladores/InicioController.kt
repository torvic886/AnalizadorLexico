package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico6
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion3
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.awt.Desktop
import java.io.File
import java.net.URL
import java.util.*
import javax.swing.JOptionPane
import kotlin.collections.ArrayList


class InicioController : Initializable {
    @FXML
    lateinit var codigoFuente: TextArea

    @FXML
    lateinit var tablaTokens: TableView<Token>
    @FXML
    lateinit var lexemaToken: TableColumn<Token, String>
    @FXML
    lateinit var categoriaToken: TableColumn<Token, String>
    @FXML
    lateinit var filaToken: TableColumn<Token, Int>
    @FXML
    lateinit var columnaToken: TableColumn<Token, Int>

    //
    @FXML
    lateinit var tablaErroresLexico: TableView<Error> // variable de la tabla
    @FXML
    lateinit var mensajeErrorLexico: TableColumn<Error, String> // variable de la columna lexema
    @FXML
    lateinit var filaErrorLexico: TableColumn<Error, Int> // variable de la columna categoria
    @FXML
    lateinit var columnaErrorLexico: TableColumn<Error, Int> // variable de la columna fila

    @FXML
    lateinit var tablaErroresSintactico: TableView<Error> // variable de la tabla
    @FXML
    lateinit var mensajeErrorSintactico: TableColumn<Error, String> // variable de la columna lexema
    @FXML
    lateinit var filaErrorSintactico: TableColumn<Error, Int> // variable de la columna categoria
    @FXML
    lateinit var columnaErrorSintactico: TableColumn<Error, Int> // variable de la columna fila

    @FXML
    lateinit var tablaErroresSemantico: TableView<Error> // variable de la tabla
    @FXML
    lateinit var mensajeErrorSemantico: TableColumn<Error, String> // variable de la columna lexema
    @FXML
    lateinit var filaErrorSemantico: TableColumn<Error, Int> // variable de la columna categoria
    @FXML
    lateinit var columnaErrorSemantico: TableColumn<Error, Int> // variable de la columna fila

    //


    private var unidadDeCompilacion: UnidadDeCompilacion3? = null
    private lateinit var lexico: AnalizadorLexico
    private lateinit var sintaxis: AnalizadorSintactico6
    private lateinit var semantica: AnalizadorSemantico
    @FXML
    lateinit var arbolVisual: TreeView<String>

    @FXML
    fun analizar(e: ActionEvent) {
        if (codigoFuente.text.isNotEmpty()) {
            lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()
            tablaTokens.items = FXCollections.observableArrayList(lexico.listaTokens)
            //tablaErroresLexico.setItems(FXCollections.observableArrayList(lexico.listaErrores))


            sintaxis = AnalizadorSintactico6(lexico.listaTokens)
            unidadDeCompilacion = sintaxis.esUnidadDeCompilacion5()
            tablaErroresSintactico.setItems(FXCollections.observableArrayList(sintaxis.listaErrores))

            println(sintaxis.listaErrores)

            if (unidadDeCompilacion != null) {
                arbolVisual.root = unidadDeCompilacion!!.getArbolVisual()

                semantica = AnalizadorSemantico(unidadDeCompilacion!!)
                semantica.llenarTablaSimbolos()

                println(semantica.tablaSimbolos)

                semantica.analizarSemantica()

                println(semantica.listaErrores)
                tablaErroresSemantico.setItems(FXCollections.observableArrayList(semantica.listaErrores))

            }
            if (sintaxis.listaErrores.isEmpty()) {

            } else {
                var alerta = Alert(Alert.AlertType.WARNING)
                alerta.headerText = "Alerta"
                alerta.contentText = "Hay errores en el código fuente"
            }


        } else {
            //  JOptionPane.showMessageDialog(null, "Al menos Debe Ingresar Un Codigo");
            JOptionPane.showMessageDialog(null, "Al menos Debe Ingresar Un Codigo",
                    "Mensaje Informativo", JOptionPane.WARNING_MESSAGE);
        }

    }

    @FXML
    fun limpiar(e: ActionEvent) {

        if (codigoFuente.text.isNotEmpty()) {
            tablaTokens.items.clear()
            codigoFuente.text = ""
        } else {
            //  JOptionPane.showMessageDialog(null, "Al menos Debe Ingresar Un Codigo");
            JOptionPane.showMessageDialog(null, "No existe Un Codigo Que Limpiar",
                    "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /** Método que permite cargar el archuvo pdf que contiene la información base del lenguaje Helix
     *
     */
    @FXML
    fun generarPDF(e: ActionEvent) {
        try {

            val objetofile: File = File("resources\\pdf\\Lenguaje Helix.pdf")
            Desktop.getDesktop().open(objetofile)

        } catch (evvv: Exception) {
            JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE)
        }
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        lexemaToken.cellValueFactory = PropertyValueFactory("lexema")
        categoriaToken.cellValueFactory = PropertyValueFactory("categoria")
        filaToken.cellValueFactory = PropertyValueFactory("fila")
        columnaToken.cellValueFactory = PropertyValueFactory("columna")

        mensajeErrorLexico.cellValueFactory = PropertyValueFactory("error")
        filaErrorLexico.cellValueFactory = PropertyValueFactory("fila")
        columnaErrorLexico.cellValueFactory = PropertyValueFactory("columna")

        mensajeErrorSintactico.cellValueFactory = PropertyValueFactory("error")
        filaErrorSintactico.cellValueFactory = PropertyValueFactory("fila")
        columnaErrorSintactico.cellValueFactory = PropertyValueFactory("columna")

        mensajeErrorSemantico.cellValueFactory = PropertyValueFactory("error")
        filaErrorSemantico.cellValueFactory = PropertyValueFactory("fila")
        columnaErrorSemantico.cellValueFactory = PropertyValueFactory("columna")


    }

    @FXML
    fun traducirCodigo(e: ActionEvent) {
        if (unidadDeCompilacion != null) {
            val codigo = unidadDeCompilacion!!.getJavaCode()
            File("src/Principal.java").writeText( codigo )
            val runtime = Runtime.getRuntime().exec("javac src/Principal.java")
            runtime.waitFor()
            Runtime.getRuntime().exec("java Principal", null, File("src"))

        }
        /*if (unidadDeCompilacion != null) {
            File("src/Principal.java").writeText( unidadDeCompilacion!!.getJavaCode() )
            val runtime = Runtime.getRuntime().exec("javac src/Principal.java")
            runtime.waitFor()
            Runtime.getRuntime().exec("java Principal", null, File("src"))
        }*/
    }

}