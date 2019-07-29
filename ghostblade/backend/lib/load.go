package lib

// 这个文件用于将所有的/static/文件下的静态资源加载进来
// 注意 /static/下面

import (
	"github.com/gobuffalo/packr/v2"
	"github.com/linxd-cheer/my-demo/ghostblade/backend/models"
	"gopkg.in/yaml.v2"
)

const (
	ID_WEAPON = iota
	ID_ARMOR
)


var (
	// key为int32，value为实现了Id的接口
	// 设计的key可以保证绝对的不重复，所以可以把所有的都放进来
	container = make(map[int32]models.Id, 16)
	fileId = make(map[string]int8)

	box = packr.New("MyBox", "../statics")
)

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


// 当有新类型添加进来时需要改变的就是下面这3个
type conf struct {
	Weapon []models.Weapon `yaml:"weapon"`
	Armor []models.Armor `yaml:"armor"`
}

func init() {
	// 先进行配置的初始化
	initFile()
	for file, id := range fileId {
		var list = conf{}
		if e := Load(file, &list); e != nil {
			//utils.LogFatal("init weapon error ", e)
		}

		for _, value := range getSlice(list, id) {
			container[value.GetId()] = value
		}
	}
}


func initFile() {
	fileId["weapon.yml"] = ID_WEAPON
	fileId["armor.yml"] = ID_ARMOR
}

func getSlice(list conf, id int8) []models.Id {
	array := make([]models.Id, 0, 16)
	switch id {
	case ID_WEAPON:
		for _, value := range list.Weapon {
			array = append(array, value)
		}
	case ID_ARMOR:
		for _, value := range list.Armor {
			array = append(array, value)
		}
	}
	return array
}


