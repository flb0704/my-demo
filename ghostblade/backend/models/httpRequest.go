package models

import "github.com/bitly/go-simplejson"

type HttpRequest struct {
	Path  string           `json:"path"`
	Param *simplejson.Json `json:"param"`
}
