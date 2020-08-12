
import simple.search.{DataReader, IndexBuilder, ProgramIterator}

object SimpleSearchApp extends App with DataReader with IndexBuilder {
  readDir(args).fold(
    println,
    file => ProgramIterator.iterate(buildIndex(file))
  )
}
