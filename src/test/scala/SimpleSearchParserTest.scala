import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import simple.search.model.MatchPercents
import simple.search.{DataReader, IndexBuilder, IndexSearcher, model}

class SimpleSearchParserTest extends AnyFunSuite with Matchers
  with DataReader with IndexBuilder with IndexSearcher {

  test("check parser") {
    val path = "./src/test/resources/onefile"
    val index: model.Index = readDir(Array(path)) match {
      case Right(file) => buildIndex(file)
      case Left(_) => throw new Exception("parse test failed")
    }

    index.wordsOccurrences.keys.toList should contain theSameElementsAs
      List("to", "be", "or", "not", "123", "456")
  }
}
