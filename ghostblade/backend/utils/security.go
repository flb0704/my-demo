// 这个包主要是来实现rsa秘钥的生成，以及对文件的加密、数字签名
package utils

import (
	"crypto"
	"crypto/md5"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha1"
	"crypto/x509"
	"encoding/pem"
	"errors"
	"fmt"
	"io/ioutil"
	"log"
	"os"
)

const (
	PUB_SUFFIX  = "/.rsa_gb.pub"
	PRIV_SUFFIX = "/.rsa_gb"
)

func GenerateKeyPair(bits int) (*rsa.PublicKey, *rsa.PrivateKey) {
	privateKey, e := rsa.GenerateKey(rand.Reader, bits)
	if e != nil {
		fmt.Println(e)
	}
	return &privateKey.PublicKey, privateKey
}

func PrivateKeyToBits(priv *rsa.PrivateKey) []byte {
	return pem.EncodeToMemory(
		&pem.Block{
			Type:  "RSA PRIVATE KEY",
			Bytes: x509.MarshalPKCS1PrivateKey(priv),
		},
	)
}

func PublicKeyToBits(pub *rsa.PublicKey) []byte {
	bytes, err := x509.MarshalPKIXPublicKey(pub)
	if err != nil {
		fmt.Println(err)
	}

	return pem.EncodeToMemory(
		&pem.Block{
			Type:  "RSA PUBLIC KEY",
			Bytes: bytes,
		},
	)
}

// 在指定的目录下，生成公钥和私钥文件
// 默认公钥文件为 .rsa_gb.pub   私钥文件 .rsa_gb
func GenerateKeyFile(fileDir string) {
	// 如果目录不存在
	if _, err := os.Stat(fileDir); os.IsNotExist(err) {
		// 试图创建目录，目录可读写
		e := os.Mkdir(fileDir, 0666)
		Handler(e, func() {
			log.Fatal(e)
		})
	}

	// 如果公钥和私钥文件都存在，则不进行创建
	_, e := os.Stat(fileDir + PUB_SUFFIX)
	_, e2 := os.Stat(fileDir + PRIV_SUFFIX)
	if !os.IsNotExist(e) && !os.IsNotExist(e2) {
		return
	}
	// 生成keyPair
	publickey, privateKey := GenerateKeyPair(2048)
	// key to bits
	privBits := PrivateKeyToBits(privateKey)
	pubBits := PublicKeyToBits(publickey)

	// 私钥
	file, e := os.OpenFile(fileDir+PRIV_SUFFIX, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0666)
	if Handler(e, nil) {
		_, _ = file.Write(privBits)
	}
	// 公钥
	pubFile, e := os.OpenFile(fileDir+PUB_SUFFIX, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0666)
	if Handler(e, nil) {
		_, _ = pubFile.Write(pubBits)
	}

	defer func() {
		Handler(file.Close(), nil)
		Handler(pubFile.Close(), nil)
	}()
}

// 从秘钥文件中读取密钥
func ReadKeyFile(dir string) (pub *rsa.PublicKey, priv *rsa.PrivateKey)  {
	// 如果目录不存在
	if _, err := os.Stat(dir); os.IsNotExist(err) {
		log.Fatal(err)
	}
	// 如果公钥和私钥文件都存在，则不进行创建
	_, e := os.Stat(dir + PUB_SUFFIX)
	_, e2 := os.Stat(dir + PRIV_SUFFIX)
	if os.IsNotExist(e) || os.IsNotExist(e2) {
		log.Fatal("Missing key file")
	}

	if bytes, e := ioutil.ReadFile(dir + PUB_SUFFIX); Handler(e,nil) {
		pub = BytesToPublicKey(bytes)
	}
	if bytes, e := ioutil.ReadFile(dir + PRIV_SUFFIX); Handler(e,nil) {
		priv = BytesToPrivateKey(bytes)
	}
	return
}

// 字节转换为私钥
func BytesToPrivateKey(bits []byte) *rsa.PrivateKey {
	block, _ := pem.Decode(bits)

	key, e := x509.ParsePKCS1PrivateKey(block.Bytes)
	if Handler(e,nil) {
		return key
	}
	return nil
}

// 字节转公钥
func BytesToPublicKey(bits []byte) *rsa.PublicKey {
	block, _ := pem.Decode(bits)

	pub, err := x509.ParsePKIXPublicKey(block.Bytes)
	if Handler(err,nil) {
		if key,ok := pub.(*rsa.PublicKey); ok {
			return key
		}
	}
	return nil
}

// 公钥加密
func EncryptWithPubKey(msg []byte, pub *rsa.PublicKey) []byte {
	hash := sha1.New()
	bytes, e := rsa.EncryptOAEP(hash, rand.Reader, pub, msg, nil)
	if Handler(e,nil) {
		return bytes
	}
	return nil
}

// 私钥解密
func DecryptWithPrivKey(encry []byte, priv *rsa.PrivateKey) []byte {
	hash := sha1.New()
	bytes, e := rsa.DecryptOAEP(hash, rand.Reader, priv, encry, nil)
	if Handler(e,nil) {
		return bytes
	}
	return nil
}

// 加密并生成数字签名，数字签名使用私钥来加密，解密使用公钥
// msg表示要加密的信息
// keyDir 表示密钥存放的路径
func EncryptAndDigitalSign(msg []byte, keyDir string) (encryptMsg []byte, sign []byte, e error) {
	e = nil
	pub, priv := ReadKeyFile(keyDir)
	if pub == nil || priv == nil {
		e = errors.New("rsa key not found")
		return
	}
	// 信息加密
	encryptMsg = EncryptWithPubKey(msg, pub)
	// 生成hash摘要
	h := md5.New()
	h.Write(encryptMsg)
	hashed := h.Sum(nil)

	// 使用私钥加密摘要
	signBytes, e2 := rsa.SignPKCS1v15(rand.Reader, priv, crypto.MD5, hashed)

	sign, e = signBytes, e2
	return
}

func Decrypt(msg []byte, sign []byte, keyDir string) (m []byte, e error) {
	e = nil
	pub, priv := ReadKeyFile(keyDir)
	if pub == nil || priv == nil {
		e = errors.New("rsa key not found")
		return
	}
	// 摘要校验
	h := md5.New()
	h.Write(msg)
	hashed := h.Sum(nil)

	// 签名使用公钥来解密
	if e = rsa.VerifyPKCS1v15(pub, crypto.MD5, hashed, sign); e != nil {
		return nil, e
	}
	// 内容私钥来解密
	m = DecryptWithPrivKey(msg, priv)
	return
}

