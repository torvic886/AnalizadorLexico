package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*
import javax.swing.JOptionPane


class InicioController : Initializable
{
    @FXML lateinit var codigoFuente:TextArea

    @FXML lateinit var tablaTokens: TableView<Token>
    @FXML lateinit var lexemaToken: TableColumn<Token, String>
    @FXML lateinit var categoriaToken: TableColumn<Token, String>
    @FXML lateinit var filaToken: TableColumn<Token, Int>
    @FXML lateinit var columnaToken: TableColumn<Token, Int>

    @FXML
    fun analizar( e:ActionEvent)
    {
        if (codigoFuente.text.isNotEmpty())
        {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()
            tablaTokens.items = FXCollections.observableArrayList(lexico.listaTokens)
            print(lexico.listaTokens)
        }
        else
        {
          //  JOptionPane.showMessageDialog(null, "Al menos Debe Ingresar Un Codigo");
            JOptionPane.showMessageDialog(null, "Al menos Debe Ingresar Un Codigo",
                    "Mensaje Informativo", JOptionPane.WARNING_MESSAGE);
        }

    }

    @FXML
    fun limpiar( e:ActionEvent)
    {

        if (codigoFuente.text.isNotEmpty())
        {

            tablaTokens.items.clear()
            codigoFuente.text = ""
        }
        else
        {
            //  JOptionPane.showMessageDialog(null, "Al menos Debe Ingresar Un Codigo");
            JOptionPane.showMessageDialog(null, "No existe Un Codigo Que Limpiar",
                    "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /** Método que permite cargar el archuvo pdf que contiene la información base del lenguaje Helix
     *
     */
    @FXML
    fun generarPDF( e:ActionEvent)
    {
        try {
            val p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + "\\resources\\pdf\\Lenguaje Helix.pdf")
        } catch (evvv: Exception) {
            JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE)
        }
    }


    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        lexemaToken.cellValueFactory = PropertyValueFactory("lexema")
        categoriaToken.cellValueFactory = PropertyValueFactory("categoria")
        filaToken.cellValueFactory = PropertyValueFactory("fila")
        columnaToken.cellValueFactory = PropertyValueFactory("columna")

    }

}