package api

import "github.com/linxd-cheer/my-demo/ghostblade/backend/models"

type Handler interface {
	// 处理http请求
	handler(param models.HttpRequest) models.HttpResponse
}
