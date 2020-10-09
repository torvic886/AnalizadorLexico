package co.edu.uniquindio.compiladores.controladores

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TextField

class InicioController2
{
    @FXML lateinit var areaTexto:TextArea
    @FXML lateinit var txtPrueba:TextField

    fun cambiarValores( e:ActionEvent)
    {
        val aux = areaTexto.text
        areaTexto.text = txtPrueba.text
        txtPrueba.text = aux
    }
    
}