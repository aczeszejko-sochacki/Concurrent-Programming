object Main extends App {
  def whileLoop(cond: =>Boolean)(expr: =>Unit): Unit = 
    if (cond()) { expr(); whileLoop(cond, expr) }
    else ()

  var count = 0
  val cond = () => count < 5
  val expr = () => {println(count); count += 1}

  whileLoop(cond)(expr)
}