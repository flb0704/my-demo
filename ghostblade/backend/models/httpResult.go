package models

// http统一的结果
type HttpResponse struct {
	Msg    string      `json:"msg"`    // 信息
	Status int         `json:"status"` // 响应状态
	Data   interface{} `json:"data"`   // 响应数据
}

// 获取数据成功
func Ok(data interface{}) HttpResponse {
	return HttpResponse{
		Msg:    "ok",
		Status: 200,
		Data:   data,
	}
}

// 当错误时，返回错误信息
func Error(msg string, data interface{}) HttpResponse {
	return HttpResponse{
		Msg:    msg,
		Status: 500,
		Data:   data,
	}
}
