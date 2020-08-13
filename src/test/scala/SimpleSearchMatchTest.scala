import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import simple.search.model.MatchPercents
import simple.search.{DataReader, IndexBuilder, IndexSearcher, model}

class SimpleSearchMatchTest extends AnyFunSuite with Matchers
  with DataReader with IndexBuilder with IndexSearcher {

  val path = "./src/test/resources/somefiles"
  val index: model.Index = readDir(Array(path)) match {
    case Right(file) => buildIndex(file)
    case Left(_) => throw new Exception("match test failed")
  }

  test("no matches") {
    searchIndex("nomatch", index).toList shouldBe empty
  }

  test("match 100 percents") {
    searchIndex("simple search", index).toList shouldBe List(MatchPercents("file0.txt", 100d))
  }

  test("match several equals words in file") {
    searchIndex("five", index).toList shouldBe List(MatchPercents("file1.txt", 50d))
  }

  test("match with several words") {
    searchIndex("to be or", index).toList shouldBe List(MatchPercents("file3.txt", 50d))
  }

  test("match with several equal words") {
    searchIndex("to be or not to be", index).toList shouldBe List(MatchPercents("file3.txt", 60d))
  }

  test("match in several files") {
    searchIndex("one", index).toList should contain theSameElementsAs
      List(MatchPercents("file1.txt", 30d), MatchPercents("file2.txt", 50d), MatchPercents("file3.txt", 10d))
  }

  test("match in several files with several words") {
    searchIndex("two one", index).toList should contain theSameElementsAs
      List(MatchPercents("file1.txt", 40d), MatchPercents("file2.txt", 100d), MatchPercents("file3.txt", 20d))
  }
}
