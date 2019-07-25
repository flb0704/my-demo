package models

import "github.com/bitly/go-simplejson"

type HttpRequest struct {
	Path  string           `json:"path"`  // 请求的接口路径
	Param *simplejson.Json `json:"param"` // 参数，统一按照json来传
}
