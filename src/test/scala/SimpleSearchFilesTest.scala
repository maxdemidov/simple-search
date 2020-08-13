import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import simple.search.DataReader
import simple.search.errors.{MissingPathArg, NotDirectory}

class SimpleSearchFilesTest extends AnyFunSuite with Matchers
  with DataReader {

  test("no path") {
    readDir(Array()) match {
      case Right(_) => throw new Exception("'no path' test failed")
      case Left(error) => error shouldBe MissingPathArg
    }
  }

  test("empty path") {
    val path = ""
    readDir(Array(path)) match {
      case Right(_) => throw new Exception("'empty path' test failed")
      case Left(error) => error shouldBe NotDirectory(s"Path [$path] is not a directory")
    }
  }

  test("not such directory") {
    val path = "./src/test/resources/nosuchdir"
    readDir(Array(path)) match {
      case Right(_) => throw new Exception("'not such directory' test failed")
      case Left(error) => error shouldBe NotDirectory(s"Path [$path] is not a directory")
    }
  }

  test("not a directory") {
    val path = "./src/test/resources/emptyfile/file.txt"
    readDir(Array(path)) match {
      case Right(_) => throw new Exception("'not a directory' test failed")
      case Left(error) => error shouldBe NotDirectory(s"Path [$path] is not a directory")
    }
  }

  // todo - to extend with tests for other errors

  test("directory with file") {
    val path = "./src/test/resources/emptyfile"
    readDir(Array(path)) match {
      case Right(file) => file.listFiles().length shouldBe 1
      case Left(_) => throw new Exception("'directory with file' test failed")
    }
  }

  test("directory with files") {
    val path = "./src/test/resources/somefiles"
    readDir(Array(path)) match {
      case Right(file) => file.listFiles().length shouldBe 4
      case Left(_) => throw new Exception("'directory with files' test failed")
    }
  }
}
