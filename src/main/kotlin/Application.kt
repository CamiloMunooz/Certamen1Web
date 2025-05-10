package prueba

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.*

import kotlinx.serialization.Serializable


@Serializable
data class DatosPersona(val id: Int, val nombre: String, val edad: Int, val dvrut: Int)

@Serializable
data class TipoAnimal(val id: Int, val animal: String, val raza: String, val dvrut: Int)

@Serializable
data class Libro(val id: Int, val nombre_libro: String, val autor: String, val dvrut: Int)

@Serializable
data class TipoProducto(val id: Int, val nombre_producto: String, val precio: Int, val dvrut: Int)

@Serializable
data class Marca(val id: Int, val nombre: String, val pais_origen: String, val dvrut: Int)

@Serializable
data class Estudiante(val id: Int, val curso: String, val promedio: Double, val dvrut: Int)

@Serializable
data class Empleado(val id: Int, val trabajo: String, val sueldo: Int, val dvrut: Int)

@Serializable
data class Pelicula(val id: Int, val nombre_pelicula: String, val genero: String, val dvrut: Int)

@Serializable
data class Curso(val id: Int, val nombre_curso: String, val profesor: String, val dvrut: Int)

@Serializable
data class Juego(val id: Int, val nombre_juego: String, val genero: String, val dvrut: Int)


@Serializable
data class Entidad(val dvRut: Int, val EntidadPrincipal: String)



val entidades = mutableListOf<Entidad>()
val personas = mutableListOf<DatosPersona>()
val animales = mutableListOf<TipoAnimal>()
val libros = mutableListOf<Libro>()
val productos = mutableListOf<TipoProducto>()
val vehiculos = mutableListOf<Marca>()
val estudiantes = mutableListOf<Estudiante>()
val empleados = mutableListOf<Empleado>()
val peliculas = mutableListOf<Pelicula>()
val cursos = mutableListOf<Curso>()
val juegos = mutableListOf<Juego>()




fun main(args: Array<String>) {

    entidades.add(Entidad(0, "personas"))
    entidades.add(Entidad(1, "animales"))
    entidades.add(Entidad(2, "libros"))
    entidades.add(Entidad(3, "productos"))
    entidades.add(Entidad(4, "vehiculos"))
    entidades.add(Entidad(5, "estudiantes"))
    entidades.add(Entidad(6, "empleados"))
    entidades.add(Entidad(7, "películas"))
    entidades.add(Entidad(8, "cursos"))
    entidades.add(Entidad(9, "juegos"))


    personas.add(DatosPersona(0, "Juan", 25,0))
    personas.add(DatosPersona(1, "María", 30,0))
    personas.add(DatosPersona(2, "Pedro", 22,0))

    animales.add(TipoAnimal(0, "Perro", "Labrador",1))
    animales.add(TipoAnimal(1, "Gato", "Siamés",1))
    animales.add(TipoAnimal(2, "Conejo", "Angora",1))

    libros.add(Libro(0, "Cien años de soledad", "Gabriel García Márquez",2))
    libros.add(Libro(1, "1984", "George Orwell",2))
    libros.add(Libro(2, "Don Quijote de la Mancha", "Miguel de Cervantes",2))

    productos.add(TipoProducto(0, "Camiseta", 25000,3))
    productos.add(TipoProducto(1, "Zapatos", 50000,3))
    productos.add(TipoProducto(2, "Bolso", 30000,3))

    vehiculos.add(Marca(0, "Toyota", "Japón",4))
    vehiculos.add(Marca(1, "Ford", "Estados Unidos",4))
    vehiculos.add(Marca(2, "Chevrolet", "Estados Unidos",4))

    estudiantes.add(Estudiante(0, "1ero Medio", 4.5,5))
    estudiantes.add(Estudiante(1, "3ro Medio", 6.3,5))
    estudiantes.add(Estudiante(2, "2do Medio", 3.8,5))

    empleados.add(Empleado(0, "Gerente", 50000,6))
    empleados.add(Empleado(1, "Analista", 40000,6))
    empleados.add(Empleado(2, "Desarrollador", 35000,6))

    peliculas.add(Pelicula(0, "Pulp Fiction", "Crimen",7))
    peliculas.add(Pelicula(1, "Forrest Gump", "Drama",7))
    peliculas.add(Pelicula(2, "La La Land", "Musical",7))

    cursos.add(Curso(0, "Matemáticas", "Alexis Sanchez",8))
    cursos.add(Curso(0, "Historia", "Arturo Vidal",8))
    cursos.add(Curso(0, "Religion", "Messi",8))

    juegos.add(Juego(0, "The Legend of Zelda", "Aventura",9))
    juegos.add(Juego(1, "Minecraft", "Sandbox",9))
    juegos.add(Juego(2, "Super Mario Bros.", "Plataformas",9))


    embeddedServer(Netty,
        port = 8080,
        host = "127.0.0.1",
        module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
    }
    routing {

        get("/") {
            call.respondText(status = HttpStatusCode.OK, text = "En funcionamiento")
        }

        post("/entidades") {
            val nuevaEntidad = call.receive<Entidad>()
            entidades.add(nuevaEntidad)
            call.respond(HttpStatusCode.Created, nuevaEntidad)
        }
        get("/entidades") {
            call.respond(entidades)
        }
        get("/entidades/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(entidades.find { it.dvRut == id } ?: HttpStatusCode.NotFound)
        }
        put("/entidades") {

            val datoActualizado = call.receive<Entidad>()
            val RutAct = entidades.indexOfFirst { it.dvRut == datoActualizado.dvRut }

            if (RutAct == -1) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Entidad con dvRut ${datoActualizado.dvRut} no encontrada"
                )
            } else {
                entidades[RutAct] = datoActualizado
                call.respondText("Entidad actualizada: $datoActualizado")
            }
        }
        delete("/entidades") {

            val entidadAEliminar = call.receive<Entidad>()

            val eliminada = entidades.removeIf { it.dvRut == entidadAEliminar.dvRut }

            if (eliminada) {
                call.respondText("Entidad eliminada con éxito", status = HttpStatusCode.OK)
            } else {
                call.respondText(
                    "No se encontró una entidad con el ID proporcionado",
                    status = HttpStatusCode.NotFound
                )
            }
        }

        get("/{entidad}/{relacional}/{id}"){

            val entidad = call.parameters["entidad"]
            val relacional = call.parameters["relacional"]
            val id = call.parameters["id"]?.toInt()


            when(entidad){
                "personas" -> {

                    call.respond(personas.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "animales" -> {

                    call.respond(animales.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "libros" -> {

                    call.respond(libros.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "productos" -> {

                    call.respond(productos.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "vehiculos" -> {

                    call.respond(vehiculos.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "estudiantes" -> {

                    call.respond(estudiantes.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "empleados" -> {

                    call.respond(empleados.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "peliculas" -> {

                    call.respond(peliculas.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "cursos" -> {

                    call.respond(cursos.find { it.id == id } ?: HttpStatusCode.NotFound)
                }
                "juegos" -> {

                    call.respond(juegos.find { it.id == id } ?: HttpStatusCode.NotFound)
                }

                else -> call.respondText("Entidad no encontrada", status = HttpStatusCode.NotFound)

            }

        }
    }
}
