// 进行存档和读档的操作
package utils

import (
	"fmt"
	"testing"
)

type hello struct {
	Name string `json:"name"`
}

func TestSaveDoc(t *testing.T) {
	SaveDoc("123321", hello{Name:"zhangsan"})
}

func TestReadDoc(t *testing.T) {
	var h = hello{}
	ReadDoc("123321", &h)
}

func TestKMP(t *testing.T) {

	KMPSearch([]byte("1231231323"),[]byte("313"))
	KMPSearch([]byte("1111111111111112"),[]byte("12"))
	KMPSearch([]byte("1111111111111112"),[]byte("12"))
	if KMPSearch([]byte("1111"),[]byte("222222")) == -1 {
		fmt.Println("yes")
	}
}