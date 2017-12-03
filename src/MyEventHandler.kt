import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import java.util.*

class MyEventHandler(private val coords: LinkedList<Coordinate>) : EventHandler<MouseEvent> {
    override fun handle(event: MouseEvent) {
        coords.add(Coordinate(event.x, event.y))
        event.consume()
    }
}