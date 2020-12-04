package co.edu.uniquindio.compiladores.semantica

class Simbolo() {
    var nombre:String = ""
    var tipo:String = ""
    var modificable:Boolean = false
    var ambito:Ambito? = null
    var nombreAmbito: String? = ""
    var fila:Int = 0
    var columna:Int = 0
    var tiposParametros:ArrayList<String>? = null

    /**
     * Constructor para crear un simbolo de tipo valor
     */
    constructor( nombre:String, tipo:String, modificable:Boolean, ambito:Ambito, fila:Int, columna:Int ): this() {
        this.nombre = nombre
        this.tipo = tipo
        this.modificable = modificable
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }
    /**
     * Constructor para crear un simbolo cuyo ambito es un String (para poder mostrar en la tabla)
     */
    constructor( nombre:String, tipo:String, modificable:Boolean, ambito: String, fila:Int, columna:Int ): this() {
        this.nombre = nombre
        this.tipo = tipo
        this.modificable = modificable
        this.nombreAmbito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     * Constructor para crear un simbolo de tipo método
     */
    constructor( nombre:String, tipoRetorno:String, tiposParametros:ArrayList<String>, ambito:Ambito ): this() {
        this.nombre = nombre
        tipo = tipoRetorno
        this.tiposParametros = tiposParametros
        this.ambito = ambito
    }

    override fun toString(): String {
        return if(tiposParametros == null) {
            "Simbolo(nombre='$nombre', tipo='$tipo', modificable=$modificable, ambito='$ambito', fila=$fila, columna=$columna)"
        } else {
            "Simbolo(nombre='$nombre', tipo='$tipo', ambito='$ambito', tiposParametros=$tiposParametros)"
        }
    }

}