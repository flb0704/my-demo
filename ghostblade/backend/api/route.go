// 定义api借口路由
package api

import "net/http"

func Register(path string, fn func())  {
	http.HandleFunc(path, func(writer http.ResponseWriter, request *http.Request) {
		fn()
	})
}
