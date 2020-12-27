import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

var cubosActuales = 0
var lenaActual = 0
var ramasActuales = 0
var comidaActual = 0

const val CUBOS_NECESARIOS = 4
const val LENA_NECESARIA  = 2
const val RAMA_NECESARIA  = 1
const val COMIDA_NECESARIA  = 1

val mutex = Mutex()


fun main() {
    comenzar()
    Thread.sleep(80000)
    if (cubosActuales == CUBOS_NECESARIOS && lenaActual == LENA_NECESARIA && ramasActuales == RAMA_NECESARIA && comidaActual == COMIDA_NECESARIA)
        println("Barca construida y aprovisionada con exito")
}

fun comenzar() {
    println("EMPIEZAN LOS JUEGOS DEL HAMBREEEEEEE")
    GlobalScope.launch {
        runBlocking {
            amigoA()
            amigoB()
            amigoC()
        }
    }
}
suspend fun amigoA(){
    GlobalScope.async {
        repeat(CUBOS_NECESARIOS) {
            println("El Amigo A va a por un cubo de agua dulce")
            delay(4000)
            println("El Amigo A vuelve con un cubo de agua dulce")
            hamaca("Amigo A descansa en la hamaca durante 1 segundo",1000)
            cubosActuales += 1
        }
    }
}

suspend fun amigoB(){
    GlobalScope.async {
        repeat(LENA_NECESARIA) {
            println("El Amigo B va a por lena")
            hacha("Amigo B usa la hacha que tiene en el bolsillo", 5000)
            lenaActual += 1
            hamaca("Amigo B descansa en la hamaca durante 3 segundos", 3000)
        }
    }
}

suspend fun amigoC(){
    GlobalScope.async{
        //solo tiene que hacerlo una vez asi que no hay que hacer ningun bucle
        delay(1000)
        println("El amigo Amigo C va a por ramas para los remos")
        println("El amigo Amigo C vuelve con las ramas")
        //tarda 3 segundos en cortar las ramas
        delay(3000)
        ramasActuales += 1
        println("El amigo Amigo C va a cazar utilizando el hacha")
        //tarda 4 segundos en cazar, solo puede utilizar uno el hacha
        hacha("Amigo C está cazando",4000)
        comidaActual += 1
    }
}


suspend fun hamaca(nombre : String, tiempo : Long){
    //solo puede hacerlo uno a la vez
    mutex.withLock {
        println("El $nombre, quiere descansar")
        println("El $nombre, se tumba en la hamaca")
        delay(tiempo)
        println("El $nombre, se levanta de la hamaca")
        println("El $nombre, deja de descansar")
    }
}

suspend fun hacha(nombre : String, tiempo:Long){
    //solo puede utilizarlo uno a la vez
    mutex.withLock {
        println("El $nombre coge el hacha")
        delay(tiempo)
        println("El $nombre deja el hacha")
        println("El $nombre vuelve con la lena")
    }
}

