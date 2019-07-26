package utils

// 定义异常的通用处理
// 没有异常，则返回true，有异常，可以执行传入的函数，并返回false
func Handler(e error, fn func()) bool  {
	if e == nil {
		return true
	} else if fn != nil {
		fn()
	}
	return false
}