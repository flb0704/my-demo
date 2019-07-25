package utils

import (
	"github.com/gobuffalo/packr/v2"
	"gopkg.in/yaml.v2"
)

var box = packr.New("MyBox", "../statics")

// 加载yaml文件中的内容，到结构体当中去
// fileName: 文件名
// i: 在此表示结构体的指针
func Load(fileName string, i interface{}) error {
	bytes, e := box.Find(fileName)
	if e != nil {
		return e
	}
	return yaml.Unmarshal(bytes, i)
}
