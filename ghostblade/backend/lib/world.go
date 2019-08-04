package lib

import "github.com/linxd-cheer/my-demo/ghostblade/backend/models"

type worldMap map[int32]models.Weapon

type worldList struct {
	Data []worldMap
}


