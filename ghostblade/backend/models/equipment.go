package models

type Weapon struct {
	Name string `json:"name"`
	Atk  int    `json:"atk"`
}

type Armor struct {
	Name string `json:"name"`
}

type Shoe struct {
	Name  string `json:"name"`
	Level int    `json:"level"` // 品级
}
