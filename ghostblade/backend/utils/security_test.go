package utils

import (
	"fmt"
	"os"
	"testing"
)

func TestPublicKeyToBits(t *testing.T) {
	publickey, privateKey := GenerateKeyPair(2048)
	pubBits := PublicKeyToBits(publickey)
	privBits := PrivateKeyToBits(privateKey)

	fmt.Println(string(pubBits))
	fmt.Println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++")
	fmt.Println(string(privBits))
}

func TestGenerateKeyFile(t *testing.T)  {
	publicKey, priv := GenerateKeyPair(1024)

	bits := PublicKeyToBits(publicKey)
	privBits := PrivateKeyToBits(priv)

	key := BytesToPublicKey(bits)
	privKey := BytesToPrivateKey(privBits)
	if key != nil && privKey != nil {
		fmt.Println("ok")
	}
}

func TestEncryptAndDigitalSign(t *testing.T) {
	s, _ := os.UserHomeDir()
	var text = "HELLO WORLD"
	encryptMsg, sign, _ := EncryptAndDigitalSign([]byte(text), s)

	m, e := Decrypt(encryptMsg, sign, s)
	if nil == e && string(m) == text {
		fmt.Println("ok")
	} else {
		t.Error("failed")
	}
}
