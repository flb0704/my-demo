package utils

import "testing"

func TestLogInfo(t *testing.T) {
	LogInfof("Test %[1]d number %[1]d \n", 1, 2)
}

func TestLogError(t *testing.T) {
	LogErrorf(" Test %[2]d number %[1]d \n", 1, 200)
}

func TestLogFatal(t *testing.T) {
	LogFatalf("hello")
}