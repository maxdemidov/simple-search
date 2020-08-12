package simple.search

import simple.search.model.{FileWordCount, Index, MatchPercents}

import scala.collection.immutable

trait IndexSearcher {

  def searchIndex(searchString: String, indexedFiles: Index): immutable.Iterable[MatchPercents] = {

    val words = searchString.split("\\s+").groupBy(x => x).keys.toList

    words.map(word => indexedFiles.wordsOccurrences.get(word)).collect {
      case Some(listWordCounts) => listWordCounts
      case None => Nil
    }.flatten
      .groupBy(v => v.fileId)
      .map(v => {
        val fileIndex = indexedFiles.fileIdMetas.getOrElse(v._1, throw new IllegalStateException("Incorrect index"))
        val count = v._2.foldLeft[Integer](0)((acc: Integer, wc: FileWordCount) => acc + wc.count)
        val percents: Double = count.toDouble * 100d / fileIndex.allWordsCount.toDouble
        MatchPercents(fileIndex.fileName, percents)
      })
  }
}
