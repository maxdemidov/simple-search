package simple.search

package object errors {

  sealed trait ReadDirError
  case object MissingPathArg extends ReadDirError
  case class NotDirectory(error: String) extends ReadDirError
  case class FileNotFound(t: Throwable) extends ReadDirError

  sealed trait ReadFileError
  case class CantReadFile(t: Throwable) extends ReadFileError
}
