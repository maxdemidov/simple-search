package simple.search

import scala.collection.mutable

package object model {

  case class FileIdMetadata(fileName: String, fileId: Integer, allWordsCount: Integer)

  case class FileWordCount(fileId: Integer, count: Integer)

  case class Index(fileIdMetas: mutable.Map[Integer, FileIdMetadata],
                   wordsOccurrences: mutable.Map[String, List[FileWordCount]])


  case class WordCount(word: String, count: Integer)

  case class MatchPercents(fileName: String, percents: Double)
}
