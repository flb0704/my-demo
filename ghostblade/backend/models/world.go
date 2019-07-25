package models

type Id interface {
	GetId() int32 // 获取id
}

// 世界地图
type World struct {
	Id   int32 `json:"id" yaml:"id"`
	Name string `json:"name" yaml:"name"`
}

func (w World) GetId() int32 {
	return w.Id
}
