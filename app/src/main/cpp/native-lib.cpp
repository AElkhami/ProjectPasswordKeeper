#include <jni.h>
#include <string>

#ifdef __cplusplus
extern "C"
{
#endif

JNIEXPORT jstring JNICALL
Java_com_elkhamitech_projectkeeper_Constants_getTransformationFromJNI(JNIEnv *env, jclass type) {
//    std::string transformation = "BLOWFISH/CBC/PKCS5Padding";
    std::string transformation = "AES/CBC/PKCS5Padding";
    return env->NewStringUTF(transformation.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_elkhamitech_projectkeeper_Constants_getAlgorithmFromJNI(JNIEnv *env, jclass type) {
//    std::string algorithm = "Blowfish";
    std::string algorithm = "AES";
    return env->NewStringUTF(algorithm.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_elkhamitech_projectkeeper_Constants_getEncodingFromJNI(JNIEnv *env, jclass type) {
    std::string encoding = "UTF-8";
    return env->NewStringUTF(encoding.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_elkhamitech_projectkeeper_Constants_getEncryptKeyFromJNI(JNIEnv *env, jclass type) {
    std::string ENCODE_KEY = "R5sf90RoBL3mIEFApBXxF5+WYY=";
    return env->NewStringUTF(ENCODE_KEY.c_str());
}

#ifdef __cplusplus
}
#endif
