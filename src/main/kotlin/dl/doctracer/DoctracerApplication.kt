package dl.doctracer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DoctracerApplication

fun main(args: Array<String>) {
	runApplication<DoctracerApplication>(*args)
}
