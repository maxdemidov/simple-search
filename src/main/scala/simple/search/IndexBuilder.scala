package simple.search

import java.io.File

import simple.search.errors.CantReadFile
import simple.search.model.{FileIdMetadata, FileWordCount, Index, WordCount}

import scala.collection.mutable

trait IndexBuilder extends DataReader {

  def buildIndex(dir: File): Index = {

    val dirName = dir.getName

    var fileNameCounter: Integer = 0

    val filesIdMetas:  mutable.Map[Integer, FileIdMetadata] = mutable.Map()
    val wordsOccurrences: mutable.Map[String, List[FileWordCount]] = mutable.Map()

    dir.listFiles().toList.foreach(file => {
      val fileName = file.getName
      fileNameCounter = fileNameCounter + 1

      readFile(dir, fileName) match {

        case Right(data) =>
          val wordsCounts = splitToWords(data)
          extendOccurrencesWithWords(wordsOccurrences, fileNameCounter, wordsCounts)
          extendFilesIdMetas(filesIdMetas, fileNameCounter, fileName, wordsCounts)

        case Left(error: CantReadFile) =>
          println(s"read error for file $fileName: ${error.t.getMessage}")
      }
    })
    println(s"$fileNameCounter files read in directory $dirName")
    Index(filesIdMetas, wordsOccurrences)
  }

  private def splitToWords(data: String) = {
    data.split("\\W+").groupBy(x => x).map(x => WordCount(x._1, x._2.length))
  }

  private def extendOccurrencesWithWords(wordsOccurrences: mutable.Map[String, List[FileWordCount]],
                                         fileNameCounter: Integer,
                                         wordsCounts: Iterable[WordCount]): Unit = {
    wordsCounts.foreach(wordCount => {
      val newList = wordsOccurrences.get(wordCount.word) match {
        case Some(list) =>
          FileWordCount(fileNameCounter, wordCount.count) :: list
        case None =>
          List(FileWordCount(fileNameCounter, wordCount.count))
      }
      wordsOccurrences += wordCount.word -> newList
    })
  }

  private def extendFilesIdMetas(filesIdMetas:  mutable.Map[Integer, FileIdMetadata],
                                 fileNameCounter: Integer,
                                 fileName: String,
                                 wordsCounts: Iterable[WordCount]) = {
    val allWordsInFileCount = wordsCounts.map(_.count).foldLeft(0)(_ + _)
    filesIdMetas += fileNameCounter -> FileIdMetadata(fileName, fileNameCounter, allWordsInFileCount)
  }
}
