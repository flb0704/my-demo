// 对日志的打印进行封装
package utils

import (
	"io"
	"log"
	"os"
)

var (
	Info *log.Logger
	Error *log.Logger
)

func init()  {
	infoFile := create(Cfg.HomeDir + "/ghostblade-info.log")
	errorFile := create(Cfg.HomeDir + "/ghostblade-error.log")

	Info = log.New(io.MultiWriter(os.Stdout, infoFile),"[info] ",log.Ldate|log.Ltime|log.Lshortfile)
	Error = log.New(io.MultiWriter(os.Stdout, errorFile),"[error] ",log.Ldate|log.Ltime|log.Lshortfile)
}

func create(filePath string) *os.File {
	file, _ := os.OpenFile(filePath, os.O_CREATE|os.O_APPEND, 0666)
	return file
}

func LogInfo(v interface{}){
	Info.Println(v)
}

func LogInfof(fmts string, v ...interface{}){
	Info.Printf(fmts, v...)
}


func LogError(v interface{})  {
	Error.Println(v)
}

func LogErrorf(fmts string, i ...interface{})  {
	Error.Printf(fmts, i...)
}

func LogFatal(v interface{})  {
	Error.Print(" [fatal] ")
	Error.Println(v)
}

func LogFatalf(fmts string, i ...interface{})  {
	fmts = " [fatal] " + fmts
	Error.Printf(fmts, i...)
	// 游戏退出
	os.Exit(1)
}
