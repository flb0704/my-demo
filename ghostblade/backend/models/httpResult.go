package models

// http统一的结果
type HttpResult struct {
	Msg    string      `json:"msg"`
	Status int         `json:"status"`
	Data   interface{} `json:"data"`
}

// 获取数据成功
func Ok(data interface{}) HttpResult {
	return HttpResult{
		Msg:    "ok",
		Status: 200,
		Data:   data,
	}
}

// 当错误时，返回错误信息
func Error(msg string, data interface{}) HttpResult {
	return HttpResult{
		Msg:    msg,
		Status: 500,
		Data:   data,
	}
}
