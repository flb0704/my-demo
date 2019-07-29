// 这个文件定义全局通用的变量，全局变量仅仅允许在这里做修改
package utils

import "os"

type setting struct {
	HomeDir string
	Separator []byte
}

var Cfg setting

func init()  {
	s, _ := os.UserHomeDir()
	Cfg.HomeDir = s + "/ghostblade/"
	Cfg.Separator = []byte("@v1v1v1v@")

	// 判断这个目录是否存在，不存在则进行创建
	if _, e := os.Stat(Cfg.HomeDir); os.IsNotExist(e) {
		e := os.Mkdir(Cfg.HomeDir, os.ModePerm)
		if e != nil {
			LogErrorf(" create %s failed", Cfg.HomeDir)
		}
	}
}
