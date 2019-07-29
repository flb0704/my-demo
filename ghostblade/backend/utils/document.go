// 进行存档和读档的操作
package utils

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"os"
)

// fileName 要写入的文件名
// content 一般是struct，会进行json转换
func SaveDoc(fileName string, content interface{}) {
	fileName = Cfg.HomeDir + fileName
	if bytes, e := json.Marshal(content); Handler(e, nil) {
		// 进行加密
		encryptMsg, sign, e := EncryptAndDigitalSign(bytes, Cfg.HomeDir)
		Handler(e, func() {
			panic("save failed, keystore not found")
		})

		// 第一部分是签名，第二部分是数据
		sign = append(sign, Cfg.Separator...)
		sign = append(sign, encryptMsg...)

		e = ioutil.WriteFile(fileName, sign, os.ModePerm)
		Handler(e, func() {
			LogError(e)
		})
	}
}

func ReadDoc(fileName string, valType interface{}) {
	fileName = Cfg.HomeDir + fileName
	// 获取文件中的数据
	if bytes, e := ioutil.ReadFile(fileName); Handler(e, nil) {
		// 利用kmp算法来查找分隔符位于哪个位置
		if index := KMPSearch(bytes, Cfg.Separator); index != -1 {
			length := len(Cfg.Separator)
			sign := bytes[:index]
			data := bytes[index+length:]

			if m, e := Decrypt(data, sign, Cfg.HomeDir); Handler(e, nil) {
				fmt.Println(string(m))
				e := json.Unmarshal(m, valType)
				Handler(e, func() {
					LogError(e)
				})
			}
		} else {
			LogInfo("document is not save file")
		}
	}

}

// kmp 算法，实现参考 https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/
func KMPSearch(txt []byte, pattern []byte) int {
	n := len(txt)
	m := len(pattern)

	//longest prefix suffix
	lps := make([]int, m, m)
	// 计算pattern中的子列
	computeLPSArray(pattern, m, lps)

	i, j := 0, 0
	for i < n {
		if pattern[j] == txt[i] {
			j++
			i++
		}
		if j == m {
			LogInfof("found pattern at index %d \n", i-j)
			return i - j
		} else if i < n && pattern[j] != txt[i] {
			if j != 0 {
				j = lps[j-1]
			} else {
				i++
			}
		}
	}

	return -1
}

func computeLPSArray(pat []byte, m int, lps []int) {
	ilen := 0
	lps[0] = 0 // lps[0] is always 0

	i := 1 // from 1 to last
	for i < m {
		if pat[i] == pat[ilen] {
			ilen++
			lps[i] = ilen
			i++
		} else {
			if ilen != 0 {
				ilen = lps[ilen-1]
			} else {
				lps[i] = 0
				i++
			}
		}
	}
}
