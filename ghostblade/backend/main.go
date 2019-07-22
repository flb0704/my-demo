package main

import (
	"encoding/json"
	"fmt"
	sj "github.com/bitly/go-simplejson"
	"github.com/linxd-cheer/my-demo/ghostblade/backend/lib"
	"net/http"
	"time"
)

func main() {


	bytes, e := json.Marshal(lib.LEVE_1)
	if e != nil {
		return
	}
	fmt.Println(string(bytes))

	newJson, e := sj.NewJson(bytes)
	if e != nil {
		return
	}
	fmt.Println(newJson.Get("name"))

	fmt.Println("Hello World!")



	http.HandleFunc("/",sayHello)
	e = http.ListenAndServe(":8080", nil)
	if e !=nil {
		fmt.Println(e)
	}

}


func sayHello(w http.ResponseWriter, r *http.Request){
	r.ParseForm()
	fmt.Println("path", r.URL.Path)
	fmt.Fprintf(w,"hello ", time.Now())
}
