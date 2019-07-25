package models

// 武器
type Weapon struct {
	Id    int32  `json:"id" yaml:"id"`       // 物品id
	Level int8   `json:"level" yaml:"level"` // 品质级别
	Name  string `json:"name" yaml:"name"`   // 名称
	Atk   int    `json:"atk" yaml:"atk"`     // 物理伤害值
}

func (w Weapon) GetId() int32 {
	return w.Id
}

// 护甲
type Armor struct {
	Id      int32  `json:"id" yaml:"id"`           // 物品id
	Level   int8   `json:"level" yaml:"level"`     // 品质级别
	Name    string `json:"name" yaml:"name"`       // 名称
	Defense int    `json:"defense" yaml:"defense"` // 防御值
}

func (a Armor) GetId() int32 {
	return a.Id
}

// 鞋子
type Shoe struct {
	Id    int32  `json:"id" yaml:"id"`       // 物品id
	Level int8   `json:"level" yaml:"level"` // 品质级别
	Name  string `json:"name" yaml:"name"`   // 名称
	Agile int32  `json:"agile" yaml:"agile"` // 敏捷
}

func (s Shoe) GetId() int32 {
	return s.Id
}
