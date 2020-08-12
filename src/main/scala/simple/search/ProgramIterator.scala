package simple.search

import simple.search.model.Index

object ProgramIterator extends IndexSearcher {

  @scala.annotation.tailrec
  def iterate(indexedFiles: Index): Unit = {

    print(s"search>")
    val searchString = readLine()

    if (searchString != ":quit") {
      searchIndex(searchString, indexedFiles).toList match {
        case Nil =>
          println("no matches found")
        case list =>
          val result = list
            .map(matchPercents => s"${matchPercents.fileName} : ${matchPercents.percents}%")
            .fold("")(_ + " " + _)
          println(result)
      }
      iterate(indexedFiles)
    }
  }
}
