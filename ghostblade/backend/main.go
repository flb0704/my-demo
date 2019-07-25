package main

import (
	"fmt"
	"github.com/linxd-cheer/my-demo/ghostblade/backend/lib"
)

func main() {
	fmt.Println("hello")
	fmt.Println(lib.GetWeapon(0x11ff001))
	fmt.Println(lib.GetArmor(0x11f1001))
}
