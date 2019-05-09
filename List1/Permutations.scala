object Permutations extends App {
  def allInserts[T](x: T, xs: List[T]): List[List[T]] = {
    def iter[T](x: T, prefix: List[T], suffix: List[T], acc: List[List[T]]): List[List[T]] = suffix match {
      case Nil => acc :+ (prefix :+ x)
      case head::tail => iter(x, prefix :+ head, tail, (acc :+ (prefix ::: x :: suffix)))
    }

    iter(x, Nil, xs, Nil)
  }

  def permutations[T](xs: List[T]): List[List[T]] = {
    def iter[T](xs: List[T], acc: List[List[T]]): List[List[T]] = xs match {
      case Nil => acc
      case head::tail => {
        iter(tail, acc.flatMap(x => allInserts(head, x)))
      }
    }

    iter(xs, List(Nil))
  }

  permutations(List(1, 2, 3, 4))
}