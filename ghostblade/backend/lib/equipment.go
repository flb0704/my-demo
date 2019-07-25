package lib

import "github.com/linxd-cheer/my-demo/ghostblade/backend/models"

func GetWeapon(id int32) models.Weapon {
	if i, ok := container[id]; ok {
		// 使用接口断言来进行类型的转换
		if weapon, ok := i.(models.Weapon); ok {
			return weapon
		}
	}
	return models.Weapon{}
}


func GetArmor(id int32) models.Armor  {
	if i, ok := container[id]; ok {
		// 使用接口断言来进行类型的转换
		if armor, ok := i.(models.Armor); ok {
			return armor
		}
	}
	return models.Armor{}
}

func GetShoe(id int32) models.Shoe {
	if i, ok := container[id]; ok {
		// 使用接口断言来进行类型的转换
		if shoe, ok := i.(models.Shoe); ok {
			return shoe
		}
	}
	return models.Shoe{}
}