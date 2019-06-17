object Main extends App {
  def whileLoop(cond: =>Boolean)(expr: =>Unit): Unit = 
    if (cond) { expr; whileLoop(cond)(expr) }

  var count = 0

  whileLoop(count < 5) { println(count); count += 1 }
}