package simple.search

import java.io.File

import simple.search.errors.{CantReadFile, FileNotFound, MissingPathArg, NotDirectory, ReadDirError, ReadFileError}

import scala.io.Source
import scala.util.Try

trait DataReader {

  def readDir(args: Array[String]): Either[ReadDirError, File] = {
    for {
      path <- args.headOption.toRight(MissingPathArg)
      file <- Try(new java.io.File(path)).fold(
        throwable => Left(FileNotFound(throwable)),
        file => if (file.isDirectory) Right(file) else Left(NotDirectory(s"Path [$path] is not a directory"))
      )
    } yield file
  }

  def readFile(dir: File, fileName: String): Either[ReadFileError, String] = {
    Try(Source.fromFile(s"${dir.getPath}/$fileName")).fold(
      throwable => Left(CantReadFile(throwable)),
      fileSource => Right(fileSource.mkString)
    )
  }
}
